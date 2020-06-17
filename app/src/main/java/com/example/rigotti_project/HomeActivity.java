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
        setTitle("Home");

        profile_image = (ImageView) findViewById(R.id.home_profile_image);

        String foto = PersonalData.getFOTO();

        bitmap = ImageManager.StringToBitMap(foto);

        profile_image.setImageBitmap(bitmap);

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
