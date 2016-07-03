package com.jebware.sample;

import com.jebware.rdbconverter.RdbReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by jware on 7/3/16.
 */
public class RdbConverterSampleDisk {

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

        InputStream is = new FileInputStream("foods.rdb");

        RdbReader<Food> reader = new RdbReader<Food>(is, Food.class);

        Food food;
        while ((food = reader.read()) != null) {
            System.out.printf(Locale.US,
                    "%s has a deliciousness rating of %s\n",
                    food.name,
                    food.deliciousnessRating.toPlainString());
        }

    }

}
