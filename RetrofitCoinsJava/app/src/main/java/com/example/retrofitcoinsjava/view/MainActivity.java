package com.example.retrofitcoinsjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.retrofitcoinsjava.R;
import com.example.retrofitcoinsjava.adapter.recyclerViewAdapter;
import com.example.retrofitcoinsjava.model.CryptoModel;
import com.example.retrofitcoinsjava.service.ICryptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //Data indirmek için ArrayList Oluşturdukk.
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    recyclerViewAdapter recyclerViewAdapter;
    //Hafızayı daha verimli kullanabilmek için RxJava ve Disposable kullanıyoruz.
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //https://api.nomics.com/v1/prices?key=39ca529f5af868a5279edfc90866f997

        recyclerView = findViewById(R.id.recyclerView);

        //Retrofit & Json
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loadDataApi();
    }
    //Servisi çekmek için oluşturduğumuz CryptoApi interface ini retrofitte tanımladık.
    private  void  loadDataApi(){

        ICryptoApi cryptoApi = retrofit.create(ICryptoApi.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(cryptoApi.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponse));


        /*
        Call<List<CryptoModel>> listCall = cryptoApi.getData();

        listCall.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {

                if (response.isSuccessful()){
                    List<CryptoModel> responseList = response.body(); // Crypto Model Listesi veriyor.
                    cryptoModels = new ArrayList<>(responseList);

                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter = new recyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

         */
    }

    private void handleResponse(List<CryptoModel> cryptoModelList) {
        cryptoModels = new ArrayList<>(cryptoModelList);


        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter = new recyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }

}
