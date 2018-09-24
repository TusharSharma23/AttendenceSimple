package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sharma.tushar.attendencesimple.Data.SetDetailsAdapter;
import com.sharma.tushar.attendencesimple.Data.SubjectAdapter;

import java.util.Calendar;

public class HomePage extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    public static final int HOME_PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        ListView listView = findViewById(R.id.homepage_list_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_menu);

        if (day != 0 && day != 6) {
            SubjectAdapter adapter = new SetDetailsAdapter().setupAdapter(HomePage.this, day, HOME_PAGE);
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.GONE);
        }

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
        getMenuInflater().inflate(R.menu.open_in_calender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.previous_record_menu_item:
                intent = new Intent(HomePage.this, Record.class);
                startActivity(intent);
                return true;
            case R.id.about_menu_item:
                Toast.makeText(HomePage.this, "Under Construction", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.open_calender:
                intent = new Intent(HomePage.this, CalenderDisplay.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
