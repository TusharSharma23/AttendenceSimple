package com.sharma.tushar.attendencesimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PerSubjectDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_subject_detail);
        TextView subjectName1 = findViewById(R.id.detail_subject_name1);
        TextView subjectName2 = findViewById(R.id.detail_subject_name2);
        TextView attended = findViewById(R.id.per_subject_attended);
        TextView total = findViewById(R.id.per_subject_total);
        TextView percent = findViewById(R.id.per_subject_percent);
    }
}
