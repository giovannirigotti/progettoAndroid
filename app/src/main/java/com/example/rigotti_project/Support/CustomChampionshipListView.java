package com.example.rigotti_project.Support;

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

    private ArrayList<String> images;
    private ArrayList<Integer> imgID;
    private ArrayList<String> listaCampionati;

    private Activity context;

    public CustomChampionshipListView(Activity context, ArrayList<String> listaCampionati, ArrayList<String> images) {
        super(context, R.layout.campionato_item, listaCampionati);
        this.context = context;
        this.listaCampionati = listaCampionati;
        this.images = images;
        this.imgID = new ArrayList<>();
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

        String IMG = images.get(position);
        Log.e("IMMAGINE_passata", IMG);
        String[] t = IMG.split(".");
        String toPass = String.valueOf(t[0]);
        Log.e("IMMAGINE_short", toPass);


        imgID.add(getResId(toPass));

        viewHolder.iv_logo.setImageResource(imgID.get(position));
        viewHolder.tv_nome.setText(listaCampionati.get(position));
        return r;
    }

    public Integer getResId(String string) {

        Log.println(Log.ERROR, "nome_logo_id", string);
        int resourceID = context.getResources().getIdentifier(string, "drawable", context.getPackageName());

        return resourceID;
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
