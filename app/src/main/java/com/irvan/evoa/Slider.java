package com.irvan.evoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;

public class Slider extends AppCompatActivity {
    ViewPager Vpager;
    Button BtnSldAwal;
    int [] layouts;
    Adaptasi adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // hilangkan ActionBAR

        setContentView(R.layout.activity_slider);

        Vpager = findViewById(R.id.pager);
        BtnSldAwal = findViewById(R.id.BtnSldAwal);

        layouts = new int[] {
                R.layout.slidersatu,
                R.layout.sliderdua,
                R.layout.slider3,
        };

       adapter = new Adaptasi(this,layouts);
       Vpager.setAdapter(adapter);
        BtnSldAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Vpager.getCurrentItem()+1 < layouts.length){
                Vpager.setCurrentItem(Vpager.getCurrentItem()+1);
            }else {
                    startActivity(new Intent(getApplicationContext(),Daftar.class));
                // menuju ke Activity Daftar
        }


    }
});
        Vpager.addOnPageChangeListener(lihatPagerListener);
}
        ViewPager.OnPageChangeListener lihatPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == layouts.length - 1) {
                BtnSldAwal.setText("Continue");
            }else {
                BtnSldAwal.setText("Next");
            }
        }
            @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
