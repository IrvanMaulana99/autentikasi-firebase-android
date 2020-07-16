package com.irvan.evoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Daftar extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mPTxt1, mPTxt2, mPTxt3, mPTxt4;
    Button mBtnDaftar;
    TextView mTxtLogin, SyaratK;
    private FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        //Variabel
        mPTxt1 = findViewById(R.id.PTxt1);
        mPTxt2 = findViewById(R.id.PTxt2);
        mPTxt3 = findViewById(R.id.PTxt3);
        mPTxt4 = findViewById(R.id.PTxt4);
        mBtnDaftar = findViewById(R.id.BtnLogin2);
        mTxtLogin = findViewById(R.id.TxtLogin2);
        SyaratK = findViewById(R.id.SyaratK);

        // Method Firebase
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        // Action Untuk Button Daftar
        mBtnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mPTxt2.getText().toString().trim();
                String password = mPTxt3.getText().toString().trim();
                final String namalengkap = mPTxt1.getText().toString();
                final String hp = mPTxt4.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mPTxt2.setError("Masukkan Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPTxt3.setError("Masukkan Password");
                    return;
                }
                if (password.length() <= 4) {
                    mPTxt3.setError("Password harus memiliki lebih dari 4 huruf");
                    return;
                }
                            // Proses Daftar akun di Firebase
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Kirim email verifikasi
                            FirebaseUser pemakai = mAuth.getCurrentUser();
                            pemakai.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                Toast.makeText(Daftar.this, "Email untuk Verifikasi Telah Dikirim", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email tidak Terkirim"+ e.getMessage());
                                }
                            });
                            Toast.makeText(Daftar.this, "Akun telah dibuat", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("NamaL",namalengkap);
                            user.put("email", email);
                            user.put("Hp",hp);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Profil akun telah terbuat untuk " + userID);
                                }
                                }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Daftar.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        SyaratK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SyaratKetentuan.class));
            }
        });
        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}