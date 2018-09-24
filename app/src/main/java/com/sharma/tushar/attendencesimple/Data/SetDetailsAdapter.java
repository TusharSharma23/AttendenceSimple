package com.sharma.tushar.attendencesimple.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sharma.tushar.attendencesimple.Details;

import java.util.ArrayList;
import java.util.Arrays;

public class SetDetailsAdapter {

    private DatabaseHelper helper;

    public SubjectAdapter setupAdapter(Context context, int day, int page) {

        helper = new DatabaseHelper(context);

        //Get today's list code from database in String.
        String todaysList = getList(day);

        //Create array from code containing integers.
        Details.todaysClasses = todaysList.split("!");

        //Create ArrayList containing subject names.
        ArrayList arrayList = formList(todaysList);

        return new SubjectAdapter(context, arrayList, page);
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
}
