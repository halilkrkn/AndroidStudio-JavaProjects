package com.example.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FeedActivity extends AppCompatActivity {
    //Menunun bu activity e  bağlandığı yer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Menudeki seçince ne olacağı
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //Menudeki add_post seçimi için yaptık. diğerleri içinde bu şekilde yapılabilir.
       if (item.getItemId() == R.id.add_post){
           Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
           startActivity(intent);
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }
}
