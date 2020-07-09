package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Rankings.Classifica;
import com.example.rigotti_project.Rankings.CustomPilotRankingListView;
import com.example.rigotti_project.Rankings.CustomTeamRankingListView;
import com.example.rigotti_project.Rankings.ListaClassifiche;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Activity per la visualizzazione dei classifica piloti e team al campionato
// Consente di le due ListView contenenti le classifiche
// ---------------------------------
// ---------------------------------

public class RankingActivity extends AppCompatActivity {

    // Variabili
    private ListaClassifiche lista_classifiche;
    private Classifica classifica;
    private Integer indice_campionato;

    //View
    private ListView lv_piloti, lv_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lista_classifiche = new ListaClassifiche();

        //Setto indice_campionato
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
        // endregion //

        setTitle(Utili.listaCampionati.getCampionato(indice_campionato).getNome());

        // Carico dati dal salvataggio in locale
        lista_classifiche = Utili.listaClassifiche;
        classifica = lista_classifiche.getLista_classifiche().get(indice_campionato);

        // Popolo ArrayList per la ListView customizzata piloti
        ArrayList<String> p_nomi = new ArrayList<>();
        ArrayList<String> p_team = new ArrayList<>();
        ArrayList<String> p_auto = new ArrayList<>();
        ArrayList<Integer> p_punti = new ArrayList<>();
        for (int j = 0; j < classifica.getClassifica_piloti().size(); j++) {
            p_nomi.add(classifica.getClassifica_piloti().get(j).getNome());
            p_team.add(classifica.getClassifica_piloti().get(j).getTeam());
            p_auto.add(classifica.getClassifica_piloti().get(j).getAuto());
            p_punti.add(classifica.getClassifica_piloti().get(j).getPunti());
        }

        // Popolo ArrayList per la ListView customizzata team
        ArrayList<String> t_team = new ArrayList<>();
        ArrayList<String> t_auto = new ArrayList<>();
        ArrayList<Integer> t_punti = new ArrayList<>();
        for (int j = 0; j < classifica.getClassifica_team().size(); j++) {
            t_team.add(classifica.getClassifica_team().get(j).getTeam());
            t_auto.add(classifica.getClassifica_team().get(j).getAuto());
            t_punti.add(classifica.getClassifica_team().get(j).getPunti());
        }

        // Creo e visualizzo ListView customizzata piloti
        lv_piloti = (ListView) findViewById(R.id.classifica_piloti);
        CustomPilotRankingListView c1 = new CustomPilotRankingListView(RankingActivity.this, p_nomi, p_team, p_auto, p_punti);
        lv_piloti.setAdapter(c1);

        // Creo e visualizzo ListView customizzata team
        lv_team = (ListView) findViewById(R.id.classifica_team);
        CustomTeamRankingListView c2 = new CustomTeamRankingListView(RankingActivity.this, t_team, t_auto, t_punti);
        lv_team.setAdapter(c2);
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
        return (Utili.setMenu(RankingActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
