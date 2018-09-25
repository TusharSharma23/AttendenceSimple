package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Display a calender and when a date is clicked,
 * display that day's schedule along with options to change
 * attended and no class status.
 */
public class CalenderDisplay extends AppCompatActivity {

    //Used to create Subject Adapter for this class.
    public static final int CALENDER_PAGE = 2;

    //Used to send dateCode with intent.
    public static final String EXTRA_DATE_CODE = "ExtraData";
    //Used to send selected day with intent.
    public static final String EXTRA_DAY = "Day";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_display);
        CalendarView calendarView = findViewById(R.id.calender_view);

        //When a date is selected from calender
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Create dateCode as 'yymmdd' format.
                int dateCode = Integer.parseInt(year + "" + month +"" + dayOfMonth);
                //Get selected day.
                Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
                Log.i("Date Stored ", String.valueOf(dateCode));
                //If day is valid, call Details class
                //If invalid day, no effect.
                if (day!=0 && day!=6) {
                    Intent intent = new Intent(CalenderDisplay.this, Details.class);
                    intent.putExtra(EXTRA_DATE_CODE, dateCode);
                    intent.putExtra(EXTRA_DAY, day);
                    startActivity(intent);
                }
            }
        });
    }
}
