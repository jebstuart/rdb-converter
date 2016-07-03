package com.jebware.sample;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

/**
 * Created by jware on 7/3/16.
 */
public interface FoodApi {

    @GET("/food")
    Call<List<Food>> getFoods();

}
