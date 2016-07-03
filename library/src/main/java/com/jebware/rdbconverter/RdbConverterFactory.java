package com.jebware.rdbconverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by jware on 6/29/16.
 * (c) 2016
 */
public class RdbConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            return new RdbResponseBodyConverter<>(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
