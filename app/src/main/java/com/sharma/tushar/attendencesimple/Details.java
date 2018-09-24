package com.sharma.tushar.attendencesimple;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sharma.tushar.attendencesimple.Data.DataContract;
import com.sharma.tushar.attendencesimple.Data.DatabaseHelper;
import com.sharma.tushar.attendencesimple.Data.SetDetailsAdapter;
import com.sharma.tushar.attendencesimple.Data.SubjectAdapter;

public class Details extends AppCompatActivity {

    DatabaseHelper helper;

    //List of classes not attended.
    public static StringBuffer notAttendedClasses = null;

    //List of classes not held or teacher is absent.
    public static StringBuffer noClass = null;

    //List of today's scheduled classes in integer. Example - 1!3!
    public static String todaysClasses[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Get selected date from intent.
        Intent intent = getIntent();
        int dateCode = 0;
        int day = 0;
        if (intent.hasExtra(CalenderDisplay.EXTRA_DATE_CODE) && intent.hasExtra(CalenderDisplay.EXTRA_DAY)) {
            dateCode = intent.getIntExtra(CalenderDisplay.EXTRA_DATE_CODE, 0);
            day = intent.getIntExtra(CalenderDisplay.EXTRA_DAY, 1);
        }

        //Initialize instance variables
        helper = new DatabaseHelper(this);
        notAttendedClasses = new StringBuffer("");
        noClass = new StringBuffer("");

        //Get ListView
        ListView listView = findViewById(R.id.subject_list);

        //Create adapter and attach to ListView
        SubjectAdapter adapter = new SetDetailsAdapter().setupAdapter(Details.this, day, CalenderDisplay.CALENDER_PAGE);
        listView.setAdapter(adapter);

        //Submit button Task
        Button btn = findViewById(R.id.submit_button);
        final int finalDateCode = dateCode;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performTask(finalDateCode);
            }
        });

        //Cancel Button Task
        btn = findViewById(R.id.cancel_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(Details.this);
            }
        });
    }

    //Submit button actual working
    private void performTask(int finalDateCode) {
        SQLiteDatabase db = helper.getWritableDatabase();

        //Set values for input in NOT_ATTENDED_TABLE
        ContentValues values = new ContentValues();
        values.put(DataContract.NOT_ATTENDED_DATE, finalDateCode);
        values.put(DataContract.NOT_ATTENDED_CODE, notAttendedClasses.toString());

        //Insertion in table
        db.insert(DataContract.NOT_ATTENDED_TABLE, null, values);
        Log.i(" Inserted code", notAttendedClasses.toString());

        SQLiteDatabase db1 = helper.getReadableDatabase();

        /*
         * Loop through todaysClasses array.
         * Get total and not attended classes and increment total.
         * Increment not attended only if value present in notAttendedClasses string.
         * Skip incrementing if value present in noClass.
         *
         * Dispose current page when task complete.
         */
        for (int i = 0; i < todaysClasses.length; i++) {
            String todaysClass = todaysClasses[i];

            if (noClass.toString().contains(todaysClass)) {
                noClass.deleteCharAt(noClass.indexOf(todaysClass));
                continue;
            }

            Cursor cursor = db1.query(DataContract.SUBJECT_TABLE,
                    new String[]{DataContract.SUBJECT_TOT_CLASSES, DataContract.SUBJECT_NOT_ATTENDED},
                    DataContract._ID + " = ?",
                    new String[]{todaysClass},
                    null,
                    null,
                    null);
            cursor.moveToFirst();

            int total = cursor.getInt(cursor.getColumnIndex(DataContract.SUBJECT_TOT_CLASSES));
            total++;
            Log.i(" Total classes attended", total + "");
            values = new ContentValues();
            if (notAttendedClasses.toString().contains(todaysClass)) {
                int notAttended = cursor.getInt(cursor.getColumnIndex(DataContract.SUBJECT_NOT_ATTENDED));
                notAttended++;
                notAttendedClasses.deleteCharAt(notAttendedClasses.indexOf(todaysClass));
                Log.i(" Not Attended ", notAttended + "");
                values.put(DataContract.SUBJECT_NOT_ATTENDED, notAttended);
            }
            values.put(DataContract.SUBJECT_TOT_CLASSES, total);
            db.update(DataContract.SUBJECT_TABLE,
                    values,
                    DataContract._ID + " = ?",
                    new String[]{todaysClass});
            cursor.close();
        }
    }
}
