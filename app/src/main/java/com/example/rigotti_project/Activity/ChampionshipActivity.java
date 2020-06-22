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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class ChampionshipActivity extends AppCompatActivity {

    private Integer position;

    //TextView
    private TextView tv_nome;
    private TextView tv_forum;

    //ImageView
    private ImageView iv_logo;

    //Campionato
    private Campionato campionato;

    //Button
    private Button btn_calendario, btn_iscritti, btn_classifica, btn_impostazioni, btn_iscriviti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);
        setTitle("Campionato");
        campionato = new Campionato();

        Intent i = getIntent();

        if (!i.hasExtra("position")) {
            Utili.doToast(this, "Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        position = i.getIntExtra("position", -1);

        campionato = Utili.listaCampionati.getCampionato(position);

        if (position == -1) {
            Utili.doToast(this, "Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        String forum = "www.simulator.it/forum/"+ campionato.getNome();

        tv_nome = (TextView) findViewById(R.id.camp_nome);
        iv_logo = (ImageView) findViewById(R.id.camp_logo);
        tv_forum = (TextView) findViewById(R.id.tv_forum);

        btn_calendario = (Button) findViewById(R.id.btn_calendario);
        btn_classifica = (Button) findViewById(R.id.btn_classifica);
        btn_iscritti = (Button) findViewById(R.id.btn_iscritti);
        btn_impostazioni = (Button) findViewById(R.id.btn_impostazioni);
        btn_iscriviti = (Button) findViewById(R.id.btn_iscrizione);

        tv_nome.setText(campionato.getNome());
        iv_logo.setImageResource(campionato.getId_logo());
        tv_forum.setText(forum);

        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChampionshipActivity.this, CalendarActivity.class);
                i.putExtra("indice_campionato", position);
                startActivity(i);
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
        return (Utili.setMenu(ChampionshipActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion

}
