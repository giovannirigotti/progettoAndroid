package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

public class RaceActivity extends AppCompatActivity {

    private Integer position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
        setTitle("Gara");

        Intent i = getIntent();
        if(i.hasExtra("position")){
            position = i.getIntExtra("position",-1);
        }
        if(position == -1){
            Utili.doToast(this,"Dati gara non disponibili");
        }

        Utili.doToast(this,"OK, position: "+position.toString());
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
