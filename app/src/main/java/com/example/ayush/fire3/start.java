package com.example.ayush.fire3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Ayush on 10-03-2018.
 */

public class start extends AppCompatActivity {
    ProgressBar spinbar;
    int i=0;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.prifile3);

         Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
