package com.sharma.tushar.attendencesimple.Data;

import android.provider.BaseColumns;

public class DataContract implements BaseColumns {
    private DataContract() { }

    public static final String SUBJECT_TABLE = "Subject";
    public static final String NOT_ATTENDED_TABLE = "NotAttended";
    public static final String DAILY_SCHEDULE_TABLE = "DailySchedule";

    public static final String SUBJECT_SUB_NAME = "Sub_Name";
    public static final String SUBJECT_TOT_CLASSES = "Total_Classes";
    public static final String SUBJECT_NOT_ATTENDED = "NotAttended";

    public static final String NOT_ATTENDED_DATE = "Date";
    public static final String NOT_ATTENDED_CODE = "Sub_Code";

    public static final String DAILY_SCHEDULE_CODE = "Sub_Code";

}
