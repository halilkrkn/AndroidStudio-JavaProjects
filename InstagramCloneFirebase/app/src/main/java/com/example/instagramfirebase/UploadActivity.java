package com.example.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Script;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    ImageView postImage;
    EditText postCommentText;
    Uri imageData;
    //Storage Depo kısmı yani video ve imagelerin toplandığı kısım.
    private StorageReference firebaseStorageRef;
    //Kimlik EŞleştirme.
    private FirebaseAuth mAuth;
    //Database
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //Firebase FireStore Coloud Veri Tabanına Başlanmak için.
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        postCommentText = findViewById(R.id.postCommentView);
        postImage = findViewById(R.id.postImageView);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        firebaseStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public  void upload (View view){
        if (imageData != null) {

            UUID uuid = UUID.randomUUID();
            final String imageName = "images/"+ uuid + ".jpg";
            final String postsId = uuid.toString();
            firebaseStorageRef.child(imageName).putFile(imageData).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                //Download URL i database ekleme.
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                  newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {

                          String downloadUrl = uri.toString();
                          FirebaseUser user = mAuth.getCurrentUser();
                          String userEmail = user.getEmail();
                          String userComment = postCommentText.getText().toString();
                          Date date = new Date();
                          final String getDate = date.toString();

                          HashMap<String,Object> postData = new HashMap<>();
                          postData.put("userEmail", userEmail);
                          postData.put("comment", userComment);
                          postData.put("downloadURL", downloadUrl);
                          postData.put("date", FieldValue.serverTimestamp());

                          firebaseFirestore.collection("Posts").add(postData).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                              @Override
                              public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(UploadActivity.this, "Upload Shared", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                                    startActivity(intent);
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(UploadActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                              }
                          });

                            //FIREBASE REALTIME DATABASE
                          //myRef.child("Posts").child(postsId).child("downloadUrl:").setValue(downloadUrl);
                          //myRef.child("Posts").child(postsId).child("userEmail:").setValue(userEmail);
                         // myRef.child("Posts").child(postsId).child("userComment:").setValue(userComment);
                         // myRef.child("Posts").child(postsId).child("Date:").setValue(getDate);

                          //Toast.makeText(UploadActivity.this, "Upload Shared", Toast.LENGTH_SHORT).show();

                          //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                          //startActivity(intent);
                      }
                  });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
    //Galeriden Resim Seçme İşlemleri Yapıldı.
    public  void postSelectImage (View view){

        //Galeri ve İzin için işlemler
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageData = data.getData();
            try{
                if (Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                    postImage.setImageBitmap(bitmap);
                }else{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    postImage.setImageBitmap(bitmap);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
