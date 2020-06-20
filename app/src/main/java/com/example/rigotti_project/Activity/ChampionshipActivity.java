package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

public class ChampionshipActivity extends AppCompatActivity {

    TextView tv_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);

        //NOME CAMPIONATO?
        setTitle("Campionato");

        Intent i = getIntent();
        Integer id_campionato = i.getIntExtra("id_campionato",-1);

        tv_id = (TextView) findViewById(R.id.test_id);
        tv_id.setText(id_campionato.toString());


    }

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
        return (Utili.setMenu(ChampionshipActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

}
