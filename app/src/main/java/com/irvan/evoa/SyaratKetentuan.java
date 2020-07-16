package com.irvan.evoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SyaratKetentuan extends AppCompatActivity {
    Button BtnKembali3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syarat_ketentuan);

        BtnKembali3 = findViewById(R.id.BtnKembali3);
        BtnKembali3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Daftar.class));
                finish();
            }
        });

    }
}
