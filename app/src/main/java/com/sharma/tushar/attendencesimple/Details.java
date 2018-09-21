package com.sharma.tushar.attendencesimple;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sharma.tushar.attendencesimple.Data.DataContract;
import com.sharma.tushar.attendencesimple.Data.DatabaseHelper;
import com.sharma.tushar.attendencesimple.Data.SubjectAdapter;

import java.util.ArrayList;
import java.util.Arrays;

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
        if (intent.hasExtra(MainActivity.EXTRA_DATE_CODE) && intent.hasExtra(MainActivity.EXTRA_DAY)) {
            dateCode = intent.getIntExtra(MainActivity.EXTRA_DATE_CODE, 0);
            day = intent.getIntExtra(MainActivity.EXTRA_DAY, 1);
        }

        //Initialize instance variables
        helper = new DatabaseHelper(this);
        notAttendedClasses = new StringBuffer("");
        noClass = new StringBuffer("");

        //Get ListView
        ListView listView = findViewById(R.id.subject_list);

        //Get today's list code from database in String.
        String todaysList = getList(day);

        //Create array from code containing integers.
        todaysClasses = todaysList.split("!");

        //Create ArrayList containing subject names.
        ArrayList arrayList = formList(todaysList);

        //Create adapter and attach to ListView
        SubjectAdapter adapter = new SubjectAdapter(this, arrayList);
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

    //Get Code for today's class schedule from DAILY_SCHEDULE_TABLE.
    private String getList(int day) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DataContract.DAILY_SCHEDULE_TABLE,
                new String[]{DataContract.DAILY_SCHEDULE_CODE},
                DataContract._ID + " = ?",
                new String[]{String.valueOf(day)},
                null,
                null,
                null);
        cursor.moveToFirst();
        String list = cursor.getString(cursor.getColumnIndex(DataContract.DAILY_SCHEDULE_CODE));
        cursor.close();
        return list;
    }

    //Convert code from integer to actual subject name.
    private ArrayList formList(String string) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String arr[] = string.split("!");

        //Loop through each integer value and get respective string value from SUBJECT_TABLE
        //and store in same position.
        for (int i = 0; i < arr.length; i++) {
            Cursor cursor = db.query(DataContract.SUBJECT_TABLE,
                    new String[]{DataContract.SUBJECT_SUB_NAME},
                    DataContract._ID + " = ?",
                    new String[]{arr[i]},
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            arr[i] = cursor.getString(cursor.getColumnIndex(DataContract.SUBJECT_SUB_NAME));
            cursor.close();
        }

        //Create arrayList from array containing subject name.
        return new ArrayList<>(Arrays.asList(arr));
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
                i++;
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
        Toast.makeText(Details.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        NavUtils.navigateUpFromSameTask(Details.this);
    }

}
