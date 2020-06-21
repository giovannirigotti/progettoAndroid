package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

public class ChampionshipActivity extends AppCompatActivity {

    //TextView
    private TextView tv_nome;
    private TextView tv_forum;

    //ImageView
    private ImageView iv_logo;

    //Campionato
    private Campionato campionato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);
        setTitle("Campionato");
        campionato = new Campionato();

        Intent i = getIntent();

        if(!i.hasExtra("position")){
            Utili.doToast(this,"Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        Integer position = i.getIntExtra("position",-1);

        campionato = Utili.listaCampionati.getCampionato(position);

        if(position == -1){
            Utili.doToast(this,"Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        String forum = "www.simulator.forum.id_campionato_"+campionato.getId().toString()+".it";

        tv_nome = (TextView) findViewById(R.id.camp_nome);
        iv_logo = (ImageView) findViewById(R.id.camp_logo);
        tv_forum = (TextView) findViewById(R.id.tv_forum);

        tv_nome.setText(campionato.getNome());
        iv_logo.setImageResource(campionato.getId_logo());
        tv_forum.setText(forum);

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
