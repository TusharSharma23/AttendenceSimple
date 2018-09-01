package com.sharma.tushar.attendencesimple.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.Details;
import com.sharma.tushar.attendencesimple.R;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter {

    ArrayList<String> arrayList;
    Context context;

    public SubjectAdapter(@NonNull Context context, ArrayList arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        String str = arrayList.get(pos);
        final int position = pos;
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.subject_list_item, parent, false);
        TextView sub = view.findViewById(R.id.subject_name);
        RadioButton attended = view.findViewById(R.id.attended_button);
        RadioButton notAttended = view.findViewById(R.id.not_attended_button);
        attended.setChecked(true);
        sub.setText(str);
        notAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Details.builder.append(Details.arr[position] + "!");
                Log.i(" Added to code", Details.arr[position] + "!");
                Log.i(" Buffer contains ", Details.builder.toString());

            }
        });
        //int n = Integer.parseInt(str.substring(str.length() - 1));
        /*
        if(n == 1) {
            attended.setChecked(true);
        }else {
            notAttended.setChecked(true);
        }
        */
        return view;
    }
}
