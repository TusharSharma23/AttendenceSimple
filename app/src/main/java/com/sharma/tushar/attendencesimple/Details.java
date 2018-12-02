package com.sharma.tushar.attendencesimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sharma.tushar.attendencesimple.Adapters.SetDetailsAdapter;
import com.sharma.tushar.attendencesimple.Adapters.SubjectAdapter;

/**
 * Display schedule as per day in intent.
 */
public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Get selected date and dateCode from intent.
        Intent intent = getIntent();
        int dateCode = -1;
        int day = 0;

        //DateCode from CalenderDisplay class
        if (intent.hasExtra(CalenderDisplay.EXTRA_DATE_CODE))
            dateCode = intent.getIntExtra(CalenderDisplay.EXTRA_DATE_CODE, 0);
        //Day from CalenderDisplay or HomePage class
        if (intent.hasExtra(CalenderDisplay.EXTRA_DAY))
            day = intent.getIntExtra(CalenderDisplay.EXTRA_DAY, 1);

        //Get ListView
        RecyclerView subjectList = findViewById(R.id.subject_list);

        //Create adapter and attach to ListView
        SubjectAdapter adapter = new SetDetailsAdapter(Details.this).setupAdapter(day, CalenderDisplay.CALENDER_PAGE);
        subjectList.setLayoutManager(new LinearLayoutManager(this));
        subjectList.setAdapter(adapter);

        //Submit button Task
        Button btn = findViewById(R.id.submit_button);
        final int finalDateCode = dateCode;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update database and return to previous page.
                new SetDetailsAdapter(Details.this).performTask(finalDateCode);
                NavUtils.navigateUpFromSameTask(Details.this);
            }
        });

        //Cancel Button Task
        btn = findViewById(R.id.cancel_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to previous page
                NavUtils.navigateUpFromSameTask(Details.this);
            }
        });
    }
}
