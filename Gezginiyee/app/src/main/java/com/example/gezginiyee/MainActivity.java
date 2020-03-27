package com.example.gezginiyee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView  listView;

    //static her yerden ulaşılır anlamında kullanılır. ama çok tercih eldilen bir yöntem değildir.
    // Singleton tek bir obje tek bir instance a sahip anlamındadır.

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        //Data
        final ArrayList<String> gezginList = new ArrayList<>();
        gezginList.add("Galata");
        gezginList.add("Eiffel");
        gezginList.add("Colosseo");
        gezginList.add("London Bridge");

        final ArrayList<String> countryNames = new ArrayList<>();
        countryNames.add("Türkiye");
        countryNames.add("Fransa");
        countryNames.add("İtalya");
        countryNames.add("İngiltere");

        //İndirdiğimiz image ları bir değişkene atamamız gerekiyor. Bir obje olarak tanımlamalıyız.
        // Böyle yapabilmemiz için ise Bitmap kullanılır.

        //Bitmap Data
        Bitmap galata = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.galata);
        Bitmap eiffel = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.eiffel);
        Bitmap colosseo = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.colosseo);
        Bitmap londonbridge = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.londonbridge);

        final ArrayList<Bitmap> gezginImages = new ArrayList<>();
        gezginImages.add(galata);
        gezginImages.add(eiffel);
        gezginImages.add(colosseo);
        gezginImages.add(londonbridge);



        //ListView
        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1,gezginList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // System.out.println(gezginList.get(i));
                //System.out.println(countryNames.get(i));

                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("name",gezginList.get(i));
                intent.putExtra("country",countryNames.get(i));
                Singleton singleton = Singleton.getInstance();
                singleton.setChosenImage(gezginImages.get(i));

                startActivity(intent);

            }
        });



    }
}
