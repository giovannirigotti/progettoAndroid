package com.example.rigotti_project;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText nome, cognome, email, password, conferma, data;
    private String _nome, _cognome, _email, _password, _conferma, _data;
    private Button btn_procedi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registrazione pt.1");

        nome = (EditText) findViewById(R.id.et_nome);
        cognome = (EditText) findViewById(R.id.et_cognome);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_pass);
        conferma = (EditText) findViewById(R.id.et_confirm_pass);
        data = (EditText) findViewById(R.id.et_data);

        btn_procedi = (Button) findViewById(R.id.btn_procedi);
        btn_procedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //controllo dati inseriti

                if (checkRegisterData()) {
                    //se i dati sono ok li passo alla prossima activity
                    //tramite intent.putExtra
                    Intent i = new Intent(RegisterActivity.this, EndRegisterActivity.class);

                    i.putExtra("nome", nome.getText().toString());
                    i.putExtra("cognome", cognome.getText().toString());
                    i.putExtra("email", email.getText().toString());
                    i.putExtra("password", password.getText().toString());
                    i.putExtra("data", data.getText().toString());

                    startActivity(i);
                }

            }
        });
    }

    public boolean checkRegisterData() {

        _nome = nome.getText().toString();
        _cognome = cognome.getText().toString();
        _email = email.getText().toString();
        _password = password.getText().toString();
        _conferma = conferma.getText().toString();
        _data = data.getText().toString();

        String err;


        if (_nome.isEmpty() || _cognome.isEmpty() || _email.isEmpty() || _password.isEmpty() || _conferma.isEmpty() || _data.isEmpty()) {
            err = "Tutti i campi sono obbligatori, non dimenticarne manco uno";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validateSimpleText(_nome, 15)) {
            err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Nome\'\nLunghezza massima 25 caratteri";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validateSimpleText(_cognome, 15)) {
            err = "Assicurati di aver inserito solo caratteri alfabetici nel campo \'Cognome\'\nLunghezza massima 25 caratteri";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.isEmailValid(_email)) {
            err = "Inserisci un'email valida. Es: nomeesempio@dominio.org";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.validatePassword(_password)) {
            err = "Inserisci una password lunga almeno 8 caratteri con minimo una maiuscola, un carattere speciale e un numero";
            Utili.doToast(this, err);
            return false;
        } else if (!(_password.equals(_conferma))) {
            err = "Le password non sono uguali, assicurati di aver scritto correttamente la password";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.checkBirthDate(data.getText().toString())) {
            err = "La data di nascita è inserita erroneamente\n Assicurati di usare il formato gg/mm/yyyy";
            Utili.doToast(this, err);
            return false;
        }
        return true;
    }
}
