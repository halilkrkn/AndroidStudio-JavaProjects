package com.example.retrofitcoinsjava.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    //Çekeceğimiz Api deki Keyleri tanımladık.
    @SerializedName("currency")
    public String currency;
    @SerializedName("price")
    public String price;
}
