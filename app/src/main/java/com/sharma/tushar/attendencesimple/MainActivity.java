package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    private DrawerLayout drawerLayout;

    public static final String EXTRA_DATE_CODE = "ExtraData";
    public static final String EXTRA_DAY = "Day";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calender_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    int dateCode = Integer.parseInt(year + "" + month +"" + dayOfMonth);
                    Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                    int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    Log.i("Date Stored ", String.valueOf(dateCode));
                    if (day!=0 && day!=6) {
                        Intent intent = new Intent(MainActivity.this, Details.class);
                        intent.putExtra(EXTRA_DATE_CODE, dateCode);
                        intent.putExtra(EXTRA_DAY, day);
                        startActivity(intent);
                    }
                }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_per_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.previous_record_menu_item:
                Intent intent = new Intent(MainActivity.this, Record.class);
                startActivity(intent);
                return true;
            case R.id.about_menu_item:
                Toast.makeText(MainActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
