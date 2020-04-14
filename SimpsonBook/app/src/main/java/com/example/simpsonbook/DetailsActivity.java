package com.example.simpsonbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView nameText;
    TextView jobText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.imageView);
        nameText = findViewById(R.id.nameText);
        jobText = findViewById(R.id.jobText);

        Intent intent = getIntent();
        Simpson selectedSimpson = (Simpson) intent.getSerializableExtra("selectedSimpson");


        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),selectedSimpson.getPictureInteger());
        imageView.setImageBitmap(bitmap);

        nameText.setText(selectedSimpson.getName());
        jobText.setText(selectedSimpson.getJob());
    }
}
