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
import java.util.Collections;
import java.util.Random;

// ---------------------------------
// ---------------------------------
// Classe per la creazione di una ListView personalizzata
// Nello specifico crea una ListView per visualizzare i risultati dei team ad una gara
// ---------------------------------
// ---------------------------------

public class CustomTeamResultsListView extends ArrayAdapter<String> {

    private ArrayList<Integer> punti;
    private ArrayList<String> team, auto;

    private Activity context;

    public CustomTeamResultsListView(Activity context, ArrayList<String> team, ArrayList<String> auto) {
        super(context, R.layout.punti_team_item, team);
        this.context = context;
        this.team = team;
        this.auto = auto;
        this.punti = new ArrayList<>();
        int upperbound = 25;
        //CREO PUNTI A CASO (NON DOPPIATI)
        //generate random values from 0-25
        Random rand = new Random(); //instance of random class
        punti.add(25);//il primo ha 25 punti
        int i = 1;
        while (i < team.size()) {
            //FACILITO zeri
            if (team.size() - i < 5) {
                upperbound = 3;
            } else if (team.size() - i < 2) {
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
            r = layoutInflater.inflate(R.layout.punti_team_item, null);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tv_auto.setText(auto.get(position));
        viewHolder.tv_team.setText(team.get(position));
        viewHolder.tv_punti.setText(punti.get(position).toString());
        return r;
    }


    class ViewHolder {
        TextView tv_team, tv_auto, tv_punti;


        ViewHolder(View v) {
            tv_auto = v.findViewById(R.id.punti_team_auto);
            tv_team = v.findViewById(R.id.punti_team_team);
            tv_punti = v.findViewById(R.id.punti_team_punti);

        }
    }
}
