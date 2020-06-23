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
import android.widget.TextView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.PersonalData;
import com.example.rigotti_project.Support.Utili;

import java.util.ArrayList;

public class ChampionshipActivity extends AppCompatActivity {

    private Integer indice_campionato;

    private DatabaseHelper db;

    private String json;

    //TextView
    private TextView tv_nome;
    private TextView tv_forum;

    //ImageView
    private ImageView iv_logo;

    //Campionato
    private Campionato campionato;

    //Button
    private Button btn_calendario, btn_iscritti, btn_piloti, btn_impostazioni, btn_iscriviti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);
        setTitle("Campionato");
        campionato = new Campionato();
        db = new DatabaseHelper(this);

        json = Utili.getCampionati(this);

        Intent i = getIntent();

        // Controllo che l'intent sia stato passato correttamente
        // (setto position)
        // region CHECK INTENT
        if (!i.hasExtra("position")) {
            Utili.doToast(this, "Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        } else {
            indice_campionato = i.getIntExtra("position", -1);
            if (indice_campionato == -1) {
                Utili.doToast(this, "Campionato non disponibile.");
                Intent new_i = new Intent(this, HomeActivity.class);
                startActivity(new_i);
            }
        }
        // endregion

        //prendo campionato dalla lista campionati comune
        campionato = Utili.listaCampionati.getCampionato(indice_campionato);

        //imposto le view
        // region SET VIEW
        tv_nome = (TextView) findViewById(R.id.camp_nome);
        iv_logo = (ImageView) findViewById(R.id.camp_logo);
        tv_forum = (TextView) findViewById(R.id.tv_forum);

        btn_calendario = (Button) findViewById(R.id.btn_calendario);
        btn_piloti = (Button) findViewById(R.id.btn_piloti);
        btn_iscritti = (Button) findViewById(R.id.btn_iscritti);
        btn_impostazioni = (Button) findViewById(R.id.btn_impostazioni);
        btn_iscriviti = (Button) findViewById(R.id.btn_iscrizione);
        // endregion

        //"popolo" le view
        tv_nome.setText(campionato.getNome());
        iv_logo.setImageResource(campionato.getId_logo());
        String forum = "www.simulator.it/forum/" + campionato.getNome();
        tv_forum.setText(forum);

        // region LISTENER BOTTONI
        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChampionshipActivity.this, CalendarActivity.class);
                i.putExtra("indice_campionato", indice_campionato);
                startActivity(i);
            }
        });

        btn_iscritti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChampionshipActivity.this, MemberActivity.class);
                i.putExtra("indice_campionato", indice_campionato);
                startActivity(i);
            }
        });

        btn_impostazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChampionshipActivity.this, SettingsActivity.class);
                i.putExtra("indice_campionato", indice_campionato);
                startActivity(i);
            }
        });

        btn_piloti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChampionshipActivity.this, PilotsActivity.class);
                i.putExtra("indice_campionato", indice_campionato);
                startActivity(i);
            }
        });

        // endregion

        //GESTIONE ISCRIZIONE

        //controllo se sono iscritto
        //ALTERNATIVA: USO Funzione isMemeber su DatabaseHelper tanto gli account eliminabili sono solo quelli nuovi (Non quelli sul file json)
        Integer indice_iscritto = Utili.isMember(indice_campionato);
        if(indice_iscritto >= 0){
            //se sono iscritto setto bottone per cancellare l'iscrizione
            //Utili.doToast(this,"MEMBRO");
            btn_iscriviti.setBackgroundTintList(getResources().getColorStateList(R.color.colorRed));
            btn_iscriviti.setText("Cancella iscrizione");
            btn_iscriviti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //cancello iscrizione
                    db.deleteEntry(indice_campionato);
                    Utili.doToast(ChampionshipActivity.this,"ISCRIZIONE CANCELLATA CON SUCCESSO!");
                    //NEL FRATTEMPO BLOCCO
                    //ALTERNATIVA: RICARICO LA PAGINA!
                    btn_iscriviti.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    btn_iscriviti.setEnabled(false);
                }
            });



        }
        else{
            //se non sono iscritto setto bottone per iscrivermi
            //Utili.doToast(this,"NON MEMBRO");
            btn_iscriviti.setBackgroundTintList(getResources().getColorStateList(R.color.colorGreen));
            btn_iscriviti.setText("Iscriviti");
            //vado alla Activity per iscrivermi
            Intent z = new Intent(ChampionshipActivity.this, EnrollActivity.class);
            z.putExtra("indice_campionato", indice_campionato);
            startActivity(z);
        }

    }

    //Controllo se sono iscritto al campionato
    private Integer imMember(){
        ArrayList<Pilota> iscritti = campionato.getPiloti();
        String my_name = PersonalData.getNOME() + " "+ PersonalData.getCOGNOME();
        for (int i = 0; i < iscritti.size(); i++){
            Pilota p = iscritti.get(i);
            if(p.getNome().equals(my_name)){
                return i;
            }
        }
        return -1;
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
        return (Utili.setMenu(ChampionshipActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion

}
