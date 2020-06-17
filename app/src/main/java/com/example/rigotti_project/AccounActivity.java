package com.example.rigotti_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class AccounActivity extends AppCompatActivity {

    //Database
    DatabaseHelper db;

    //VIEW DELLA PAGINA
    //TextView
    private TextView nome,cognome,email,data,nazione;

    //Bottoni
    private Button btn_cambia;

    //Immagine
    private ImageView profile_image;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accoun);

        db = new DatabaseHelper(this);

        // "INIZIALIZZO VIEW"
        nome = (TextView) findViewById(R.id.acc_nome);
        cognome = (TextView) findViewById(R.id.acc_cognome);
        email = (TextView) findViewById(R.id.acc_email);
        nazione = (TextView) findViewById(R.id.acc_nazione);
        data = (TextView) findViewById(R.id.acc_data);

        profile_image = (ImageView) findViewById(R.id.acc_profile_image);

        btn_cambia = (Button) findViewById(R.id.btn_cambia_foto);

        //POPOLO VIEW CON DATI UTENTE
        nome.setText(PersonalData.getNOME());
        cognome.setText(PersonalData.getCOGNOME());
        email.setText(PersonalData.getEMAIL());
        nazione.setText(PersonalData.getNAZIONE());
        data.setText(PersonalData.getDATA());

        //OSS:  setto immagine convertendo la foto salvata come Stringa (in PersonalData)
        //      a Bitmap tramite il metodo Utili.StringToBitMap(stringa)
        profile_image.setImageBitmap(ImageManager.StringToBitMap(PersonalData.getFOTO()));

        btn_cambia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //premission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show pop up for runtime permission
                        requestPermissions(permissions, ImageManager.PERMISSION_CODE);

                    } else {
                        //permission already granted
                        ImageManager.chooseFile(AccounActivity.this);
                    }
                } else {
                    //system os is less then marshmellow
                    ImageManager.chooseFile(AccounActivity.this);
                }
            }
        });
    }

    //GESTIONE IMMAGINE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImageManager.IMAGE_PICK_CODE) {
            // set image to image view
            Uri filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);

                String new_img = ImageManager.BitMapToString(bitmap);

                //SALVO NUOVA IMMAGINE IN LOCALE
                PersonalData.setFOTO(new_img);

                //SALVO NUOVA IMMAGINE SUL DB
                db.updateImage(new_img,PersonalData.getEMAIL());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ImageManager.PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    ImageManager.chooseFile(AccounActivity.this);
                } else {
                    // permission was dained
                    Utili.doToast(this, "Permesso negato");
                }
            }
        }
    }

    // AGGIUNGO MENU ALLA ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //GESTISCO MENU NELLA ACTIVITY
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Ritorno True se il quando premo su un Item questo è nello switch case
        // Ritrono "super.onOptionsItemSelected(item)" altrimenti
        return (Utili.setMenu(AccounActivity.this,item)) ? true : super.onOptionsItemSelected(item);
    }

}
