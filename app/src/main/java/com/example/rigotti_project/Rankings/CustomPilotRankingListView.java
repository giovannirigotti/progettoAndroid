package com.example.rigotti_project.Rankings;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rigotti_project.Championship.CustomChampionshipListView;
import com.example.rigotti_project.R;

import java.util.ArrayList;

public class CustomPilotRankingListView  extends ArrayAdapter<String> {

    private ArrayList<Integer> punti;
    private ArrayList<String> nomi, team, auto;

    private Activity context;

    public CustomPilotRankingListView(Activity context, ArrayList<String> nomi, ArrayList<String> team, ArrayList<String> auto, ArrayList<Integer> punti) {
        super(context, R.layout.punti_pilota_item, nomi);
        this.context = context;
        this.nomi = nomi;
        this.team = team;
        this.auto = auto;
        this.punti = punti;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.punti_pilota_item, null);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        Log.e("TAG",nomi.get(position));
        viewHolder.tv_nome.setText(nomi.get(position));
        viewHolder.tv_auto.setText(auto.get(position));
        viewHolder.tv_team.setText(team.get(position));
        viewHolder.tv_punti.setText(punti.get(position).toString());
        return r;
    }


    class ViewHolder {
        TextView tv_nome,tv_team,tv_auto,tv_punti;


        ViewHolder(View v) {
            tv_nome = v.findViewById(R.id.punti_pilota_nome);
            tv_auto = v.findViewById(R.id.punti_pilota_auto);
            tv_team = v.findViewById(R.id.punti_pilota_team);
            tv_punti = v.findViewById(R.id.punti_pilota_punti);

        }
    }
}
