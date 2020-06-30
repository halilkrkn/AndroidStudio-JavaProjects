package com.example.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText EmailTextView;
    EditText PasswordTextView;
    Button signInButton;
    Button signUpButton;
    String EmailText,PasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmailTextView = findViewById(R.id.EmailTextView);
        PasswordTextView =  findViewById(R.id.PasswordTextView);

        //Firebase Uygulamamıza import ettik.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Giriş sayfasındaki kayıtlı kullanıcıyı tanıyarak otomatik girmesi sağladık.
        if (currentUser != null){
            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
            startActivity(intent);
        }
    }

    //Login İşlemlerini Yaptık.
    public void signIn (View view){
        EmailText = EmailTextView.getText().toString();
        PasswordText = PasswordTextView.getText().toString();
        //Hem şifreyi hemde emaili üzerinden giriş yaptırdık.
        mAuth.signInWithEmailAndPassword(EmailText,PasswordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);


                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Kullanıcı Kayıt İşlemleri Yapıldı.
    public void signUp (View view){

        EmailText = EmailTextView.getText().toString();
        PasswordText = PasswordTextView.getText().toString();
        //Email ve Şifre Üzerinden Kayıt İşlemi Yapıldı.
        mAuth.createUserWithEmailAndPassword(EmailText,PasswordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override // HATA YOKSA
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"User Created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                            startActivity(intent);
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override // HATA OLURSA
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
