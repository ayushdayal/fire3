package com.example.ayush.fire3;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Ayush on 14-02-2018.
 */

public class feedback extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        String title="FEEDBACK";
        setTitle(title);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.color.forgot));
        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.feeddrop);
//create a list of items for the spinner.
        String[] items = new String[]{"Driver", "Helper", "Location"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
}
