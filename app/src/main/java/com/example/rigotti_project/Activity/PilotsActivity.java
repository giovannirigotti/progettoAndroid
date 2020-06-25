package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.CustomPilotsListView;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class PilotsActivity extends AppCompatActivity {

    private Integer indice_campionato;

    //Campionato
    private Campionato campionato;
    private ArrayList<String> nomi, auto, team;
    private ArrayList<Pilota> piloti;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilots);
        setTitle("Iscritti");
        campionato = new Campionato();
        piloti = new ArrayList<>();

        nomi = new ArrayList<>();
        auto = new ArrayList<>();
        team = new ArrayList<>();

        Intent i = getIntent();
        if (!i.hasExtra("indice_campionato")) {
            Utili.doToast(this, "Dati non disponibili.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        indice_campionato = i.getIntExtra("indice_campionato", -1);

        if (indice_campionato == -1) {
            Utili.doToast(this, "Errore caricamento dati.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        campionato = Utili.listaCampionati.getCampionato(indice_campionato);
        piloti = campionato.getPiloti();

        getPilotsData();

        Utili.doToast(this, "Premi su un pilota iscritto per vedere più informazioni!");

        lv = (ListView) findViewById(R.id.lista_piloti);
        CustomPilotsListView custom = new CustomPilotsListView(this, nomi, auto, team);
        lv.setAdapter(custom);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view info
                showPilotDialog(view, position);
            }
        });

    }

    private void showPilotDialog(View v, Integer position) {
        //APRO DIALOG INFO_PILOTA
        AlertDialog.Builder builder = new AlertDialog.Builder(PilotsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.pilot_info_dialog, null);

        final TextView name, car, squad;
        final Button btn_esci;

        name = (TextView) view.findViewById(R.id.info_pilota_nome);
        car = (TextView) view.findViewById(R.id.info_pilota_auto);
        squad = (TextView) view.findViewById(R.id.info_pilota_team);

        name.setText(nomi.get(position));
        car.setText(auto.get(position));
        squad.setText(team.get(position));

        btn_esci = (Button) view.findViewById(R.id.btn_esci);

        builder.setTitle("Dati pilota").setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_esci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    public void getPilotsData() {
        Pilota p;
        Log.e("POPOLAZIONE", "Start");
        for (int i = 0; i < piloti.size(); i++) {
            p = piloti.get(i);
            nomi.add(p.getNome());
            auto.add(p.getAuto());
            team.add(p.getTeam());
            //Log.e("Pilota", p.getNome() + p.getAuto() + p.getTeam());
        }
        Log.e("POPOLAZIONE", "End");
    }

    // region IMPORTO MENU

    // AGGIUNGO MENU ALLA ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //GESTISCO MENU NELLA ACTIVITY
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Ritorno True se il quando premo su un Item questo è nello switch case
        // Ritrono "super.onOptionsItemSelected(item)" altrimenti
        return (Utili.setMenu(PilotsActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
