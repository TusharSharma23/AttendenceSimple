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
import android.widget.Switch;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.CalenderDisplay;
import com.sharma.tushar.attendencesimple.Details;
import com.sharma.tushar.attendencesimple.HomePage;
import com.sharma.tushar.attendencesimple.R;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter {

    private ArrayList arrayList;
    private Context context;
    private int page;

    public SubjectAdapter(@NonNull Context context, ArrayList arrayList, int page) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.page = page;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        String str = (String) arrayList.get(position);
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.subject_list_item, parent, false);
        TextView sub = view.findViewById(R.id.subject_name);
        final Switch attended = view.findViewById(R.id.attended_button);
        CheckBox noClass = view.findViewById(R.id.no_class);

        sub.setText(str);

        if (this.page == HomePage.HOME_PAGE) {
            attended.setVisibility(View.GONE);
            noClass.setVisibility(View.GONE);
        } else if (this.page == CalenderDisplay.CALENDER_PAGE) {
            addClickListeners(attended, noClass, position);
        }

        return view;
    }

    private void addClickListeners(final Switch attended, CheckBox noClass, final int position) {
        attended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String schedule = Details.todaysClasses[position];
                if (!isChecked) {
                    Details.notAttendedClasses.append(Details.todaysClasses[position]).append("!");
                    Log.i(" Added to code", Details.todaysClasses[position] + "!");
                    Log.i(" Buffer contains ", Details.notAttendedClasses.toString());
                } else if (Details.notAttendedClasses.toString().contains(schedule)) {
                    Details.notAttendedClasses.deleteCharAt(Details.notAttendedClasses.indexOf(schedule));
                    Log.i("Removed from code", "");
                    Log.i(" Buffer contains ", Details.notAttendedClasses.toString());
                }
            }
        });

        noClass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String schedule = Details.todaysClasses[position];
                if(isChecked) {
                    Details.noClass.append(schedule).append("!");
                    attended.setVisibility(View.INVISIBLE);
                } else {
                    if(Details.noClass.toString().contains(schedule))
                        Details.noClass.deleteCharAt(Details.noClass.indexOf(schedule));
                    attended.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
