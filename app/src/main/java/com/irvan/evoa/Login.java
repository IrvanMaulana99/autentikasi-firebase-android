package com.irvan.evoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Login extends AppCompatActivity {
    EditText mPTxt2, mPTxt3, ResetEmail;
    Button mBtnLogin;
    TextView mTxtLogin, mTxtPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPTxt2 = findViewById(R.id.PTxt2);
        mPTxt3 = findViewById(R.id.PTxt3);
        mAuth = FirebaseAuth.getInstance();
        mBtnLogin = findViewById(R.id.BtnLogin2);
        mTxtLogin = findViewById(R.id.TxtLogin2);
        mTxtPass = findViewById(R.id.TxtPass);


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mPTxt2.getText().toString().trim();
                String password = mPTxt3.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mPTxt2.setError("Masukkan Email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPTxt3.setError("Masukkan Password");
                    return;
                }
                if (password.length() < 4) {
                    mPTxt3.setError("Password harus memiliki lebih dari 4 huruf");
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(Login.this, "Login telah berhasil", Toast.LENGTH_SHORT).show();
                         EditText surel = (EditText) findViewById(R.id.PTxt2);
                         String Txt1 = surel.getText().toString();

                         EditText pw = (EditText) findViewById(R.id.PTxt3);
                         String Txt2 = pw.getText().toString();


                         startActivity(new Intent(getApplicationContext(), MainActivity.class));
                     }else{
                         Toast.makeText(Login.this, "Email atau Password salah !", Toast.LENGTH_SHORT).show();
                     }
                    }
                });
            }
        });
        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Daftar.class));
            }
        });
        mTxtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText TxtPass = new EditText(v.getContext());
                final AlertDialog.Builder DialogPassword = new AlertDialog.Builder(v.getContext());
                DialogPassword.setTitle("Apakah anda ingin Reset Password ?");
                DialogPassword.setMessage("Masukkan email yang terhubung ke aplikasi untuk mereset email !");
                DialogPassword.setView(TxtPass);

                DialogPassword.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String em = TxtPass.getText().toString();
                        mAuth.sendPasswordResetEmail(em).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Link untuk Reset telah dikirim ke Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error ! Alamat reset tidak terkirim"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                DialogPassword.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                DialogPassword.create().show();
            }
        });
    }
}
