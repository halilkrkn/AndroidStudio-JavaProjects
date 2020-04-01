            package com.example.littletableaum;

            import androidx.annotation.NonNull;
            import androidx.annotation.Nullable;
            import androidx.appcompat.app.AppCompatActivity;
            import androidx.core.app.ActivityCompat;
            import androidx.core.content.ContextCompat;

            import android.Manifest;
            import android.content.Intent;
            import android.content.pm.PackageManager;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteStatement;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.graphics.ImageDecoder;
            import android.net.Uri;
            import android.os.Build;
            import android.os.Bundle;
            import android.provider.MediaStore;
            import android.view.View;
            import android.widget.Button;
            import android.widget.EditText;
            import android.widget.ImageView;

            import java.io.ByteArrayOutputStream;
            import java.io.IOException;

            import static com.example.littletableaum.MainActivity.imageArray;

            public class Main2Activity extends AppCompatActivity {
                EditText nameEditText;
                EditText artistEditText;
                ImageView imageView;
                Button saveButton;
                SQLiteDatabase database;
                Bitmap  selectedImage;
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main2);

                    imageView = findViewById(R.id.imageView2);
                    nameEditText = findViewById(R.id.nameEditText);
                    artistEditText = findViewById(R.id.artistEditText2);
                    saveButton = findViewById(R.id.saveButton);

                    Intent  intent = getIntent();
                   String info = intent.getStringExtra("info");
                    if (info.matches("newArt")){
                        nameEditText.setText("");
                        artistEditText.setText("");
                        Bitmap selectBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                R.drawable.select);
                        imageView.setImageBitmap(selectBitmap);
                        saveButton.setVisibility(View.VISIBLE);
                    }else{
                        saveButton.setVisibility(View.INVISIBLE);
                        String name = intent.getStringExtra("name");
                        String artist = intent.getStringExtra("artist");
                        Bitmap image = imageArray.get(intent.getIntExtra("position",0));

                        nameEditText.setText(name);
                        nameEditText.setText(artist);
                        imageView.setImageBitmap(image);

                    }
                }

                //Verileri Kaydeetme
                public  void save(View view){
                    String name = nameEditText.getText().toString();
                    String artist = artistEditText.getText().toString();

                    ByteArrayOutputStream outputStream =  new ByteArrayOutputStream();

                    //YÜklenen ımage ı boyutunu ve kalitesini belirledik.
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 25,outputStream);

                    byte[] byteArray = outputStream.toByteArray();


                    try {

                        database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
                        database.execSQL("CREATE TABLE IF NOT EXISTS arts (name VARCHAR, artist VARCHAR, image BLOB)");
                        String sqlString = "INSERT INTO arts (name, artist, image) VALUES (?,?,?)";
                        SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);

                        // Üç tane soru işareti için bağlama yapıyoruz.
                        sqLiteStatement.bindString(1,name);
                        sqLiteStatement.bindString(2,artist);
                        sqLiteStatement.bindBlob(3,byteArray);
                        sqLiteStatement.execute();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    /*
                       Burada da MainActivity deki değişkenleri static yaptığımızda;
                    *  nameArray.add(name);
                    * artistArray.add(artist);
                    * imageArrayy.add(selectedImage);
                    * arrayAdapter.notifyDataSetChanged();
                    * */

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                  //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                  //  finish(); // bu Aktivitedeki her şeyi yapıyor ve önceki aktiviteye gönderiyor.



                }

                public void selectImage(View view){

                    // Galeriye İzin İşlemleri
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);

                    }else{
                        //Kullanıcı Galerisine Ulaşma
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,1);
                    }


                }

                //Kullanıcı Galerisine Ulaşma2
                @Override
                protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

                    if (requestCode ==1 && resultCode == RESULT_OK && data != null){
                       Uri imageData = data.getData();
                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                                ImageDecoder.Source  source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                imageView.setImageBitmap(selectedImage);
                            }else{
                                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                                imageView.setImageBitmap(selectedImage);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    super.onActivityResult(requestCode, resultCode, data);
                }

                @Override
                public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

                    if (requestCode == 2){
                        //İzni Almış olduk.
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                            //İzni Aldıktan Sonra Tekrar Galeriye Ulaşma
                            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,1);
                        }
                    }

                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
