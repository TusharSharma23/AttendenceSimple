package com.sharma.tushar.attendencesimple;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.Data.DataContract;
import com.sharma.tushar.attendencesimple.Data.DatabaseHelper;
import com.sharma.tushar.attendencesimple.Data.SubjectPercent;

import java.util.Objects;

public class PerSubjectDetail extends AppCompatActivity {

    private TextView attended;
    private TextView total;
    private TextView percent;
    private EditText attendedEdited;
    private EditText totalEdited;
    private LinearLayout buttons;
    private SubjectPercent detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        detail = null;
        if (intent.hasExtra(Record.SUBJECT_PERCENT_KEY)) {
            detail = (SubjectPercent) intent.getSerializableExtra(Record.SUBJECT_PERCENT_KEY);
        }

        setContentView(R.layout.activity_per_subject_detail);
        TextView subjectName1 = findViewById(R.id.detail_subject_name1);
        TextView subjectName2 = findViewById(R.id.detail_subject_name2);
        attended = findViewById(R.id.per_subject_attended_textview);
        total = findViewById(R.id.per_subject_total_textview);
        percent = findViewById(R.id.per_subject_percent);

        attendedEdited = findViewById(R.id.per_subject_attended_edittext);
        totalEdited = findViewById(R.id.per_subject_total_edittext);
        buttons = findViewById(R.id.save_cancel);

        Button save_button = findViewById(R.id.per_subject_submit_button);
        Button cancel_button = findViewById(R.id.per_subject_cancel_button);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int attendedText = Integer.parseInt(attended.getText().toString());
                int totalText = Integer.parseInt(total.getText().toString());

                SQLiteDatabase db = new DatabaseHelper(PerSubjectDetail.this).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DataContract.SUBJECT_NOT_ATTENDED,String.valueOf(totalText - attendedText));
                values.put(DataContract.SUBJECT_TOT_CLASSES,String.valueOf(totalText));
                db.update(DataContract.SUBJECT_TABLE,
                        values,
                        DataContract.SUBJECT_SUB_NAME + " = ?",
                        new String[]{detail.getSubject()});

                attended.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                percent.setVisibility(View.VISIBLE);
                attended.setText(attendedEdited.getText().toString());
                total.setText(totalEdited.getText().toString());
                percent.setText(String.valueOf(Math.round (((attendedText/(double)totalText) * 100)*100.0)/100.0));

                attendedEdited.setVisibility(View.GONE);
                totalEdited.setVisibility(View.GONE);
                buttons.setVisibility(View.GONE);
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attended.setVisibility(View.VISIBLE);
                total.setVisibility(View.VISIBLE);
                percent.setVisibility(View.VISIBLE);

                attendedEdited.setVisibility(View.GONE);
                totalEdited.setVisibility(View.GONE);
                buttons.setVisibility(View.GONE);
            }
        });

        String nameArr[] = detail.getSubject().split(" ");
        if(nameArr.length == 3) {
            subjectName1.setText(nameArr[0] + " " +nameArr[1]);
            subjectName2.setText(nameArr[2]);
        } else {
            subjectName1.setText(nameArr[0]);
            subjectName2.setText(nameArr[1]);
        }

        attended.setText(String.valueOf(detail.getAttended()));
        total.setText(String.valueOf(detail.getTotal()));
        percent.setText(detail.getPercentage() + " %");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(PerSubjectDetail.this);
                return true;
            case R.id.per_subject_edit_menu:
                attended.setVisibility(View.GONE);
                total.setVisibility(View.GONE);
                percent.setVisibility(View.GONE);

                attendedEdited.setVisibility(View.VISIBLE);
                totalEdited.setVisibility(View.VISIBLE);
                buttons.setVisibility(View.VISIBLE);
                attendedEdited.setText(attended.getText().toString());
                totalEdited.setText(total.getText().toString());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
