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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class EndRegisterActivity extends AppCompatActivity {

    //Database
    DatabaseHelper db;

    //Immagine
    private Bitmap bitmap;
    ImageView profile_image;

    //Bottoni
    private Button btn_registrati, btn_immagine;

    //EditText
    private EditText numero, nazione, circuito_preferito, circuito_odiato, auto;

    //Stringhe / Ineteri
    private Integer _numero;
    private String _nazione, _circuito_preferito, _circuito_odiato, _auto, _foto;
    private String _nome, _cognome, _email, _pass, _data;

    private int OK = 0;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_register);

        db = new DatabaseHelper(this);
        Intent i = getIntent();
        _nome = i.getStringExtra("nome").trim();
        _cognome = i.getStringExtra("cognome").trim();
        _email = i.getStringExtra("email").trim();
        _pass = i.getStringExtra("password").trim();
        _data = i.getStringExtra("data").trim();

        numero = (EditText) findViewById(R.id.end_numero);
        nazione = (EditText) findViewById(R.id.end_nazione);
        circuito_preferito = (EditText) findViewById(R.id.end_circuito_preferito);
        circuito_odiato = (EditText) findViewById(R.id.end_circuito_odiato);
        auto = (EditText) findViewById(R.id.end_auto);

        btn_registrati = (Button) findViewById(R.id.btn_registrazione);
        btn_immagine = (Button) findViewById(R.id.btn_up_image);

        profile_image = (ImageView) findViewById(R.id.profile_image);

        btn_immagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //premission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show pop up for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);

                    } else {
                        //permission already granted
                        chooseFile();
                    }
                } else {
                    //system os is less then marshmellow
                    chooseFile();
                }
            }
        });

        btn_registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se l'utente inserisce i dati correttamente posso procedere
                if (checkEndRegisterData()) {
                    _numero = Integer.parseInt(numero.getText().toString().trim());
                    _nazione = nazione.getText().toString().trim();
                    _circuito_preferito = circuito_preferito.getText().toString().trim();
                    _circuito_odiato = circuito_odiato.getText().toString().trim();
                    _auto = auto.getText().toString().trim();

                    //Converto bitmap in strings per salvare la foto.
                    _foto = Utili.BitMapToString(bitmap);

                    //salva dati sul DB;
                    db.addUser(_nome, _cognome, _email, _data, _nazione, _numero, _circuito_preferito, _circuito_odiato, _auto, _foto, _pass);


                    //Vado al login;
                    Intent i = new Intent(EndRegisterActivity.this, MainActivity.class);
                    i.putExtra("email", _email);
                    startActivity(i);

                }
            }
        });


    }

    public boolean checkEndRegisterData() {
        String _numero, _nazione, _circuito_preferito, _circuito_odiato, _auto;
        _numero = numero.getText().toString();
        _nazione = nazione.getText().toString();
        _circuito_preferito = circuito_preferito.getText().toString();
        _circuito_odiato = circuito_odiato.getText().toString();
        _auto = auto.getText().toString();
        String err;
        if (_numero.isEmpty() || _nazione.isEmpty() || _circuito_odiato.isEmpty() || _circuito_preferito.isEmpty() || _auto.isEmpty() || OK != 1) {
            err = "Inserisci tutti, se non hai preferenze sulle piste scrivi quello che ti pare,\n Assicurati di aver caricato un'immagine profilo!";
            Utili.doToast(this, err);
            return false;
        } else if (Utili.validateNumero(_numero) < 0) {
            Utili.doToast(this, _numero);
            err = "Assicurati di aver inserito solo numeri nel campo \'Numero di gara\'\nOss: numeri solo tra 0 e 999";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validateSimpleText(_nazione, 20)) {
            err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Nazionalità\'\nLunghezza massima 20 caratteri";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validateSimpleText(_circuito_preferito, 40)) {
            err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Circuito preferito\'\nLunghezza massima 40 caratteri";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validateSimpleText(_circuito_odiato, 40)) {
            err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Circuito odiato\'\nLunghezza massima 40 caratteri";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validateSimpleText(_auto, 30)) {
            err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Auto preferita\'\nLunghezza massima 30 caratteri";
            Utili.doToast(this, err);
            return false;
        }

        return true;
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        OK = 1;
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // set image to image view
            Uri filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
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
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    chooseFile();
                } else {
                    // permission was dained
                    Utili.doToast(this, "Permesso negato");
                }
            }
        }
    }
}
