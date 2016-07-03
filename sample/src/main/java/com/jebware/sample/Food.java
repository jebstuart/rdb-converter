package com.jebware.sample;

import com.jebware.rdbconverter.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by jware on 7/3/16.
 */
public class Food {

    public String name;

    @SerializedName("del_rtng")
    public BigDecimal deliciousnessRating;

}
