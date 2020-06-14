package com.example.rigotti_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
}
