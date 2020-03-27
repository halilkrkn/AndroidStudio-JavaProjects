package com.example.gezginiyee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    ImageView ımageView;
    TextView gezginNameText;
    TextView countryNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ımageView = findViewById(R.id.imageView);
        gezginNameText = findViewById(R.id.gezginNameText);
        countryNameText = findViewById(R.id.countryNameText);

        Intent intent = getIntent();
        String gezginName = intent.getStringExtra("name");
        String countryName= intent.getStringExtra("country");
        gezginNameText.setText(gezginName);
        countryNameText.setText(countryName);

        Singleton singleton = Singleton.getInstance();
        ımageView.setImageBitmap(singleton.getChosenImage());


    }
}
