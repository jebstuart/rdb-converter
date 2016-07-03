package com.jebware.rdbconverter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jware on 6/29/16.
 * (c) 2016
 */
class RdbResponseBodyConverter<T> implements Converter<ResponseBody, List<T>> {

    private Class parameterClass;

    public RdbResponseBodyConverter(Type type) throws ClassNotFoundException {
        if (!(type instanceof ParameterizedType) || ((ParameterizedType)type).getActualTypeArguments().length != 1) {
            throw new IllegalArgumentException("RdbResponseBodyConverter only works for List<Object> types");
        }
        Type parameterType = ((ParameterizedType)type).getActualTypeArguments()[0];
        if (!(parameterType instanceof Class)) {
            throw new IllegalArgumentException("RdbResponseBodyConverter only works for List<Object> types");
        }
        parameterClass = (Class) parameterType;
    }

    @Override
    public List<T> convert(ResponseBody value) throws IOException {
        RdbReader<T> reader = new RdbReader<>(value.byteStream(), parameterClass);
        List<T> list = new ArrayList<>();
        T item;
        try {
            while ((item = reader.read()) != null) {
                list.add(item);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IOException("unable to read lines of rdb data");
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IOException("unable to read lines of rdb data");
        }
        reader.close();

        return list;
    }

}
