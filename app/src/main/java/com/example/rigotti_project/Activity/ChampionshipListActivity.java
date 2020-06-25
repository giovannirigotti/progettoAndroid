package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rigotti_project.Championship.CustomChampionshipListView;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.Championship.Impostazione;
import com.example.rigotti_project.Championship.ListaCampionati;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Support.DatabaseHelper;
import com.example.rigotti_project.Support.Utili;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChampionshipListActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private String json;

    private ListView lv;

    private ArrayList<Integer> champID;

    private ArrayList<Integer> imgID;

    private ArrayList<String> nomi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship_list);
        setTitle("Lista campionati");

        db = new DatabaseHelper(this);

        json = Utili.getCampionati(this);

        ReadData();
        db.updatePiloti();


        lv = (ListView) findViewById(R.id.lista_campionati);
        CustomChampionshipListView custom = new CustomChampionshipListView(ChampionshipListActivity.this, nomi, imgID);
        lv.setAdapter(custom);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenCampionato(ChampionshipListActivity.this, position);
            }
        });


    }


    public void ReadData() {
        if (!json.equals("error")) {
            ListaCampionati campionati = new ListaCampionati();
            ArrayList<Campionato> lista_campionati = new ArrayList<>();
            // id
            // nome
            // logo
            // calendario
            // impostazioni
            // auto
            // piloti

            nomi = new ArrayList<>();
            imgID = new ArrayList<>();
            champID = new ArrayList<>();

            try {

                final JSONObject jsonObj = new JSONObject(json);

                final JSONArray j_campionati = jsonObj.getJSONArray("campionati");
                //CICLO PER TUTTI I CAMPIONATI
                for (int i = 0; i < j_campionati.length(); i++) {
                    JSONObject a = j_campionati.getJSONObject(i);

                    //LEGGO DATI DAL JSON
                    Integer id = a.getInt("id");
                    String nome = a.getString("nome");
                    String logo_png = a.getString("logo");

                    // region CONVERTO NOME DEL LOGO NEL "RESOURCE_ID" CORRISPONDETE
                    String logo = Utili.getNameLogo(logo_png);
                    Integer id_logo = Utili.getResId(this, logo);
                    // endregion

                    final JSONArray j_calendario = a.getJSONArray("calendario");
                    //CICLO PER TUTTE LE GARE
                    ArrayList<Gara> calendario = new ArrayList<>();
                    for (int j = 0; j < j_calendario.length(); j++) {
                        JSONObject b = j_calendario.getJSONObject(j);

                        //LEGGO DATI DAL JSON
                        Integer seq = b.getInt("seq");
                        String data = b.getString("data");
                        String circuito = b.getString("circuito");

                        //CREO GARA
                        Gara gara = new Gara(seq,data,circuito);

                        //INSERISCO LE GARE NEL CALENDARIO
                        calendario.add(gara);
                    }

                    final JSONArray j_impostazioni = a.getJSONArray("impostazioni-gioco");
                    //CICLO PER TUTTE LE IMPOSTAZIONI
                    ArrayList<Impostazione> impostazioni = new ArrayList<>();
                    for (int k = 0; k < j_impostazioni.length(); k++) {
                        JSONObject c = j_impostazioni.getJSONObject(k);

                        //LEGGO DATI DAL JSON
                        String tipo = c.getString("tipo");
                        String valore = c.getString("valore");

                        //CREO GARA
                        Impostazione impostazione = new Impostazione(tipo,valore);

                        //INSERISCO IMPOSTAZIONE NELLA LISTA DELLE IMPOSTZIONI
                        impostazioni.add(impostazione);
                    }

                    //LEGGO DATI DAL JSON
                    String auto = a.getString("lista-auto");

                    final JSONArray j_piloti = a.getJSONArray("piloti-iscritti");
                    //CICLO PER TUTTI I PILOTI
                    ArrayList<Pilota> piloti = new ArrayList<>();
                    for (int z = 0; z < j_piloti.length(); z++) {
                        JSONObject d = j_piloti.getJSONObject(z);

                        //LEGGO DATI DAL JSON
                        String p_nome = d.getString("nome");
                        String p_team = d.getString("team");
                        String p_auto = d.getString("auto");

                        //CREO PILOTA
                        Pilota pilota = new Pilota(p_nome,p_team,p_auto);

                        //INSERISCO PILOTA NELLA LISTA DEI PILOTI ISCRITTI
                        piloti.add(pilota);
                    }

                    // Creo nuovo campionato
                    Campionato campionato = new Campionato(id, nome, id_logo, calendario, impostazioni, auto, piloti);

                    //Aggiungo campionato alla lista
                    lista_campionati.add(campionato);
                    Utili.listaCampionati = new ListaCampionati(lista_campionati);

                    Log.e("DEBUG", "AGGIUNGO CAMPIONATO ALLA LISTA");


                    champID.add(id);
                    nomi.add(nome);
                    imgID.add(id_logo);
                }

                //SALVO LA LISTA DEI CAMPIONATI
                campionati.setCampionati(lista_campionati);

            } catch (final JSONException e) {
                e.printStackTrace();
                Utili.doToast(this, "Errore nella lettura dei dati");
            }

        }
    }

    public void OpenCampionato(Activity context, int position) {
        Intent i = new Intent(context, ChampionshipActivity.class);
        i.putExtra("position", position);
        startActivity(i);
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
        return (Utili.setMenu(ChampionshipListActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }

}
