package com.sharma.tushar.attendencesimple.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DBname = "Attendance Manager";
    private static int DBversion = 1;

    public DatabaseHelper(Context context) {
        super(context, DBname, null, DBversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + DataContract.SUBJECT_TABLE + "(" +
                 DataContract._ID + " INTEGER Primary Key AUTOINCREMENT," +
                 DataContract.SUBJECT_SUB_NAME + " TEXT NOT NULL UNIQUE," +
                 DataContract.SUBJECT_TOT_CLASSES + " INTEGER NOT NULL DEFAULT 0," +
                 DataContract.SUBJECT_NOT_ATTENDED +" INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(query);
        query = "create table " + DataContract.NOT_ATTENDED_TABLE +"(" +
                DataContract.NOT_ATTENDED_DATE + " INTEGER NOT NULL," +
                DataContract.NOT_ATTENDED_CODE + " TEXT NOT NULL);";
        db.execSQL(query);
        query = "create table " + DataContract.DAILY_SCHEDULE_TABLE + "(" +
                DataContract._ID + " INTEGER Primary Key AUTOINCREMENT," +
                DataContract.DAILY_SCHEDULE_CODE + " TEXT NOT NULL);";
        db.execSQL(query);

        insertSub(db);
        insertSch(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void insertSub(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Algorithm Design");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Software Engineering");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Java Programming");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Industrial Management");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Digital Communication");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Communication Skills");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Algorithm Design lab");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Software Engineering Lab");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Java Programming Lab");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Digital Communication Lab");
        db.insert(DataContract.SUBJECT_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.SUBJECT_SUB_NAME, "Communication Skills Lab");
        db.insert(DataContract.SUBJECT_TABLE, null, values);
    }

    private void insertSch(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(DataContract.DAILY_SCHEDULE_CODE, "4!5!1!3!2!10");
        db.insert(DataContract.DAILY_SCHEDULE_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.DAILY_SCHEDULE_CODE, "5!3!1!2!6!11");
        db.insert(DataContract.DAILY_SCHEDULE_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.DAILY_SCHEDULE_CODE, "3!1!7!6!8");
        db.insert(DataContract.DAILY_SCHEDULE_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.DAILY_SCHEDULE_CODE, "2!5!4!4!3!1!2");
        db.insert(DataContract.DAILY_SCHEDULE_TABLE, null, values);

        values = new ContentValues();
        values.put(DataContract.DAILY_SCHEDULE_CODE, "9!5");
        db.insert(DataContract.DAILY_SCHEDULE_TABLE, null, values);

    }



}
