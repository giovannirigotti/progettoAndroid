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

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.Utili;

public class DetailsActivity extends AppCompatActivity {

    //VIEW
    private ImageView img;
    private Button btn_share;

    //VARIABILI
    private Integer id_risorsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Dettagli");

        //prendo nome della foto dalla activity galleria che la apre
        Intent i = getIntent();
        if (!i.hasExtra("id_risorsa")){
            Utili.doToast(this, "Foto non disponibile");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        }else{
            id_risorsa = i.getIntExtra("id_risorsa",-1);
            if (id_risorsa == -1){
                Utili.doToast(this, "Foto non disponibile");
                Intent new_i = new Intent(this, HomeActivity.class);
                startActivity(new_i);
            }
        }

        //prendo ImageView e Button
        img = (ImageView) findViewById(R.id.dettagli_img);
        btn_share = (Button) findViewById(R.id.dettagli_btn);
        //setto immagine tramite resource_id (int)
        img.setImageResource(id_risorsa);
        //setto OnClickListener
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utili.shareImage(DetailsActivity.this, id_risorsa);
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
        return (Utili.setMenu(DetailsActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
