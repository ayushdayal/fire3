package com.example.ayush.fire3;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Ayush on 12-02-2018.
 */

public class getschooldetail extends AppCompatActivity {
    EditText schoolname;
    EditText classname;
    EditText section;
    EditText roolno;
    EditText studentid;
    final String TAG ="dd";
    schooldetail sd;
    ActionBar actionBar;
    public String upload_url;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schooldetail);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.color.forgot));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        schoolname=findViewById(R.id.schoolname);
        classname=findViewById(R.id.classname);
        section=findViewById(R.id.section);
        roolno=findViewById(R.id.roolno);
        studentid=findViewById(R.id.studentid);
    }
    public void uploaddetail(View view){
        sd = new schooldetail(
                schoolname.getText().toString(),
                section.getText().toString(),
                classname.getText().toString(),
                roolno.getText().toString(),
                studentid.getText().toString()
        );

        upload_url="http://103.68.217.42/SchoolDetail.php?u="+getuuid()+"scnme="+sd.getSchoolname()+"sc="+sd.getSection()+"cl="+sd.getClassname()+"rl="+sd.getRollno();
        Log.d(TAG, "uploaddetail: details uploaded"+upload_url);
        new signupserver().execute();
        Intent intent=new Intent(this,homepage.class);
        startActivity(intent);
    }
    // Todo what if connection failed add else on json reaponce
    // todo add validate for all strings
    String getuuid(){
        upload_url="http://103.68.217.42/getuuid.php?emial="+sd.getStudentid();
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(upload_url);
        return jsonStr;
    }
    public class signupserver extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {super.onPreExecute();
            Toast.makeText( getschooldetail.this,"uploading data",Toast.LENGTH_LONG).show();}

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(upload_url);
            Log.e(TAG, "Response from url: " + jsonStr);
            Toast.makeText(getschooldetail.this,jsonStr,Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {super.onPostExecute(result);}
    }


}
