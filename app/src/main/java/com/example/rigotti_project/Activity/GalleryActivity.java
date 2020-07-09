package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Activity per la visualizzazione delle fotografie (Galleria fotografica)
// e la loro eventuale visualizzione e condivisione
// ---------------------------------
// ---------------------------------

public class GalleryActivity extends AppCompatActivity {
    // VARIABILI
    private ArrayList<String> nomi_foto;

    // View
    private ListView lista_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setTitle("Galleria");

        nomi_foto = new ArrayList<>();

        String prefisso = "th_gte";

        for (int i = 1; i <= Utili.NUMERO_FOTO; i++) {
            String nome_risorsa = prefisso + String.valueOf(i);
            nomi_foto.add(nome_risorsa);
        }

        // Creo e visualizzo ListView customizzata
        lista_foto = (ListView) findViewById(R.id.lista_foto);
        CustomGalleryList custom = new CustomGalleryList(this, nomi_foto);
        lista_foto.setAdapter(custom);

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
        return (Utili.setMenu(GalleryActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
