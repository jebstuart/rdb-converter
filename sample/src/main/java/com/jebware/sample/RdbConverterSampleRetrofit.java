package com.jebware.sample;

import com.jebware.rdbconverter.RdbConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by jware on 7/3/16.
 */
public class RdbConverterSampleRetrofit {

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(new RdbConverterFactory())
                .build();

        FoodApi foodApi = retrofit.create(FoodApi.class);

        Response<List<Food>> response;
        try {
            response = foodApi.getFoods().execute();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return;
        }

        for (Food food : response.body()) {
            System.out.printf(Locale.US,
                    "%s has a deliciousness rating of %s\n",
                    food.name,
                    food.deliciousnessRating.toPlainString());
        }

        System.exit(0);
    }

}
