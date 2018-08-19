package com.example.ayush.fire3;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ayush on 15-02-2018.
 */

public class location extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        setTitle("MAP");
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.color.forgot));
    }
}
