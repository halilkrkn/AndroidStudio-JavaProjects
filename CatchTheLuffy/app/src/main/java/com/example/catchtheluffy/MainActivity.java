package com.example.catchtheluffy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText;
    TextView scoreText;
    int arttir;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        //imageları bir dizi içerisine aldık.
        imageArray = new ImageView[]{imageView,imageView2,imageView3,
                imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};




        arttir = 0;

        hideImage(); // image ları yok etme methodu.

        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long time) { // Burada timeText e Geri Sayım yaptırdık.
                timeText.setText("Time: " + time/1000);
            }

            @Override
            public void onFinish() { // Geri Sayım bittiğinde image ların görünmemesini sağladık.
               timeText.setText("Time Out!!");
                handler.removeCallbacks(runnable);
                for (ImageView image: imageArray){
                    image.setVisibility(View.INVISIBLE);
                }

                // Oyunun Tekrar Başlaması için Alert oluşturduk.
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Tekrar Başla!");
                alertDialog.setMessage("Tekrar Başlasın mı?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = getIntent(); // Tekrar başlatma için 'çok da kullanılmayan' bu yöntemi kullandık.
                        finish();
                        startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("Ana Menüye Dön", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String arttir = scoreText.getText().toString();
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                       intent.putExtra("ArttirInput", arttir);
                       startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        }.start();
    }

    public void tikla(View view){ // Burada her imagelara tıkladığımızda score in artmasını sağladık.

        arttir++;
        scoreText.setText("Score: "+ arttir);

    }
    public void hideImage(){ // Burada imageların kaybolmasını sağladık.

        //Handler ve runnable ile imageların belli bir saniye kaybolmasını ve hareket etmesini sağladık.
         handler = new Handler();
         runnable = new Runnable() {
             @Override
             public void run() {
                 for (ImageView image: imageArray){
                     image.setVisibility(View.INVISIBLE);
                 }

                 //Random sınıfını kullanarak imagelerin rastgele hareket etmesini sağladık.
                 Random random = new Random();
                 int rast = random.nextInt(9);
                 imageArray[rast].setVisibility(View.VISIBLE);

                 // Bu kısımda imagelerin istenilen saniye de hareket etmesini sağladık.
                 // Yani imagelerin kaybolup ortaya çıkma hızını ayarladık.
                 handler.postDelayed(this,500);
             }
         };

            handler.post(runnable);

    }
}
