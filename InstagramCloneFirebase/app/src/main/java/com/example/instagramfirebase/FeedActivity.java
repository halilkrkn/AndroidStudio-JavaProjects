package com.example.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {

    ListView listView;
    PostClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> userImageFromFB;
    ArrayList<String> userCommentFromFB;

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

        listView = findViewById(R.id.listView);

        userEmailFromFB = new ArrayList<String>();
        userCommentFromFB = new ArrayList<String>();
        userImageFromFB = new ArrayList<String>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        adapter = new PostClass(userEmailFromFB,userCommentFromFB,userImageFromFB,this);
        listView.setAdapter(adapter);
        getDataFromFirebase();
    }

    public void getDataFromFirebase(){
        DatabaseReference newRefence = firebaseDatabase.getReference("Posts");
        newRefence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // System.out.println("FBV children: " + snapshot.getChildren());
                // System.out.println("FBV key: " + snapshot.getKey());
                // System.out.println("FBV Value: " + snapshot.getValue());
                // System.out.println("FBV Priority: " + snapshot.getPriority());
                // System.out.println("FBV childrenCount: " + snapshot.getChildrenCount());
           for (DataSnapshot ds : snapshot.getChildren()) {
               // System.out.println("FBV ds value"+ ds.getValue());

               HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
               //System.out.println("FBV userEmail"+ hashMap.get("userEmail"));

               userEmailFromFB.add(hashMap.get("User Email"));
               userCommentFromFB.add(hashMap.get("Comment"));
               userImageFromFB.add(hashMap.get("DownloadURL"));
               adapter.notifyDataSetChanged();


           }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
