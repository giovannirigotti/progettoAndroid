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
import com.example.rigotti_project.Championship.CustomSettingsListView;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Rankings.CustomPilotRankingListView;
import com.example.rigotti_project.Rankings.CustomTeamRankingListView;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private Integer indice_campionato;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Impostazioni");

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

        Campionato campionato = Utili.listaCampionati.getCampionato(indice_campionato);
        ArrayList<String> tipi = new ArrayList<>();
        ArrayList<String> valori = new ArrayList<>();
        for (int j = 0; j < campionato.getImpostazioni().size(); j++) {
            tipi.add(campionato.getImpostazioni().get(j).getTipo());
            valori.add(campionato.getImpostazioni().get(j).getValore());
        }

        lv = (ListView) findViewById(R.id.lista_impostazioni);
        CustomSettingsListView custom = new CustomSettingsListView(SettingsActivity.this, tipi, valori);
        lv.setAdapter(custom);
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
        return (Utili.setMenu(SettingsActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
