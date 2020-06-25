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
import com.example.rigotti_project.Rankings.CustomPilotRankingListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CustomPilotResultsListView extends ArrayAdapter<String> {

    private ArrayList<Integer> punti;
    private ArrayList<String> nomi, team, auto;

    private Activity context;

    public CustomPilotResultsListView(Activity context, ArrayList<String> nomi, ArrayList<String> team, ArrayList<String> auto) {
        super(context, R.layout.punti_pilota_item, nomi);
        this.context = context;
        this.nomi = nomi;
        this.team = team;
        this.auto = auto;
        this.punti = new ArrayList<>();
        int upperbound = 20;
        //CREO PUNTI A CASO (NON DOPPIATI)
        //generate random values from 0-25
        Random rand = new Random(); //instance of random class
        punti.add(20);//il primo ha 20 punti
        int i = 1;
        while (i < nomi.size()) {
            //FACILITO zeri
            if (nomi.size() - i < 5) {
                upperbound = 3;
            } else if (nomi.size() - i < 2) {
                upperbound = 1;
            }
            int num = rand.nextInt(upperbound);
            if (!punti.contains(num) || num == 0) {
                punti.add(num);
                i++;
            }
        }
        Collections.sort(punti, Collections.reverseOrder());
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
        viewHolder.tv_nome.setText(nomi.get(position));
        viewHolder.tv_auto.setText(auto.get(position));
        viewHolder.tv_team.setText(team.get(position));
        viewHolder.tv_punti.setText(punti.get(position).toString());
        return r;
    }


    class ViewHolder {
        TextView tv_nome, tv_team, tv_auto, tv_punti;


        ViewHolder(View v) {
            tv_nome = v.findViewById(R.id.punti_pilota_nome);
            tv_auto = v.findViewById(R.id.punti_pilota_auto);
            tv_team = v.findViewById(R.id.punti_pilota_team);
            tv_punti = v.findViewById(R.id.punti_pilota_punti);

        }
    }
}
