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

import com.example.rigotti_project.Championship.CustomChampionshipListView;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.Championship.Impostazione;
import com.example.rigotti_project.Championship.ListaCampionati;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.Utili;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Activity per la visualizzazione della lista dei campionati
// Consente di visualizzare logo e nome dei campionati
// Consente di passare alla Activity del campionato corrispondete a quello premuto dall'utente
// ---------------------------------
// ---------------------------------

public class ChampionshipListActivity extends AppCompatActivity {

    // DB
    private DatabaseHelper db;

    // View
    private ListView lv;

    // Per ListView
    private ArrayList<Integer> champID;
    private ArrayList<Integer> imgID;
    private ArrayList<String> nomi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship_list);
        setTitle("Lista campionati");

        db = new DatabaseHelper(this);

        nomi = new ArrayList<>();
        imgID = new ArrayList<>();
        champID = new ArrayList<>();

        // "Popolo" gli ArrayList da passare al customizzatore della ListView
        for (int i = 0; i < Utili.listaCampionati.getCampionati().size(); i++) {
            Campionato a = Utili.listaCampionati.getCampionato(i);
            //LEGGO DATI DAL JSON
            Integer id = a.getId();
            String nome = a.getNome();
            Integer id_logo = a.getId_logo();
            champID.add(id);
            nomi.add(nome);
            imgID.add(id_logo);
        }

        // Creo e visualizzo e ListView customizzata
        lv = (ListView) findViewById(R.id.lista_campionati);
        CustomChampionshipListView custom = new CustomChampionshipListView(ChampionshipListActivity.this, nomi, imgID);
        lv.setAdapter(custom);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Apro campionato selezionato dall'utente
                OpenCampionato(ChampionshipListActivity.this, position);
            }
        });


    }

    public void OpenCampionato(Activity context, int position) {
        Intent i = new Intent(context, ChampionshipActivity.class);
        // passo "position" (indice campionato / id)
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
        return (Utili.setMenu(ChampionshipListActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion

}
