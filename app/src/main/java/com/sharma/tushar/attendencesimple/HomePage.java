package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sharma.tushar.attendencesimple.Adapters.SetDetailsAdapter;
import com.sharma.tushar.attendencesimple.Adapters.SubjectAdapter;

import java.util.Calendar;

/**
 * Display today's schedule along with two buttons
 * <ul>
 * <li>Attended all</li>
 * <li>Attended some</li>
 * </ul>
 * <p>When 'attended all' button is clicked, save all attended in database.</p>
 * <p>When 'Attended some' button is clicked, use @link Data.SetDetailsAdapter#performTask(-1)</p>
 * If any of the buttons is clicked, hide both buttons to avoid multiple inputs.
 */
public class HomePage extends AppCompatActivity {

    /**
     *To slide drawerLayout in when menu button clicked
     *and out when any other button clicked.
     */
    private DrawerLayout drawerLayout;

    //Used to create Subject adapter for this page.
    public static final int HOME_PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Get today's day.
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        RecyclerView subjectList = findViewById(R.id.homepage_list_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_menu);

        //If day is valid, display list and buttons
        if (day != 0 && day != 6) {
            displayTodaysClasses(subjectList, day);
        } else {
            //Hide Today's Layout
            findViewById(R.id.homepage_contents).setVisibility(View.INVISIBLE);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        //Display Menu button on toolbar.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
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
                //If "Previous Records" menu item clicked, Open Record class.
                intent = new Intent(HomePage.this, Record.class);
                startActivity(intent);
                return true;
            case R.id.about_menu_item:
                //If "About" menu item clicked, Open dialog box and close drawer.
                drawerLayout.closeDrawer(GravityCompat.START, true);
                AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                builder.setMessage("Under Construction");
                builder.setPositiveButton("Ok", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case android.R.id.home:
                //If Menu button on toolbar clicked, open drawer.
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.open_calender:
                //If Calender icon clicked, open CalenderDisplay class.
                intent = new Intent(HomePage.this, CalenderDisplay.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayTodaysClasses(RecyclerView subjectList, final int day) {
        //Create and attach adapter
        SubjectAdapter adapter = new SetDetailsAdapter(HomePage.this).setupAdapter(day, HOME_PAGE);
        subjectList.setLayoutManager(new LinearLayoutManager(this));
        subjectList.setAdapter(adapter);

        //Hide "No Class Today Message"
        findViewById(R.id.no_class_textview).setVisibility(View.INVISIBLE);

        final Button attended = findViewById(R.id.all_attended);
        final Button attendedSome = findViewById(R.id.some_attended);

        //Attended button
        attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update database and hide buttons.
                new SetDetailsAdapter(HomePage.this).performTask(-1);
                attended.setVisibility(View.INVISIBLE);
                attendedSome.setVisibility(View.INVISIBLE);
            }
        });

        //Attended Some button
        attendedSome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call @link Details class
                Intent intent = new Intent(HomePage.this, Details.class);
                intent.putExtra(CalenderDisplay.EXTRA_DAY, day);
                startActivity(intent);
                //Hide buttons
                attended.setVisibility(View.INVISIBLE);
                attendedSome.setVisibility(View.INVISIBLE);
            }
        });
    }

}
