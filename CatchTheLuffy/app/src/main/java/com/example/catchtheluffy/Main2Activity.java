package com.example.catchtheluffy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView =findViewById(R.id.textView3);


        Intent ıntent = getIntent();
        //Integer.parseInt(textView.getText().toString());
        String Arttir = ıntent.getStringExtra("ArttirInput");
        textView.setText(Arttir);

    }

    public void changeActivity2(View view){
        Intent ıntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(ıntent);
    }
}
