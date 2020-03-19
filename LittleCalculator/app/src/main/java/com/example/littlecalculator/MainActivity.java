package com.example.littlecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    EditText editText;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         editText =  findViewById(R.id.editText);
         editText2 = findViewById(R.id.editText2);
         resultText = findViewById(R.id.textView);




    }

    public void Toplama(View view){

        if(editText.getText().toString().matches("") || editText2.getText().toString().matches("")){
            resultText.setText("Boş Alanları Giriniz!!");
        }
        else{
            int a = Integer.parseInt(editText.getText().toString());
            int b = Integer.parseInt(editText2.getText().toString());

            int resultInteger = a + b;

            resultText.setText("Sonuç: " + resultInteger);
        }



    }
    public void Cikarma(View view){
        if(editText.getText().toString().matches("") || editText2.getText().toString().matches("")){
            resultText.setText("Boş Alanları Giriniz!!");
        }else {
            int a = Integer.parseInt(editText.getText().toString());
            int b = Integer.parseInt(editText2.getText().toString());

            int resultInteger = a - b;

            resultText.setText("Sonuç: " + resultInteger);
        }
    }

    public void Carpma(View view){
        if(editText.getText().toString().matches("") || editText2.getText().toString().matches("")){
            resultText.setText("Boş Alanları Giriniz!!");
        }else {
            int a = Integer.parseInt(editText.getText().toString());
            int b = Integer.parseInt(editText2.getText().toString());

            int resultInteger = a * b;

            resultText.setText("Sonuç: " + resultInteger);
        }
    }
    public void Bolme(View view){
        if(editText.getText().toString().matches("") || editText2.getText().toString().matches("")){
            resultText.setText("Boş Alanları Giriniz!!");
        }else {
            int a = Integer.parseInt(editText.getText().toString());
            int b = Integer.parseInt(editText2.getText().toString());

            int resultInteger = a / b;

            resultText.setText("Sonuç: " + resultInteger);
        }
    }
}
