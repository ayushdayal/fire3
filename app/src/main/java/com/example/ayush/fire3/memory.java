package com.example.ayush.fire3;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Ayush on 14-02-2018.
 */

public class memory extends AppCompatActivity {
    final public String TAG="dfs";
    final public String title="MEMORIES";
    ActionBar actionBar;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memories);
        setTitle(title);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.color.forgot));

        Log.d(TAG, "onCreate: after memo");
        downloadimage();
    }
    void downloadimage(){
        StorageReference pathrefrence=FirebaseStorage.getInstance().getReference("WIN_20180213_19_17_36_Pro.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        pathrefrence.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.d(TAG, "onSuccess: image downloaded");
                Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                ImageView imageView=findViewById(R.id.memories1);
                imageView.setImageDrawable(image);
                Log.d(TAG, "onSuccess: image set");
            }
            
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
}
