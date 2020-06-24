package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class EnrollActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //GESTIONE
    private Campionato c;
    private Integer indice_campionato;
    private DatabaseHelper db;
    private String auto;
    private String[] automobili;
    private String auto_to_add;
    private String team_to_add;

    //VIEW
    private Spinner spinner;
    private Button btn_iscriviti;
    private EditText et_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        setTitle("Iscrizione");

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

        //Setto Views
        spinner = (Spinner) findViewById(R.id.spinner_auto);
        btn_iscriviti = (Button) findViewById(R.id.btn_enroll_iscriviti);
        et_team = (EditText) findViewById(R.id.enroll_team);


        c = Utili.listaCampionati.getCampionato(indice_campionato);
        db = new DatabaseHelper(this);
        auto = c.getAuto();
        automobili = auto.split(",");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EnrollActivity.this,
                android.R.layout.simple_spinner_item, automobili);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btn_iscriviti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntry()) {
                    db.addEntry(indice_campionato,auto_to_add,team_to_add);
                    Utili.doToast(EnrollActivity.this, "Iscrizione effettuata al campionato!");
                    Intent i = new Intent(EnrollActivity.this, ChampionshipActivity.class);
                    i.putExtra("position",indice_campionato);
                    startActivity(i);
                }
            }
        });

    }

    public boolean checkEntry() {
        team_to_add = et_team.getText().toString();
        if (team_to_add.isEmpty() || auto_to_add.isEmpty()) {
            Utili.doToast(this, "Assicurati di selezionare un auto e inserire un team.");
            return false;
        } else {
            if(!Utili.validateSimpleText(team_to_add,40)){
                Utili.doToast(this, "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Team\'\nLunghezza massima 40 caratteri");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        auto_to_add = automobili[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        Utili.doToast(this, "Seleziona un auto dalla lista.");
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
        return (Utili.setMenu(EnrollActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
