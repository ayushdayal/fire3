package com.example.ayush.fire3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by Ayush on 15-02-2018.
 */

public class bustab extends AppCompatActivity {
    final public String TAG="bus tab";
    final public String title="BUS";
    ActionBar actionBar;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus);
        setTitle(title);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.color.forgot));
    }
    public void openbusdetail(View view){
        Intent intent= new Intent(this,busdetail.class);
        startActivity(intent);
    }
    public  void openbusloc(View view){
        Log.d(TAG, "openbusloc: bus locatio sent");
        Intent intent =new Intent(this,MapsActivity.class);
        startActivity(intent);
    } //
    public void opencall(View view){
        Intent intent=new Intent(this,popup.class);
        startActivity(intent);
    }  //
    public void feedback(View view){
        Intent intent=new Intent(this,feedback.class);
        startActivity(intent);
    }  //
    public void share(View view){
       /* ACTION_SEND: Deliver some data to someone else.
        createChooser (Intent target, CharSequence title): Here, target- The Intent that the user will be selecting an activity to perform.
            title- Optional title that will be displayed in the chooser.
        Intent.EXTRA_TEXT: A constant CharSequence that is associated with the Intent, used with ACTION_SEND to supply the literal data to be sent.
        //
        String s="share ed bu me";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,s);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);*/


        String latitude="27.9933";
        String longitude="28.1111";
        String uri = "geo:" + latitude + ","
                +longitude + "?q=" + latitude
                + "," + longitude;
        startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri)));
    }  //
}
