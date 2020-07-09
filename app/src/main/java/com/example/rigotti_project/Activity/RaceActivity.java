package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

// ---------------------------------
// ---------------------------------
// Activity per la visualizzazione della gara
// Consente di visualizzare circuito, numero della gara e data
// Consente di passare ai risultati dei piloti e team della gara
// Consente di passare alla galleria fotografica dell'evento
// ---------------------------------
// ---------------------------------

public class RaceActivity extends AppCompatActivity {

    // Variabili
    private Campionato campionato;
    private Gara gara;
    private Integer position = -1;
    private Integer indice_campionato = -1;

    // View
    private TextView tv_circuito, tv_data, tv_giornata;
    private Button btn_classifica, btn_galleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
        setTitle("Gara");

        // Controllo Intent e Extra passati
        Intent i = getIntent();
        if (!i.hasExtra("position") || !i.hasExtra("indice_campionato")) {
            Utili.doToast(this, "Dati non disponibili.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }
        position = i.getIntExtra("position", -1);
        indice_campionato = i.getIntExtra("indice_campionato", -1);
        if (position == -1 || indice_campionato == -1) {
            Utili.doToast(this, "Errore caricamento dati.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        // Carico dati dal salvataggio in locale
        campionato = Utili.listaCampionati.getCampionato(indice_campionato);
        gara = campionato.getGara(position);

        tv_circuito = (TextView) findViewById(R.id.race_circuito);
        tv_data = (TextView) findViewById(R.id.race_data);
        tv_giornata = (TextView) findViewById(R.id.race_giornata);

        btn_classifica = (Button) findViewById(R.id.race_btn_classifica);
        btn_galleria = (Button) findViewById(R.id.race_btn_galleria);

        tv_giornata.setText(gara.getSeq().toString());
        tv_circuito.setText(gara.getCircuito());
        tv_data.setText(gara.getData());

        // Vado ai risultati
        btn_classifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RaceActivity.this, ResultsActivity.class);
                intent.putExtra("indice_campionato", indice_campionato);
                startActivity(intent);
            }
        });

        // Vado alla galleria
        btn_galleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RaceActivity.this, GalleryActivity.class);
                intent.putExtra("indice_campionato", indice_campionato);
                startActivity(intent);
            }
        });
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
        return (Utili.setMenu(RaceActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
