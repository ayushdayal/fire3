package com.example.ayush.fire3;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.fire3.models.BaseCalendarEvent;
import com.example.ayush.fire3.models.CalendarEvent;
import com.example.ayush.fire3.models.DayItem;
import com.example.ayush.fire3.models.IDayItem;
import com.example.ayush.fire3.models.IWeekItem;
import com.example.ayush.fire3.models.WeekItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ayush on 10-02-2018.
 * todo : homepage has been made start intent , move it back to start.class after completion
 */

public class homepage extends AppCompatActivity implements CalendarPickerController {
    android.support.v7.app.ActionBar actionBar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int i = -2;
    DatabaseReference myRef2;
    private ProgressBar spinbar;
    public int pStatus = 0;
    private Handler handler = new Handler();
    int attPer = 90;
    private static String url_all_products = "http://103.68.217.42/app_login.php?u=12345";
    String url = "http://192.168.0.151/app_login.php";

    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    /**
     * declaration 0f all variables for profile
     * are not used in this version of app
     **/
    String studdentName = null;
    String studentId = null;
    String studentClass = null;
    Integer studentAttendance = 0;
    Integer studentWeight = 0;
    Integer studentHeight = 0;
    Integer studentAge = 0;
    double pictureid = 0;

    TextView studentNameTv;
    TextView studentIdTv;
    TextView studentClassTv;
    TextView studentAttendanceTv;
    TextView studentWeightTv;
    TextView studentHeightTv;
    TextView studentAgeTv;
    ImageView studentpictureidTv;


    //bottambar listner
    // todo title setting has been remoeved from here
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // actionBar.setTitle("SCHOOLWARE");
                    mViewPager.setCurrentItem(0);
                    updateCurLoc(lastClassLoc);
                    return true;
/*
                    before doing anything you must make the page shift to correct postion otherwise it will produse null pointer exeption
*/
                case R.id.navigation_notifications:
                   // actionBar.setTitle("NOTIFICATION");
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_schedule:
                    //actionBar.setTitle("SCHEDULE");
                    mViewPager.setCurrentItem(2);
                    bindd();
                    // populatetabhost();
                    return true;
                /*bindd() here populate the calendar showing in 3 page */
                case R.id.navigation_profile:
                    //actionBar.setTitle("PROFILE");
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    TextView classloc;
    String classname = "null";
    final String TAG = "sdfd";
    String lastClassLoc = null;
    TabHost TabHostWindow;

    /*variables used by calendae api*/
    List<CalendarEvent> readyEvents;
    List<IDayItem> readyDays;
    List<IWeekItem> readyWeeks;

    @Bind(R.id.agenda_calendar_view)
    AgendaCalendarView mAgendaCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(getDrawable(R.color.forgot));

       // todo setting title has been removed here String asd = "SchoolWare";
       // setTitle(asd);

