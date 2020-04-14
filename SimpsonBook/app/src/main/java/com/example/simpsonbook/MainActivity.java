package com.example.simpsonbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        Simpson homer = new Simpson("Homer Simpson", "Nuclear", R.drawable.homer);
        Simpson lisa = new Simpson("Lisa Simpson", "Student", R.drawable.lisa);
        Simpson bart = new Simpson("Bart Simpson", "Student", R.drawable.bart);

        final ArrayList<Simpson> simpsonsList = new ArrayList<>();
        simpsonsList.add(homer);
        simpsonsList.add(lisa);
        simpsonsList.add(bart);

        CustomAdapter customAdapter = new CustomAdapter(simpsonsList,MainActivity.this);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("selectedSimpson",simpsonsList.get(i));
                startActivity(intent);





            }
        });



    }
}
