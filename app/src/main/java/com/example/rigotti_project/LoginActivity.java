package com.example.rigotti_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private String s_email, s_password;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        email = (EditText) findViewById(R.id.email_text);
        password = (EditText) findViewById(R.id.password_text);

        Intent i = getIntent();

        if (i.hasExtra("email")) {
            Utili.doToast(this, "Registrazione effettuata correttamente!\n" +
                    "Esegui il login e accedi");
            email.setText(i.getStringExtra("email"));
        }

        //BOTTONE LOGIN
        final Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //controllo correttezza tramite un metodo dei campi inseriti
                if (checkLoginData()) {
                    //SETTO LO "STATO"
                    Utili.setSTATUS(Utili.LOGGED);
                    Utili.setEMAIL(s_email);

                    //Creo nuova intent per passare alla home page
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);

                    //PRENDO I DATI DAL DB (da mandare alla prossima activity)
                    Cursor cursor = db.getUser(Utili.getEMAIL());

                    if (cursor.moveToFirst()) {
                        Integer id = cursor.getInt(0);
                        String nome = cursor.getString(1);
                        String cognome = cursor.getString(2);
                        String email = cursor.getString(3);
                        String data = cursor.getString(4);
                        String nazione = cursor.getString(5);
                        Integer numero = cursor.getInt(6);
                        String preferito = cursor.getString(7);
                        String odiato = cursor.getString(8);
                        String auto = cursor.getString(9);
                        String foto = cursor.getString(10);
                        String password = cursor.getString(11);
                        i.putExtra("id", id);
                        i.putExtra("nome", nome);
                        i.putExtra("cognome", cognome);
                        i.putExtra("email", email);
                        i.putExtra("data", data);
                        i.putExtra("nazione", nazione);
                        i.putExtra("numero", numero);
                        i.putExtra("preferito", preferito);
                        i.putExtra("odiato", odiato);
                        i.putExtra("auto", auto);
                        i.putExtra("foto", foto);
                        i.putExtra("password", password);

                    }

                    //Vado alla nuova activity
                    startActivity(i);
                } else {
                    //login errato
                    Utili.doToast(LoginActivity.this, "Email o password sbagliati");
                }
            }
        });

        //BOTTONE REGISTRAZIONE
        final Button btn_reg = (Button) findViewById(R.id.btn_registrazione);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creo nuova intent per passare alla register activity
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public boolean checkLoginData() {

        s_email = email.getText().toString();
        s_password = password.getText().toString();

        String err;
        //se l'utente non ha inserito l'email o la password
        if (s_email.isEmpty() || s_password.isEmpty()) {
            err = "\"Inserisci email e  password negli appositi campi.\"";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.isEmailValid(email.getText().toString())) {
            err = "Inserisci un'email valida. Es: nomeesempio@dominio.org";
            Utili.doToast(this, err);
            return false;
        } else if (!Utili.isEmailValid(email.getText().toString())) {
            err = "Sicuro di non aver dimenticato la password?\nConsiglio: la password è lunga almeno 8 caratteri con minimo una maiuscola, un carattere speciale e un numero.";
            Utili.doToast(this, err);
            return false;
        }

        //se l'utente ha inserito dei dati -->
        //controllo che corrispondano ad un utente esistente chiamando il metodo "checkUserPassword(email, password)"
        return (db.checkUserPassword(s_email, s_password)) ? true : false;

    }

    //BLOCCO BOTTONE INDIETRO PER EVITARE DI RIACCEDERE SENZA ESEGUIRE IL LOGIN

    @Override
    public void onBackPressed() {
        if (Utili.getSTATUS() == 0) {
            Utili.doToast(this, "Esegui il login!");
            return;
        } else {
            super.onBackPressed();
        }
    }

}
