package com.example.rigotti_project;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomPilotsListView extends ArrayAdapter<String> {

    private ArrayList<String> nomi;
    private ArrayList<String> auto;
    private ArrayList<String> team;

    private Activity context;

    public CustomPilotsListView(Activity context, ArrayList<String> nomi, ArrayList<String> auto, ArrayList<String> team) {
        super(context, R.layout.pilota_item, nomi);
        this.context = context;
        this.nomi = nomi;
        this.auto = auto;
        this.team = team;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.pilota_item, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tv_nome.setText(nomi.get(position));
        return r;
    }
}

class ViewHolder {
    TextView tv_nome;


    ViewHolder(View v) {
        tv_nome = (TextView) v.findViewById(R.id.pilota_nome);
    }
}
