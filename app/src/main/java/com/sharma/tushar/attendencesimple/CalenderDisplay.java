package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalenderDisplay extends AppCompatActivity {

    CalendarView calendarView;

    public static final int CALENDER_PAGE = 2;

    public static final String EXTRA_DATE_CODE = "ExtraData";
    public static final String EXTRA_DAY = "Day";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_display);
        calendarView = findViewById(R.id.calender_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    int dateCode = Integer.parseInt(year + "" + month +"" + dayOfMonth);
                    Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                    int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    Log.i("Date Stored ", String.valueOf(dateCode));
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
