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

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.CustomChampionshipListView;
import com.example.rigotti_project.Support.Utili;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChampionshipListActivity extends AppCompatActivity {

    private String json;

    private ListView lv;

    private ArrayList<Integer> champID;

    private ArrayList<Integer> imgID;

    private ArrayList<String> listaCampionati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship_list);
        setTitle("Lista campionati");

        json = Utili.getCampionati(this);

        ReadData();

        CustomChampionshipListView custom = new CustomChampionshipListView(ChampionshipListActivity.this, listaCampionati, imgID);
        lv.setAdapter(custom);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenCampionato(ChampionshipListActivity.this,position);
            }
        });


    }

    public void ReadData() {
        if (!json.equals("error")) {
            listaCampionati = new ArrayList<>();
            imgID = new ArrayList<>();
            champID = new ArrayList<>();
            lv = (ListView) findViewById(R.id.lista_campionati);
            try {
                final JSONObject jsonObj = new JSONObject(json);

                final JSONArray campionati = jsonObj.getJSONArray("campionati");


                for (int i = 0; i < campionati.length(); i++) {
                    JSONObject c = campionati.getJSONObject(i);


                    Integer id = c.getInt("id");
                    String nome = c.getString("nome");
                    String logo_png = c.getString("logo");

                    String logo = Utili.getNameLogo(logo_png);
                    Log.e("LOGO", logo);

                    Integer id_immagine = Utili.getResId(this, logo);
                    Log.e("ID IMMAGINE", id_immagine.toString());

                    champID.add(id);
                    listaCampionati.add(nome);
                    imgID.add(id_immagine);
                }

            } catch (final JSONException e) {
                e.printStackTrace();
                Utili.doToast(this, "Errore nella lettura dei dati");
            }

        }
    }

    public void OpenCampionato(Activity context, int position){
        Intent i = new Intent(context, ChampionshipActivity.class);
        i.putExtra("id_campionato", champID.get(position));
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
