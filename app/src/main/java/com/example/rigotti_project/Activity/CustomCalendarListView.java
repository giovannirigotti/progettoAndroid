package com.example.rigotti_project.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.R;

import java.util.ArrayList;

public class CustomCalendarListView extends ArrayAdapter<String> {

    private ArrayList<Integer> sequenze;
    private ArrayList<String> date;
    private ArrayList<String> circuiti;

    private Activity context;

    public CustomCalendarListView(Activity context, ArrayList<String> circuiti, ArrayList<Integer> sequenze, ArrayList<String> date) {

        super(context, R.layout.gara_item, circuiti);
        this.context = context;
        this.sequenze = sequenze;
        this.date = date;
        this.circuiti = circuiti;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.gara_item, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tv_seq.setText(sequenze.get(position).toString());
        viewHolder.tv_data.setText(date.get(position));
        viewHolder.tv_circuito.setText(circuiti.get(position));
        return r;
    }
}

class ViewHolder {
    TextView tv_seq;
    TextView tv_data;
    TextView tv_circuito;

    ViewHolder(View v) {
        tv_seq = (TextView) v.findViewById(R.id.gara_seq);
        tv_data = (TextView) v.findViewById(R.id.gara_data);
        tv_circuito =(TextView) v.findViewById(R.id.gara_circuito);
    }
}
