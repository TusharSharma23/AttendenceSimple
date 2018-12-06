package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sharma.tushar.attendencesimple.Adapters.RecordAdapter;
import com.sharma.tushar.attendencesimple.Data.DataContract;
import com.sharma.tushar.attendencesimple.Data.DatabaseHelper;
import com.sharma.tushar.attendencesimple.HelperClasses.SubjectPercent;

import java.util.ArrayList;

public class Record extends AppCompatActivity {

    private DatabaseHelper helper;
    public static final String SUBJECT_PERCENT_KEY = "subjectPercentClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        helper = new DatabaseHelper(Record.this);
        ListView listView = findViewById(R.id.record_subject_list);
        final ArrayList subjectDetail = getDetails();
        RecordAdapter adapter = new RecordAdapter(Record.this, subjectDetail);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Record.this, PerSubjectDetail.class);
                intent.putExtra(SUBJECT_PERCENT_KEY, (SubjectPercent)subjectDetail.get(position));
                startActivity(intent);
            }
        });
    }

    private ArrayList getDetails() {
        ArrayList<SubjectPercent> percentArrayList = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DataContract.SUBJECT_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String name = cursor.getString(cursor.getColumnIndex(DataContract.SUBJECT_SUB_NAME));
            int total = cursor.getInt(cursor.getColumnIndex(DataContract.SUBJECT_TOT_CLASSES));
            int notAttended = cursor.getInt(cursor.getColumnIndex(DataContract.SUBJECT_NOT_ATTENDED));
            percentArrayList.add(new SubjectPercent(name, notAttended, total));
            cursor.moveToNext();
        }
        cursor.close();
        return percentArrayList;
    }
}
