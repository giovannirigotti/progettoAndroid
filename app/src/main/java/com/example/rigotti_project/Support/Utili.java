package com.example.rigotti_project.Support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Utili {

    public static final Integer NEVER_LOGGED = -1;
    public static final Integer WAS_LOGGED = 0;
    public static final Integer LOGGED = 1;

    // VARIABILE DI STATO:
    // -1 --> mai loggato
    //  0 --> appena effettuato logout
    //  1 --> loggato

    private static Integer STATUS = NEVER_LOGGED;

    public static ListaCampionati listaCampionati;

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

    public static String getNameLogo(String logo_png) {
        return logo_png.split("\\.")[0];
    }

    public static Integer getResId(Activity context, String nome_risorsa) {
        Log.println(Log.ERROR, "nome_risorsa", nome_risorsa);
        int resourceID = context.getResources().getIdentifier(nome_risorsa, "drawable", context.getPackageName());
        return resourceID;
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
}
