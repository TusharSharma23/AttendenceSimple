package com.sharma.tushar.attendencesimple.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.CalenderDisplay;
import com.sharma.tushar.attendencesimple.HomePage;
import com.sharma.tushar.attendencesimple.R;

import java.util.ArrayList;

/**
 * Adapter class for RecyclerView at Homepage and Details classes.
 * If page == @link(Homepage.HOME_PAGE) then hide checkbox and switch
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {

    private ArrayList arrayList;
    private Context context;
    private int page;

    SubjectAdapter(@NonNull Context context, ArrayList arrayList, int page) {
        this.context = context;
        this.arrayList = arrayList;
        this.page = page;
    }

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_list_item, parent, false);
        SubjectHolder subjectHolder = new SubjectHolder(view);
        return subjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectHolder holder, int position) {
        holder.bindTo(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void addClickListeners(final Switch attended, CheckBox noClass, final int position) {
        //Alter SetDetailsAdapter.notAttendedClasses StringBuffer for not attended classes.
        attended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String schedule = SetDetailsAdapter.todaysClasses[position];
                if (!isChecked) {
                    SetDetailsAdapter.notAttendedClasses.append(SetDetailsAdapter.todaysClasses[position]).append("!");
                    Log.i(" Added to code", SetDetailsAdapter.todaysClasses[position] + "!");
                    Log.i(" Buffer contains ", SetDetailsAdapter.notAttendedClasses.toString());
                } else if (SetDetailsAdapter.notAttendedClasses.toString().contains(schedule)) {
                    SetDetailsAdapter.notAttendedClasses.deleteCharAt(SetDetailsAdapter.notAttendedClasses.indexOf(schedule));
                    Log.i("Removed from code", "");
                    Log.i(" Buffer contains ", SetDetailsAdapter.notAttendedClasses.toString());
                }
            }
        });

        //Alter SetDetailsAdapter.noClass if no class is there.
        noClass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String schedule = SetDetailsAdapter.todaysClasses[position];
                if(isChecked) {
                    SetDetailsAdapter.noClass.append(schedule).append("!");
                    attended.setVisibility(View.INVISIBLE);
                } else {
                    if (SetDetailsAdapter.noClass.toString().contains(schedule))
                        SetDetailsAdapter.noClass.deleteCharAt(SetDetailsAdapter.noClass.indexOf(schedule));
                    attended.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    class SubjectHolder extends RecyclerView.ViewHolder {

        View view;

        private SubjectHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        private void bindTo(int position) {
            //Get Subject name from arrayList.
            String str = (String) arrayList.get(position);
            TextView sub = view.findViewById(R.id.subject_name);

            //Update subject name in textView
            sub.setText(str);

            final Switch attended = view.findViewById(R.id.attended_button);
            CheckBox noClass = view.findViewById(R.id.no_class);
            //If calling class in Homepage, hide checkbox and switch.
            if (page == HomePage.HOME_PAGE) {
                attended.setVisibility(View.GONE);
                noClass.setVisibility(View.GONE);
            } else if (page == CalenderDisplay.CALENDER_PAGE) {
                addClickListeners(attended, noClass, position);
            }
        }

    }

}
