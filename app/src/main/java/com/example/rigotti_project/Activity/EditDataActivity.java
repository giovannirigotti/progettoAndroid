package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Activity per testare il servizio in background di notifica
// Si visualizzano le impostazioni dei campionati e si possono modificare
// A modifica avvenuta una variabile di stato Utili.MODIFICA viene aggiornata e il servizio se ne accorge
// ---------------------------------
// ---------------------------------

public class EditDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //View
    private Spinner spinner;
    private ListView lv;

    //Variabili
    private String[] nomi_campionati;
    private Campionato camp_to_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        setTitle("Modifica dati");
        Integer size = Utili.listaCampionati.getCampionati().size();
        nomi_campionati = new String[size];

        //Setto Views
        spinner = (Spinner) findViewById(R.id.spinner_campionati);
        lv = (ListView) findViewById(R.id.lista_edit_impostazioni);

        //Setto campionato per mandare notifica
        camp_to_edit = Utili.listaCampionati.getCampionato(0);

        for (int i = 0; i < size; i++) {
            nomi_campionati[i]=Utili.listaCampionati.getCampionato(i).getNome();
        }

        // Popolo lo spinner con i vari campionati
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nomi_campionati);

        // Creo spinner funzionante
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    // region MENU
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
        return (Utili.setMenu(EditDataActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Quando premo su un elemento aggiorno la ListView contente le impostazioni del campionato
        camp_to_edit = Utili.listaCampionati.getCampionato(position);
        ArrayList<String> tipi = new ArrayList<>();
        ArrayList<String> valori = new ArrayList<>();
        for (int j = 0; j < camp_to_edit.getImpostazioni().size(); j++) {
            tipi.add(camp_to_edit.getImpostazioni().get(j).getTipo());
            valori.add(camp_to_edit.getImpostazioni().get(j).getValore());
        }

        CustomEditDataListView custom = new CustomEditDataListView(EditDataActivity.this, tipi, valori,position);
        lv.setAdapter(custom);
        // DEBUG
        // Utili.doToast(EditDataActivity.this, "Impostazioni del campionato:\n\"" + parent.getItemAtPosition(position).toString() + "\" caricate con successo!");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // DEFAULT
    }
}
