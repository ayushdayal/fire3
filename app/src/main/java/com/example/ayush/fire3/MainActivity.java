package com.example.ayush.fire3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

    public FirebaseAuth mAuth;
    public String upload_url;

    private static final String TAG = "EmailPassword";

    public TextView loginbutton;
    public AutoCompleteTextView loginusername;
    public AutoCompleteTextView loginpassword;
    public TextView login2signup;

    TextView sdsf;
    public TextView gropass;
    public TextView titled;
    public TextView signup2in;

    String[] ids ={"ayush@kumar.com","love@me.hard"};
    String[] pass={"ayush12345","na"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        loginbutton=findViewById(R.id.loginviaInternl);
        loginusername=findViewById(R.id.loginusername);
        loginpassword=findViewById(R.id.loginpassword);
        login2signup=findViewById(R.id.textsignup);
        sdsf=findViewById(R.id.out);
        gropass=findViewById(R.id.forpass);
        titled=findViewById(R.id.titled);
        signup2in=findViewById(R.id.signup2in);

        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,ids);
        loginusername.setAdapter(adapter);
        ArrayAdapter<String> adapterpass= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,pass);
        loginpassword.setAdapter(adapterpass);


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){gohome();}
    }
    public void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        upload_url="http://103.68.217.42/app2server.php?u="+"nnh7"+"&p="+"hjhv";
        Log.e(TAG, "Response from url: " + upload_url);
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(upload_url);
        Log.e(TAG, "Response from url: " + jsonStr);
        Toast.makeText(MainActivity.this,jsonStr,Toast.LENGTH_SHORT).show();

        showProgressDialog();

        //[START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "created Successfully",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
        Intent intent= new Intent(this,getschooldetail.class);
        startActivity(intent);
    }



    private void signIn(final String email, final String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "signin Successfully",
                                    Toast.LENGTH_SHORT).show();
                            signinserver("as","asd");
                            gohome();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            gohome();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
      //  signinserver(email,password);
    }
    void signinserver(String e,String p){
       // upload_url="http://103.68.217.42/app_login.php?u=12345";
       // HttpHandler sh = new HttpHandler();
       // String jsonStr = sh.makeServiceCall(upload_url);
       // Log.e(TAG, "Response from url: " + jsonStr);
       // Toast.makeText(MainActivity.this,jsonStr,Toast.LENGTH_SHORT).show();
        new getresponce().execute();
        gohome();
    }

    void gohome(){
        Intent intent=new Intent(this,homepage.class);
        startActivity(intent);
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = loginusername.getText().toString();
        if (TextUtils.isEmpty(email)) {
            loginusername.setError("Required.");
            valid = false;
        } else {
            loginusername.setError(null);
        }

        String password = loginpassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            loginpassword.setError("Required.");
            valid = false;
        } else {
            loginpassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {

        } else {

        }
    }

    public void loginbutton(View view){
        signIn(loginusername.getText().toString(), loginpassword.getText().toString());
    }
    public void login2signupbutton(View view){
      sdsf.setVisibility(View.VISIBLE);
      signup2in.setVisibility(View.VISIBLE);
      loginbutton.setVisibility(View.GONE);
      login2signup.setVisibility(View.GONE);
      gropass.setVisibility(View.GONE);
      String signu="SIGN UP";
      titled.setText(signu);

    }
    public void signup2in(View view){
        sdsf.setVisibility(View.INVISIBLE);
        signup2in.setVisibility(View.INVISIBLE);
        loginbutton.setVisibility(View.VISIBLE);
        login2signup.setVisibility(View.VISIBLE);
        gropass.setVisibility(View.VISIBLE);
        String signu="LOG IN";
        titled.setText(signu);
    }
    public void signupbutton(View view){
        createAccount(loginusername.getText().toString(), loginpassword.getText().toString());
    }
    private class getresponce extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            upload_url="http://103.68.217.42/app_login.php?u=12345";
            String jsonStr = sh.makeServiceCall(upload_url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String value=jsonObj.getString("username");
                    Log.d(TAG, "doInBackground: "+value);


                    // Getting JSON Array node
                    // JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                   /* for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }*/
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }

}
