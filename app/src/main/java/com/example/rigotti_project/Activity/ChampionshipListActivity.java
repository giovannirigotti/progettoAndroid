package com.example.rigotti_project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.rigotti_project.R;
import com.example.rigotti_project.Support.CustomChampionshipListView;
import com.example.rigotti_project.Support.Utili;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChampionshipListActivity extends AppCompatActivity {

    private String json;

    private ListView lv;

    private ArrayList<String> imgID;

    private ArrayList<String> listaCampionati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship_list);
        setTitle("Lista campionati");

        json = Utili.getCampionati(this);

        if (!json.equals("error")) {
            listaCampionati = new ArrayList<>();
            imgID = new ArrayList<>();
            lv = (ListView) findViewById(R.id.lista_campionati);
            new GetContacts().execute();
        }

    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utili.doToast(ChampionshipListActivity.this, "Json Data is downloading");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            if (!json.equals("error")) {
                try {
                    final JSONObject jsonObj = new JSONObject(json);

                    final JSONArray campionati = jsonObj.getJSONArray("campionati");


                    for (int i = 0; i < campionati.length(); i++) {
                        JSONObject c = campionati.getJSONObject(i);


                        String id = c.getString("id");
                        final String nome = c.getString("nome");
                        final String logo = c.getString("logo");


                        listaCampionati.add(nome);
                        imgID.add(logo);
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utili.doToast(getApplicationContext(), "Json parsing error: " + e.getMessage());
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utili.doToast(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!");
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            CustomChampionshipListView custom = new CustomChampionshipListView(ChampionshipListActivity.this, listaCampionati, imgID);
            lv.setAdapter(custom);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Ritorno True se il quando premo su un Item questo è nello switch case
        // Ritrono "super.onOptionsItemSelected(item)" altrimenti
        return (Utili.setMenu(ChampionshipListActivity.this, item)) ? true : super.onOptionsItemSelected(item);
    }
}
