package com.example.retrofitcoinsjava.service;

import com.example.retrofitcoinsjava.model.CryptoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICryptoApi {

    //GET,POST,UPDATE,DELETE
    @GET("prices?key=39ca529f5af868a5279edfc90866f997")
    Call<List<CryptoModel>> getData();


}
