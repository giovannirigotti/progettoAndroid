package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.CustomCalendarListView;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    private Integer indice_campionato;

    //Campionato
    private Campionato campionato;

    private ListView lv;
    private TextView title;

    private ArrayList<String> date, circuiti;
    private ArrayList<Integer> sequenze;

    private ArrayList<Gara> calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        campionato = new Campionato();
        date = new ArrayList<>();
        circuiti = new ArrayList<>();
        sequenze = new ArrayList<>();

        title = (TextView) findViewById(R.id.calendar_title);
        title.setText("Calendario");

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
        calendario = campionato.getCalendario();

        setTitle(Utili.listaCampionati.getCampionato(indice_campionato).getNome());

        getRacesData();

        lv = (ListView) findViewById(R.id.lista_gare);
        CustomCalendarListView custom = new CustomCalendarListView(this, circuiti, sequenze, date);
        lv.setAdapter(custom);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenGara(CalendarActivity.this, position);
            }
        });
    }

    public void getRacesData() {
        Gara g;
        Log.e("POPOLAZIONE", "Start");
        for (int i = 0; i < calendario.size(); i++) {
            g = campionato.getGara(i);
            sequenze.add(g.getSeq());
            date.add(g.getData());
            circuiti.add(g.getCircuito());
        }
        Log.e("POPOLAZIONE", "End");
    }

    public void OpenGara(Activity context, int position) {
        Intent i = new Intent(context, RaceActivity.class);
        i.putExtra("indice_campionato", indice_campionato);
        i.putExtra("position", position);
        startActivity(i);
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
        return (Utili.setMenu(CalendarActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
