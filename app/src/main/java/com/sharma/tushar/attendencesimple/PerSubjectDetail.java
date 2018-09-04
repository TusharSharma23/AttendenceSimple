package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.Data.SubjectPercent;

public class PerSubjectDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        SubjectPercent detail = null;
        if (intent.hasExtra(Record.SUBJECT_PERCENT_KEY)) {
            detail = (SubjectPercent) intent.getSerializableExtra(Record.SUBJECT_PERCENT_KEY);
        }

        setContentView(R.layout.activity_per_subject_detail);
        TextView subjectName1 = findViewById(R.id.detail_subject_name1);
        TextView subjectName2 = findViewById(R.id.detail_subject_name2);
        TextView attended = findViewById(R.id.per_subject_attended);
        TextView total = findViewById(R.id.per_subject_total);
        TextView percent = findViewById(R.id.per_subject_percent);

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

    }
}
