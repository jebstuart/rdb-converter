package com.jebware.rdbconverter;

import com.jebware.rdbconverter.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jware on 6/29/16.
 * (c) 2016
 *
 * TODO Javadoc
 */
public class RdbReader<T> {

    private final BufferedReader reader;
    private final Class<T> clazz;
    private final Map<Integer, Field> columnMap = new HashMap<>();

    public RdbReader(InputStream inputStream, Class<T> clazz) throws IOException {
        this.clazz = clazz;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);

        /*
         * read until first non-comment line, which should be the header describing the columns
         * make a map of columns to fields
         * read the next non-comment line, which gives field sizes - ignore for now
         */
        String line;
        boolean hitHeader = false;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#")) {
                hitHeader = true; //we're past the comments, on to the header
                break;
            }
        }
        if (!hitHeader) throw new IOException("no header found in rdb stream, only comments");

        Field[] fields = clazz.getFields();
        String[] columns = line.split("\t");
        for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
            String column = columns[columnIndex];
            for (Field field : fields) {
                if (field.getName().equals(column)) {
                    columnMap.put(columnIndex, field);
                }
                SerializedName serializedName = field.getAnnotation(SerializedName.class);
                if (serializedName != null && serializedName.value().equals(column)) {
                    columnMap.put(columnIndex, field);
                }
            }
        }

        reader.readLine(); //burn the line with field sizes, we don't care
    }

    public T read() throws IllegalAccessException, InstantiationException, IOException {
        String line = reader.readLine();
        if (line == null) {
            return null;
        }

        T item = clazz.newInstance();
        String[] columns = line.split("\t");
        for (int column : columnMap.keySet()) {
            if (column >= columns.length) {
                continue; //split ignores empty columns at the end
            }
            String value = columns[column].trim();
            if (value.length() == 0) {
                continue; //skip empty values
            }
            Field typeField = columnMap.get(column);
            if (typeField.getType().equals(String.class)) {
                typeField.set(item, value);
            } else if (typeField.getType().equals(BigDecimal.class)) {
                BigDecimal bigDecimalValue = new BigDecimal(value);
                typeField.set(item, bigDecimalValue);
            }
        }
        return item;
    }

    public void close() throws IOException {
        reader.close();
    }

}
