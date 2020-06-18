package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rigotti_project.Support.ImageManager;
import com.example.rigotti_project.Support.PersonalData;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;


public class HomeActivity extends AppCompatActivity {

    //Immagine
    private Bitmap bitmap;
    private ImageView profile_image;

    //Textview
    private TextView nome, nazione, numero;

    //Button
    private Button campionati, modifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        //Imposto immagine
        profile_image = (ImageView) findViewById(R.id.home_profile_image);
        String foto = PersonalData.getFOTO();
        bitmap = ImageManager.StringToBitMap(foto);
        profile_image.setImageBitmap(bitmap);

        //Imposto dati utente
        nome = (TextView) findViewById(R.id.home_nome);
        nazione = (TextView) findViewById(R.id.home_nazione);
        numero = (TextView) findViewById(R.id.home_numero);
        nome.setText(PersonalData.getNOME());
        nazione.setText(PersonalData.getNAZIONE());
        numero.setText(PersonalData.getNUMERO().toString());

        //Imposto bottoni
        campionati = (Button) findViewById(R.id.btn_home_campionati);
        modifica = (Button) findViewById(R.id.btn_home_modifica);

        campionati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ChampionshipListActivity.class);
                startActivity(i);
            }
        });

        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, EditDataActivity.class);
                startActivity(i);
            }
        });


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
        return (Utili.setMenu(HomeActivity.this,item)) ? true : super.onOptionsItemSelected(item);
    }
}
