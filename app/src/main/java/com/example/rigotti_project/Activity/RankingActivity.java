package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.CustomChampionshipListView;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.Championship.Impostazione;
import com.example.rigotti_project.Championship.ListaCampionati;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Rankings.Classifica;
import com.example.rigotti_project.Rankings.CustomPilotRankingListView;
import com.example.rigotti_project.Rankings.CustomTeamRankingListView;
import com.example.rigotti_project.Rankings.ListaClassifiche;
import com.example.rigotti_project.Rankings.PuntiPilota;
import com.example.rigotti_project.Rankings.PuntiTeam;
import com.example.rigotti_project.Support.Utili;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class RankingActivity extends AppCompatActivity {

    private ListaClassifiche lista_classifiche;
    private Classifica classifica;
    private Integer indice_campionato;

    //View
    private ListView lv_piloti, lv_team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setTitle("Classifiche");
        lista_classifiche = new ListaClassifiche();

        //Setto indice_campionato
        // region CHECK INTENT
        Intent i = getIntent();
        if (!i.hasExtra("indice_campionato")) {
            Utili.doToast(this, "Campionato non disponibile.");
            Intent new_i = new Intent(this, HomeActivity.class);
            startActivity(new_i);
        } else {
            indice_campionato = i.getIntExtra("indice_campionato", -1);
            if (indice_campionato == -1) {
                Utili.doToast(this, "Campionato non disponibile.");
                Intent new_i = new Intent(this, HomeActivity.class);
                startActivity(new_i);
            }
        }
        // endregion //

        readClassifiche();
        classifica = lista_classifiche.getLista_classifiche().get(indice_campionato);
        ArrayList<String> p_nomi = new ArrayList<>();
        ArrayList<String> p_team = new ArrayList<>();
        ArrayList<String> p_auto = new ArrayList<>();
        ArrayList<Integer> p_punti = new ArrayList<>();
        for (int j = 0; j < classifica.getClassifica_piloti().size(); j++) {
            p_nomi.add(classifica.getClassifica_piloti().get(j).getNome());
            p_team.add(classifica.getClassifica_piloti().get(j).getTeam());
            p_auto.add(classifica.getClassifica_piloti().get(j).getAuto());
            p_punti.add(classifica.getClassifica_piloti().get(j).getPunti());
        }

        ArrayList<String> t_team = new ArrayList<>();
        ArrayList<String> t_auto = new ArrayList<>();
        ArrayList<Integer> t_punti = new ArrayList<>();
        for (int j = 0; j < classifica.getClassifica_team().size(); j++) {
            t_team.add(classifica.getClassifica_team().get(j).getTeam());
            t_auto.add(classifica.getClassifica_team().get(j).getAuto());
            t_punti.add(classifica.getClassifica_team().get(j).getPunti());
        }

        lv_piloti = (ListView) findViewById(R.id.classifica_piloti);
        CustomPilotRankingListView c1 = new CustomPilotRankingListView(RankingActivity.this, p_nomi, p_team, p_auto, p_punti);
        lv_piloti.setAdapter(c1);

        lv_team = (ListView) findViewById(R.id.classifica_team);
        CustomTeamRankingListView c2 = new CustomTeamRankingListView(RankingActivity.this, t_team, t_auto, t_punti);
        lv_team.setAdapter(c2);
    }

    public void readClassifiche() {
        String json = Utili.getClassifiche(this);
        Classifica cla;


        if (!json.equals("error")) {
            ArrayList<Classifica> classifiche = new ArrayList<>();
            // id
            // nome
            // logo
            // classifica-piloti
            // classifica-team

            try {

                final JSONObject jsonObj = new JSONObject(json);

                final JSONArray j_classifica = jsonObj.getJSONArray("campionati");
                //CICLO PER TUTTE LE CLASSIFICHE DEI CAMPIONATI
                for (int i = 0; i < j_classifica.length(); i++) {
                    JSONObject a = j_classifica.getJSONObject(i);

                    //LEGGO DATI DAL JSON
                    Integer id = a.getInt("id");
                    String nome_campionato = a.getString("nome");
                    String logo_png = a.getString("logo");

                    // region CONVERTO NOME DEL LOGO NEL "RESOURCE_ID" CORRISPONDETE
                    String logo = Utili.getNameLogo(logo_png);
                    Integer id_logo = Utili.getResId(this, logo);
                    // endregion

                    final JSONArray j_classifica_piloti = a.getJSONArray("classifica-piloti");
                    //CICLO PER TUTTE LE GARE
                    ArrayList<PuntiPilota> classifica_piloti = new ArrayList<>();
                    for (int j = 0; j < j_classifica_piloti.length(); j++) {
                        JSONObject b = j_classifica_piloti.getJSONObject(j);

                        //LEGGO DATI DAL JSON
                        String nome = b.getString("nome");
                        String team = b.getString("team");
                        String auto = b.getString("auto");
                        Integer punti = b.getInt("punti");

                        PuntiPilota punti_pilota = new PuntiPilota(nome, team, auto, punti);

                        //INSERISCO LE GARE NEL CALENDARIO
                        classifica_piloti.add(punti_pilota);
                    }

                    final JSONArray j_classifica_team = a.getJSONArray("classifica-team");
                    //CICLO PER TUTTE LE IMPOSTAZIONI
                    ArrayList<PuntiTeam> classifica_team = new ArrayList<>();
                    for (int k = 0; k < j_classifica_team.length(); k++) {
                        JSONObject c = j_classifica_team.getJSONObject(k);

                        //LEGGO DATI DAL JSON
                        String team = c.getString("team");
                        String auto = c.getString("auto");
                        Integer punti = c.getInt("punti");

                        PuntiTeam punti_team = new PuntiTeam(team, auto, punti);
                        //INSERISCO IMPOSTAZIONE NELLA LISTA DELLE IMPOSTZIONI
                        classifica_team.add(punti_team);
                    }

                    // Creo nuova classifica per campionato
                    Collections.sort(classifica_piloti);
                    Collections.sort(classifica_team);
                    cla = new Classifica(id, nome_campionato, id_logo, classifica_piloti, classifica_team);

                    //Aggiungo campionato alla lista
                    classifiche.add(cla);
                }

                //SALVO LA LISTA DELLE CLASSIFICHE
                lista_classifiche.setLista_classifiche(classifiche);

            } catch (final JSONException e) {
                e.printStackTrace();
                Utili.doToast(this, "Errore nella lettura dei dati");
            }

        }
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
        return (Utili.setMenu(RankingActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

    // endregion
}
