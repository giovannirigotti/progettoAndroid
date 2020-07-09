package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Activity per la visualizzazione dei risultati piloti e team ad una gara
// Consente di le due ListView contenenti i risultati
// ---------------------------------
// ---------------------------------

public class ResultsActivity extends AppCompatActivity {

    // Variabili
    private Campionato camp;
    private Integer indice_campionato;

    // View
    private ListView risultati_piloti, risultati_team;

    // Per ListView
    private ArrayList<String> nomi, auto, team, t_team, t_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setTitle("Risultati");
        nomi = new ArrayList<>();
        auto = new ArrayList<>();
        team = new ArrayList<>();
        t_auto = new ArrayList<>();
        t_team = new ArrayList<>();

        // Setto indice_campionato e indice_campionato presi dell'intent
        // region CHECK INTENT
        Intent i = getIntent();
        if (!i.hasExtra("indice_campionato")) {
            Utili.doToast(this, "Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        } else {
            indice_campionato = i.getIntExtra("indice_campionato", -1);

            if (indice_campionato == -1) {
                Utili.doToast(this, "Campionato non disponibile.");
                Intent new_i = new Intent(this, HomeActivity.class);
                startActivity(new_i);
            }
        }
        // endregion

        risultati_piloti = (ListView) findViewById(R.id.risultati_piloti);
        risultati_team = (ListView) findViewById(R.id.risultati_team);

        // Carico dati dal salvataggio in locale
        camp = Utili.listaCampionati.getCampionato(indice_campionato);

        // Popolo ArrayList per la creazione della ListView customizzata
        for (int j = 0; j < camp.getPiloti().size(); j++) {
            Pilota p = camp.getPilota(j);
            if (!team.contains(p.getTeam())) {
                t_team.add(p.getTeam());
                t_auto.add(p.getAuto());
            }
            nomi.add(p.getNome());
            auto.add(p.getAuto());
            team.add(p.getTeam());
        }

        // Creo e visualizzo ListView customizzata piloti
        CustomPilotResultsListView c1 = new CustomPilotResultsListView(this, nomi, team, auto);
        risultati_piloti.setAdapter(c1);

        // Creo e visualizzo ListView customizzata team
        CustomTeamResultsListView c2 = new CustomTeamResultsListView(this, t_team, t_auto);
        risultati_team.setAdapter(c2);
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
        return (Utili.setMenu(ResultsActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