        setContentView(R.layout.main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        classloc = findViewById(R.id.classloc);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("curloc");
        database.getReference("curloc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");
                classname = dataSnapshot.getValue(String.class);
                lastClassLoc = classname;
                sendnotification(classname);
                mViewPager.setCurrentItem(0);
                updateCurLoc(classname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read app title value.");
            }
        });

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
        // viewpager page change listner
        // as you change page you must change the bottam bar selelted icon also
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_notifications);
                        break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_schedule);
                        break;
                    case 3:
                        navigation.setSelectedItemId(R.id.navigation_profile);
                        showAttendance();
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    void bindd() {
        //if(i==1){
        ButterKnife.bind(this);

        // minimum and maximum date of our calendar
        // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);
        // Sync way
        /*
        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());
        */
        //Async way


        //////// This can be done once in another thread todo  : a new threead has been created
        Runnable runnable = new Runnable() {
            public void run() {

            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        ////////
        CalendarManager calendarManager;
        calendarManager = CalendarManager.getInstance(getApplicationContext());
        calendarManager.buildCal(minDate, maxDate, Locale.getDefault(), new DayItem(), new WeekItem());
        calendarManager.loadEvents(eventList, new BaseCalendarEvent());
        readyEvents = calendarManager.getEvents();
        readyDays = calendarManager.getDays();
        readyWeeks = calendarManager.getWeeks();


        mAgendaCalendarView.init(Locale.getDefault(), readyWeeks, readyDays, readyEvents, this);
        mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());

        View view = mViewPager.getFocusedChild();
        //i++;}// todo change here added if conditon and else
        //else{
        //do nothing;
        //}


    }
    // endregion

    // region Interface - CalendarPickerController

    @Override
    public void onDaySelected(IDayItem dayItem) {
        Log.d(LOG_TAG, String.format("Selected day: %s", dayItem));
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Log.d(LOG_TAG, String.format("Selected event: %s", event));
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        }
    }

    // endregion

    // region Private Methods
    // this function add list of event in the calendar
    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("AYUSH travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(this, R.color.orange_dark), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("DEEPAK travels to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(this, R.color.yellow), startTime2, endTime2, true);
        eventList.add(event2);

        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("PUNIYA of Harpa", "", "Dalvík",
                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
        eventList.add(event3);
    }

    // endregion

    public void updateCurLoc(String classname) {
        TextView classroom = findViewById(R.id.classromm);
        //todo : add floor and building no. to it
        classroom.setText(classname);
        ImageView imageView = findViewById(R.id.classimage);
        imageView.setImageDrawable(getDrawable(R.drawable.classrome));
    }

    public void sendnotification(String classname) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Class changed to ")
                    .setContentText(" abd  ");

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.notify(0, builder.build());
            }
        } catch (Exception e) {
        }

    }

    public void openmemories(View view) {
        Intent intent = new Intent(this, memory.class);
        startActivity(intent);
        Log.d(TAG, "openmemories: upto intnent");
    }

    public void openbustab(View view) {
        Intent intent = new Intent(this, bustab.class);
        startActivity(intent);
    }

    public void invite(View view) {
        String s = "shared by ayush for School ware";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, s);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }

    public void signout(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showloaction(View view) {
        Intent intent = new Intent(this, location.class);
        startActivity(intent);
    }

    public void feespayment(View view) {
        Intent intent = new Intent(this, feespayment.class);
        startActivity(intent);
    }

    public void openhelp(View view) {
        Log.d(TAG, "openhelp: start open acc");
        new getresponce().execute();
    }

    public void face2atten(View view) {
        ImageView face = findViewById(R.id.face);
        face.setVisibility(View.INVISIBLE);
        TextView tv = findViewById(R.id.faceTV);
        tv.setVisibility(View.VISIBLE);
        tv.setText(R.string.peratt);
    }

    public void atten2face(View view) {
        ImageView face = findViewById(R.id.face);
        face.setVisibility(View.VISIBLE);
        TextView tv = findViewById(R.id.faceTV);
        tv.setVisibility(View.INVISIBLE);
    }

    public void populatetabhost() {

        TabHostWindow = findViewById(R.id.tabHost);
        TabHostWindow.setup();
        Log.d(TAG, "populatetabhost: reached populate");
        //TabHostWindow.clearAllTabs();
        //TabHostWindow = findViewById(R.id.tabHost);
        if (!(TabHostWindow.getCurrentTab() != -1)) {

            TabHost.TabSpec m = TabHostWindow.newTabSpec("monday").setIndicator("M", getDrawable(R.drawable.ic_launcher_foreground)).setContent(R.id.W);
            TabHost.TabSpec t = TabHostWindow.newTabSpec("tuesday").setIndicator("T").setContent(R.id.W);
            TabHost.TabSpec w = TabHostWindow.newTabSpec("wednesday").setIndicator("W").setContent(R.id.W);
            TabHost.TabSpec th = TabHostWindow.newTabSpec("thursday").setIndicator("T").setContent(R.id.W);
            TabHost.TabSpec f = TabHostWindow.newTabSpec("friday").setIndicator("F").setContent(R.id.W);
            TabHost.TabSpec s = TabHostWindow.newTabSpec("saturday").setIndicator("S").setContent(R.id.W);

            TabHostWindow.addTab(m);
            TabHostWindow.addTab(t);
            TabHostWindow.addTab(w);
            TabHostWindow.addTab(th);
            TabHostWindow.addTab(f);
            TabHostWindow.addTab(s);
            TabHostWindow.setCurrentTab(0);
        }

    }

    public void showAttendance() {
        Log.d(TAG, "showAttendance:  show asdfsdf");
        spinbar = findViewById(R.id.spinbar);
        Thread timer = new Thread() {
            public void run() {
                try {
                    while (i != 100) {
                        spinbar.setProgress(i);
                        i++;
                        sleep(5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }

    public void updateprofile() {
        // todo :get data from sqllite locally and save that data in variables
        studentNameTv = findViewById(R.id.studentName);
        studentAgeTv = findViewById(R.id.studentAge);
        studentIdTv = findViewById(R.id.studentId);
        studentClassTv = findViewById(R.id.studentClass);
        studentHeightTv = findViewById(R.id.studentHeight);
        studentWeightTv = findViewById(R.id.studentWeight);
        studentpictureidTv = findViewById(R.id.studentpicutreid);

        studentWeightTv.setText(studentWeight);
        studentNameTv.setText(studdentName);
        studentAgeTv.setText(studentAge);
        studentClassTv.setText(studentClass);
        studentHeightTv.setText(studentHeight);
        studentIdTv.setText(studentId);

    }

    public void calender() {
        CalendarView calendarView = findViewById(R.id.calendarView);
        //long time= System.currentTimeMillis();
        Log.d(TAG, "calender: mili sec");
        //calendarView.setDate(time);
    }

    public void getStudentPrifileFromServer() {
        //todo : get student profile from server and save it in sqllitebase
    }

    // Method for disabling ShiftMode of BottomNavigationView
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    //pager adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new home();
                case 1:
                    return new notification();
                case 2:
                    return new calender();
                case 3:
                    return new profile();
                default:
                    return new home();
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }

    // all fragment class start
    public static class home extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.home, container, false);
        }

    }

    public static class notification extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.notification, container, false);
        }
    }

    public static class profile extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.prifile3, container, false);
        }
    }

    public static class calender extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.agendacalendar, container, false);
        }
    }
    // all fragment class close


    private class getresponce extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(homepage.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url_all_products);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String value = jsonObj.getString("username");
                    Log.d(TAG, "doInBackground: " + value);


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

