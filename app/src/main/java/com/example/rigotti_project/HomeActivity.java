package com.example.rigotti_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;


public class HomeActivity extends AppCompatActivity {

    //Immagine
    private Bitmap bitmap;
    ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent getIntent = getIntent();

        profile_image = (ImageView) findViewById(R.id.home_profile_image);

        String foto = getIntent.getStringExtra("foto");

        bitmap = Utili.StringToBitMap(foto);

        profile_image.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_account:
                //vado alla activity account;
                Utili.doToast(this,"Funziona1");
                return true;
            case R.id.item_home:
                //vado alla activity home;
                Utili.doToast(this,"Funziona2");
                return true;
            case R.id.item_campionati:
                //vado alla activity campionati;
                return true;
            case R.id.item_modifica_dati:
                //vado alla activity modifica_dati;
                return true;
            case R.id.item_logout:
                //eseguo logout
                //vado alla activity login;
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
