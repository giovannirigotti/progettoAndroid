package com.example.rigotti_project.Activity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.R;

import java.util.ArrayList;

public class CustomChampionshipListView extends ArrayAdapter<String> {

    private ArrayList<Integer> imgID;
    private ArrayList<String> nomi;

    private Activity context;

    public CustomChampionshipListView(Activity context, ArrayList<String> nomi, ArrayList<Integer> imgID) {
        super(context, R.layout.campionato_item, nomi);
        this.context = context;
        this.nomi = nomi;
        this.imgID = imgID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.campionato_item, null);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }

        viewHolder.iv_logo.setImageResource(imgID.get(position));
        viewHolder.tv_nome.setText(nomi.get(position));
        return r;
    }


    class ViewHolder {
        TextView tv_nome;
        ImageView iv_logo;

        ViewHolder(View v) {
            tv_nome = v.findViewById(R.id.lista_nome);
            iv_logo = v.findViewById(R.id.lista_logo);
        }
    }
}
