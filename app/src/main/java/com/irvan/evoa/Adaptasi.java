package com.irvan.evoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class Adaptasi extends PagerAdapter {
    int [] layouts;
    LayoutInflater Inflater;
    public Adaptasi(Context konteks, int [] layouts){
        this.layouts = layouts;
        this.Inflater = (LayoutInflater) konteks.getSystemService(konteks.LAYOUT_INFLATER_SERVICE);

    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = Inflater.inflate(layouts[position],container,false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
