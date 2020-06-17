package com.example.rigotti_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ManageDataActivity extends AppCompatActivity {

    //Database
    DatabaseHelper db;

    //VIEW DELLA PAGINA
    //EditText
    private EditText numero, preferito, odiato, auto;

    //Button
    private Button btn_aggiorna_dati;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);
        setTitle("Dati di gioco");

        db = new DatabaseHelper(this);

        // "INIZIALIZZO VIEW"
        numero = (EditText) findViewById(R.id.acc_numero);
        preferito = (EditText) findViewById(R.id.acc_preferito);
        odiato = (EditText) findViewById(R.id.acc_odiato);
        auto = (EditText) findViewById(R.id.acc_auto);

        btn_aggiorna_dati = (Button) findViewById(R.id.btn_aggiorna_dati_gioco);

        //POPOLO VIEW CON DATI UTENTE
        numero.setText(PersonalData.getNUMERO().toString());
        preferito.setText(PersonalData.getPREFERITO());
        odiato.setText(PersonalData.getODIATO());
        auto.setText(PersonalData.getAUTO());
    }
}
