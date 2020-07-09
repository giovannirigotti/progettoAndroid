package com.example.rigotti_project.Rankings;

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

// ---------------------------------
// ---------------------------------
// Classe per la creazione di una ListView personalizzata
// Nello specifico crea una ListView per visualizzare la classifica dei team in un campionato
// ---------------------------------
// ---------------------------------

public class CustomTeamRankingListView extends ArrayAdapter<String> {

    private ArrayList<Integer> punti;
    private ArrayList<String> team, auto;

    private Activity context;

    public CustomTeamRankingListView(Activity context, ArrayList<String> team, ArrayList<String> auto, ArrayList<Integer> punti) {
        super(context, R.layout.punti_team_item, team);
        this.context = context;
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
