package com.example.rigotti_project.Support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rigotti_project.Activity.AccounActivity;
import com.example.rigotti_project.Activity.ChampionshipListActivity;
import com.example.rigotti_project.Activity.EditDataActivity;
import com.example.rigotti_project.Activity.HomeActivity;
import com.example.rigotti_project.Activity.LoginActivity;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.Gara;
import com.example.rigotti_project.Championship.Impostazione;
import com.example.rigotti_project.Championship.ListaCampionati;
import com.example.rigotti_project.Championship.Pilota;
import com.example.rigotti_project.R;
import com.example.rigotti_project.Rankings.Classifica;
import com.example.rigotti_project.Rankings.ListaClassifiche;
import com.example.rigotti_project.Rankings.PuntiPilota;
import com.example.rigotti_project.Rankings.PuntiTeam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class Utili {

    public static final Integer NEVER_LOGGED = -1;
    public static final Integer WAS_LOGGED = 0;
    public static final Integer LOGGED = 1;

    // VARIABILE DI STATO:
    // -1 --> mai loggato
    //  0 --> appena effettuato logout
    //  1 --> loggato

    public static final Integer NUMERO_FOTO = 30;

    public static Integer MODIFICA = 0; //1 se modifico dati
    public static String TIPO = null; //
    public static String CAMPIONATO = null; //
    public static String VALORE = null; //
    public static Integer CAMP = null;

    private static Integer STATUS = NEVER_LOGGED;

    public static ListaCampionati listaCampionati = null;
    public static ListaClassifiche listaClassifiche = null;

    // get e set STATUS
    public static Integer getSTATUS() {
        return STATUS;
    }

    public static void setSTATUS(Integer STATUS) {
        Utili.STATUS = STATUS;
    }

    // METODI PER CONTROLLARE I DATI INSERITI DALL'UTENTE

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean checkBirthDate(String data) {
        boolean checkFormat;
        boolean checkNumber;

        if (data.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
            checkFormat = true;
        else
            checkFormat = false;
        try {
            String[] t = data.split("/");
            checkNumber = (Integer.valueOf(t[0]) <= 31 && Integer.valueOf(t[1]) <= 12 && Integer.valueOf(t[2]) >= 1900 && Integer.valueOf(t[2]) <= 2100);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return (checkNumber && checkFormat);
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    public static int validateNumero(String numero) {
        //controllo che la stringa inserita dell'utente sia effettivamente un numero
        //e controllo che sia compreso tra 0 e 999
        int n = -1;
        try {
            n = Integer.parseInt(numero);
            if (n < 0 || n > 999) {
                return -2;
            } else {
                return n;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean validateSimpleText(String text, int maxLenght) {
        // Se il testo inserito dall'utente è composto solo da caratteri
        // alfabetici ed è più corto di "maxLenght" allora ok altrimento no.
        String regex = "^[a-zA-Z_ ]*$";
        return (text.length() <= maxLenght && text.matches(regex)) ? true : false;
    }

    public static boolean validateSimpleCommaText(String text, int maxLenght) {
        // Se il testo inserito dall'utente è composto solo da caratteri
        // alfabetici ed è più corto di "maxLenght" allora ok altrimento no.
        String regex = "^[a-zA-Z,_ ]*$";
        return (text.length() <= maxLenght && text.matches(regex)) ? true : false;
    }


    // METODI PER FACILITARE LA VITA AL PROGRAMMATORE

    public static void doToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


    // LOGOUT

    public static void doLogout(Activity activity) {
        Utili.setSTATUS(Utili.WAS_LOGGED);
        PersonalData.clearUserData();
        Intent i = new Intent(activity, LoginActivity.class);
        ((Activity) activity).startActivity(i);
    }

    // region MENU

    public static boolean setMenu(Activity activity, MenuItem item) {
        Integer id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.item_account:
                //vado alla activity account;
                intent = new Intent(activity, AccounActivity.class);
                activity.startActivity(intent);
                return true;
            case R.id.item_home:
            case R.id.item_home_2:
                //vado alla activity home;
                intent = new Intent(activity, HomeActivity.class);
                activity.startActivity(intent);
                return true;
            case R.id.item_campionati:
                //vado alla activity campionati;
                intent = new Intent(activity, ChampionshipListActivity.class);
                activity.startActivity(intent);
                return true;
            case R.id.item_modifica_dati:
                //vado alla activity modifica_dati;
                intent = new Intent(activity, EditDataActivity.class);
                activity.startActivity(intent);
                return true;
            case R.id.item_logout:
                //eseguo logout: cancello dati, setto stato su "WAS_LOGGED" e vado alla activity login;
                Utili.doLogout(activity);
                return true;
        }
        return false;
    }

    // endregion

    //JSON HELPER
    public static String getCampionati(Activity activity) {
        String res = "";
        InputStream is = activity.getResources().openRawResource(R.raw.campionati);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Utili.doToast(activity, "Impossibile caricare dati.");
            return "error";
        }

        res = writer.toString();
        return res;
    }

    public static void readCampionati(Activity activity) {
        if (listaCampionati == null) {
            GetCampionati getCampionati = new GetCampionati(activity);
            getCampionati.execute();
        } else {
            Log.wtf("DATA", "Campionati already charged");
        }
    }

    public static String getClassifiche(Activity activity) {
        String res = "";
        InputStream is = activity.getResources().openRawResource(R.raw.classifiche);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Utili.doToast(activity, "Impossibile caricare dati.");
            return "error";
        }

        res = writer.toString();
        return res;
    }

    public static void readClassifiche(Activity activity) {
        if (listaClassifiche == null) {
            GetClassifiche getClassifiche = new GetClassifiche(activity);
            getClassifiche.execute();
        } else {
            Log.wtf("DATA", "Classifiche already charged");
        }
    }

    public static String getNameLogo(String logo_png) {
        return logo_png.split("\\.")[0];
    }

    public static Integer getResId(Activity context, String nome_risorsa) {
        int resourceID = context.getResources().getIdentifier(nome_risorsa, "drawable", context.getPackageName());
        return resourceID;
    }

    public static void shareImage(Activity context, Integer resource_id) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), resource_id);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), b, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        context.startActivity(Intent.createChooser(share, "Select"));
    }

    public static Integer isMember(Integer id_campionato) {
        ArrayList<Pilota> membri = listaCampionati.getCampionato(id_campionato).getPiloti();
        String my_name = PersonalData.getNOME() + " " + PersonalData.getCOGNOME();
        Integer res = -1;
        for (int i = 0; i < membri.size(); i++) {
            Pilota p = membri.get(i);
            String pilots_name = p.getNome();
            if (pilots_name.equals(my_name)) {
                res = i;
            }
        }
        return res;
    }

    // Caricp tutti i dati contenuti nel json in background
    public static class GetCampionati extends AsyncTask<Void, Void, Void> {

        private Activity context;

        public GetCampionati(Activity c) {
            super();
            this.context = c;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("JSON", "campionati.json is downloading");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            DatabaseHelper db = new DatabaseHelper(context);
            String json = getCampionati(context);
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
                        Integer id_logo = Utili.getResId(context, logo);
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
                            Gara gara = new Gara(seq, data, circuito);

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
                            Impostazione impostazione = new Impostazione(tipo, valore);

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
                            Pilota pilota = new Pilota(p_nome, p_team, p_auto);

                            //INSERISCO PILOTA NELLA LISTA DEI PILOTI ISCRITTI
                            piloti.add(pilota);
                        }

                        // Creo nuovo campionato
                        Campionato campionato = new Campionato(id, nome, id_logo, calendario, impostazioni, auto, piloti);

                        //Aggiungo campionato alla lista
                        lista_campionati.add(campionato);
                        Utili.listaCampionati = new ListaCampionati(lista_campionati);

                    }

                    //SALVO LA LISTA DEI CAMPIONATI
                    campionati.setCampionati(lista_campionati);

                } catch (final JSONException e) {
                    e.printStackTrace();
                    Utili.doToast(context, "Errore caricamento dati");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            DatabaseHelper db = new DatabaseHelper(context);
            db.updatePiloti();
            if(!db.isRulesEmpty()){
                db.updateRules();
            }
            Log.e("JSON", "Campionati caricati");
        }
    }

    public static class GetClassifiche extends AsyncTask<Void, Void, Void> {

        private Activity context;

        public GetClassifiche(Activity c) {
            super();
            this.context = c;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("JSON", "classifiche.json is downloading");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String json = getClassifiche(context);
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
                        Integer id_logo = Utili.getResId(context, logo);
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
                    Utili.listaClassifiche = new ListaClassifiche(classifiche);

                } catch (final JSONException e) {
                    e.printStackTrace();
                    Utili.doToast(context, "Errore nella lettura dei dati");
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.e("JSON", "Classifiche caricate");
        }


    }
}


