package com.example.littletableaum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> nameArray;
    ArrayList<String> artistArray;
   static ArrayList<Bitmap> imageArray;
   ArrayAdapter arrayAdapter;

    //Bağlanmak istenilen menü
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_art,menu);
        return super.onCreateOptionsMenu(menu);

    }

    // bağlanılan menüye tıklandığında
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if (item.getItemId() == R.id.add_art){
             Intent  intent = new Intent(getApplicationContext(),Main2Activity.class);
             intent.putExtra("info", "newArt");
             startActivity(intent);
         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        nameArray = new ArrayList<>();
        artistArray = new ArrayList<>();
        imageArray = new ArrayList<>();

        //ListView da sıralı gözükmesi için
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameArray);
        listView.setAdapter(arrayAdapter);

        //listView da tıklandığında yönlendirme işlemi
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("info","oldArt");
                intent.putExtra("name",nameArray.get(i));
                intent.putExtra("artist",artistArray.get(i));

                intent.putExtra("position",i);
                startActivity(intent);

            }
        });
        getData();

    }
    //Verilerin Tamamını Çekiyoruz ve MainActivity de.
    public  void getData(){

        SQLiteDatabase database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS arts (name VARCHAR, artist VARCHAR, image BLOB)");

        //rawquery yaparken;
        Cursor cursor = database.rawQuery("SELECT * FROM arts", null);

        int nameIndex = cursor.getColumnIndex("name");
        int artistIndex = cursor.getColumnIndex("artist");
        int imageIndex = cursor.getColumnIndex("image");


        while (cursor.moveToNext()){

            nameArray.add(cursor.getString(nameIndex));
            artistArray.add(cursor.getString(artistIndex));

            byte[] byteArray = cursor.getBlob(imageIndex);
            Bitmap image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
            imageArray.add(image);

            //ArrayAdapter
            arrayAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }



    @Override
    protected void onResume() {
        super.onResume();


    }
}
