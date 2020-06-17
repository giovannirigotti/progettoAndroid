package com.example.rigotti_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

    //String
    private String _numero, _preferito, _odiato, _auto;

    //Integer
    private Integer new_numero;


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
        _numero = PersonalData.getNUMERO().toString();
        _preferito = PersonalData.getPREFERITO();
        _odiato = PersonalData.getODIATO();
        _auto = PersonalData.getAUTO();
        numero.setText(_numero);
        preferito.setText(_preferito);
        odiato.setText(_odiato);
        auto.setText(_auto);

        btn_aggiorna_dati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AGGIORNO VARIABILI AI DATI ATTUALI
                _numero = numero.getText().toString().trim();
                _preferito = preferito.getText().toString().trim();
                _odiato = odiato.getText().toString().trim();
                _auto = auto.getText().toString().trim();

                if (checkCambiamenti()) {
                    updateData();
                    Utili.doToast(ManageDataActivity.this, "Dati aggiornati con successo");
                }
            }
        });
    }

    //CONTROLLO COMPATIBILITA DATI IMMESSI DALL' UTENTE
    private boolean checkData() {
        String err;
        if (_numero.isEmpty() || _preferito.isEmpty() || _odiato.isEmpty() || _auto.isEmpty()) {
            err = "Non puoi semplicemente eliminare dei dati!\nInserisci dati in tutti i campi";
            Utili.doToast(this, err);
            return false;
        } else {
            err = "Errore compatibilità:\n";
            if (Utili.validateNumero(_numero) < 0) {
                err += "Numero di gara: inserisci solo numeri positivi tra 0 e 999";
                Utili.doToast(this, err);
                return false;
            } else if (!Utili.validateSimpleText(_preferito, 40)) {
                err += "Circuito preferito: inserisci solo lettere (massimo 40 caratteri)";
                Utili.doToast(this, err);
                return false;
            } else if (!Utili.validateSimpleText(_odiato, 40)) {
                err += "Circuito odiato: inserisci solo lettere (massimo 40 caratteri)";
                Utili.doToast(this, err);
                return false;
            } else if (!Utili.validateSimpleText(_auto, 40)) {
                err += "Auto preferita: inserisci solo lettere (massimo 40 caratteri)";
                Utili.doToast(this, err);
                return false;
            }
        }

        //già controllato --> posso convertire senza problemi
        new_numero = Integer.parseInt(_numero);

        return true;
    }

    // TRUE se avvengono cambiamenti, FALSE altrimenti
    private boolean checkCambiamenti() {
        String msg;
        if (checkData()) {
            if (new_numero != PersonalData.getNUMERO()) {
                return true;
            } else if (!_preferito.equals(PersonalData.getPREFERITO())) {
                return true;
            } else if (!_odiato.equals(PersonalData.getODIATO())) {
                return true;
            } else if (!_auto.equals(PersonalData.getAUTO())) {
                return true;
            } else {
                msg = "Non hai modificato alcun campo...\nNiente da aggiornare!";
                Utili.doToast(ManageDataActivity.this, msg);
                return false;
            }
        } else {
            return false;
        }
    }

    private void updateData(){
        PersonalData.setNUMERO(new_numero);
        PersonalData.setPREFERITO(_preferito);
        PersonalData.setODIATO(_odiato);
        PersonalData.setAUTO(_auto);
        db.updateDatiGioco(new_numero,_preferito,_odiato,_auto);
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
        return (Utili.setMenu(ManageDataActivity.this,item)) ? true : super.onOptionsItemSelected(item);
    }
}
