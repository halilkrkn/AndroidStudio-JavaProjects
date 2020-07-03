package com.example.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    ListView listView;
    PostClass adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> userEmailFromFB;
    ArrayList<String> userImageFromFB;
    ArrayList<String> userCommentFromFB;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
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
       }else if (item.getItemId() == R.id.sign_out){
           firebaseAuth.signOut();
           Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
        firebaseFirestore = FirebaseFirestore.getInstance();

        adapter = new PostClass(userEmailFromFB,userCommentFromFB,userImageFromFB,this);
        listView.setAdapter(adapter);

        //REALTIME DATABASE FONKSİYONU
      //  getDataFromFirebase();

        //FIRESTORE DATABASE FONKSİYONU
       getDataFromFireStore();
    }

    //REALTIME DATABASE DE VERİLERİ ÇEKME.
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

    //FIRESTORE DATABASE DE VERİLERİ ÇEKME
    public void getDataFromFireStore(){

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("Posts");

        collectionReference.orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                    Map<String,Object> data = documentSnapshot.getData();
                    String comment =(String) data.get("comment");
                    String userEmail = (String) data.get("userEmail");
                    String downloadURL = (String) data.get("downloadURL");

                    userEmailFromFB.add(userEmail);
                    userImageFromFB.add(downloadURL);
                    userCommentFromFB.add(comment);
                    adapter.notifyDataSetChanged();


                }
            }
        });
    }


}
