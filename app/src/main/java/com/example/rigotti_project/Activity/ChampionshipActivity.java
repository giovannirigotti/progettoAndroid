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

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

public class ChampionshipActivity extends AppCompatActivity {

    //TextView
    private TextView tv_nome;

    //ImageView
    private ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);
        setTitle("Campionato");

        Intent i = getIntent();

        if(!i.hasExtra("position") || !i.hasExtra("nome") || !i.hasExtra("logo")){
            Utili.doToast(this,"Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        Integer position = i.getIntExtra("position",-1);
        Integer id_logo = i.getIntExtra("logo", -1);
        String nome = i.getStringExtra("nome");

        if(position == -1 || id_logo == -1){
            Utili.doToast(this,"Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }

        tv_nome = (TextView) findViewById(R.id.camp_nome);
        iv_logo = (ImageView) findViewById(R.id.camp_logo);

        tv_nome.setText(nome);
        iv_logo.setImageResource(id_logo);





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
