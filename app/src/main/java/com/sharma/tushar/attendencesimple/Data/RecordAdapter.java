package com.sharma.tushar.attendencesimple.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sharma.tushar.attendencesimple.R;

import java.util.ArrayList;

public class RecordAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList arrayList;

    public RecordAdapter(@NonNull Context context, @NonNull ArrayList arrayList) {
        super(context, 0, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.record_list_item, parent, false);
        }
        SubjectPercent detail = (SubjectPercent) arrayList.get(position);
        TextView subjectName = view.findViewById(R.id.item_subject);
        TextView percent = view.findViewById(R.id.item_percent);
        subjectName.setText(detail.getSubject());
        percent.setText(String.valueOf(detail.getPercentage()));

        return view;
    }
}
