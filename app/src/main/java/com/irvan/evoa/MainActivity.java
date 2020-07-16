package com.irvan.evoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView ProfilNama, ProfilEmail, profilHp, VerifikasiTxt;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    String userid;
    Button VerifBtn;
    CardView CardV1, CardV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profilHp = findViewById(R.id.ProfHp);
        ProfilNama = findViewById(R.id.ProfNama);
        ProfilEmail = findViewById(R.id.ProfEml);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        VerifBtn = findViewById(R.id.VerifBtn);
        VerifikasiTxt = findViewById(R.id.VerifikasiTxt);
        CardV1 = findViewById(R.id.CardV1);
        CardV2 = findViewById(R.id.CardV2);
        CardV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TentangAplikasi.class));
                finish();
            }
        });
        CardV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Tentang.class));
                finish();
            }
        });
        userid = mAuth.getCurrentUser().getUid();
        final FirebaseUser pemakai = mAuth.getCurrentUser();
        if (!pemakai.isEmailVerified()){
            VerifikasiTxt.setVisibility(View.VISIBLE);
            VerifBtn.setVisibility(View.VISIBLE);

            VerifBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    pemakai.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Email untuk Verifikasi Telah Dikirim", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email tidak Terkirim"+ e.getMessage());
                        }
                    });
                }
            });
        }

        DocumentReference documentReference = mStore.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                profilHp.setText(documentSnapshot.getString("Hp"));
                ProfilNama.setText(documentSnapshot.getString("NamaL"));
                ProfilEmail.setText(documentSnapshot.getString("email"));
            }
        });

        Intent intent = getIntent();
    }




    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
