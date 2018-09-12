package com.sharma.tushar.attendencesimple.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.Details;
import com.sharma.tushar.attendencesimple.R;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter {

    private ArrayList arrayList;
    private Context context;

    public SubjectAdapter(@NonNull Context context, ArrayList arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        String str = (String) arrayList.get(pos);
        final int position = pos;
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.subject_list_item, parent, false);
        TextView sub = view.findViewById(R.id.subject_name);
        final RadioButton attended = view.findViewById(R.id.attended_button);
        final RadioButton notAttended = view.findViewById(R.id.not_attended_button);
        final CheckBox noClass = view.findViewById(R.id.no_class);

        attended.setChecked(true);
        sub.setText(str);

        attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String schedule = Details.todaysClasses[position];
                while (Details.notAttendedClasses.toString().contains(schedule)) {
                    Details.notAttendedClasses.deleteCharAt(Details.notAttendedClasses.indexOf(schedule));
                }
            }
        });
        notAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.notAttendedClasses.append(Details.todaysClasses[position]).append("!");
                Log.i(" Added to code", Details.todaysClasses[position] + "!");
                Log.i(" Buffer contains ", Details.notAttendedClasses.toString());
            }
        });
        noClass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String schedule = Details.todaysClasses[position];
                if(isChecked) {
                    Details.noClass.append(schedule).append("!");
                    attended.setVisibility(View.INVISIBLE);
                    notAttended.setVisibility(View.INVISIBLE);
                } else {
                    if(Details.noClass.toString().contains(schedule))
                        Details.noClass.deleteCharAt(Details.noClass.indexOf(schedule));
                    attended.setVisibility(View.VISIBLE);
                    notAttended.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}
