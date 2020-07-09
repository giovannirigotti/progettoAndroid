package com.example.rigotti_project.Support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rigotti_project.Championship.Campionato;
import com.example.rigotti_project.Championship.Impostazione;
import com.example.rigotti_project.Championship.ListaCampionati;
import com.example.rigotti_project.Championship.Pilota;

import java.util.ArrayList;

// ---------------------------------
// ---------------------------------
// Classe di supporto per la gestione e la comunicazione con il DB SQLite
// ---------------------------------
// ---------------------------------

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "register_db";
    public static final String TABLE_USER = "user";
    public static final String C_ID = "id";
    public static final String C_NOME = "nome";
    public static final String C_COGNOME = "cognome";
    public static final String C_EMAIL = "email";
    public static final String C_DATA = "data";
    public static final String C_NAZIONE = "nazione";
    public static final String C_NUMERO = "numero";
    public static final String C_CIRCUITO_P = "circuito_preferito";
    public static final String C_CIRCUITO_O = "circuito_odiato";
    public static final String C_AUTO = "auto";
    public static final String C_FOTO = "foto";
    public static final String C_PASSWORD = "password";

    public static final String TABLE_INSCRIPTIONS = "inscriptions";
    public static final String C_ID_ENROLL = "id";
    public static final String C_ID_UTENTE = "user_id";
    public static final String C_ID_CAMPIONATO = "champ_id";
    public static final String C_NOME_PILOTA = "nome";
    public static final String C_TEAM = "team";

    public static final String TABLE_RULES = "rules";
    public static final String C_ID_CAMP = C_ID_CAMPIONATO;
    public static final String C_TIPO = "tipo";
    public static final String C_VALORE = "valore";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DB", "CREO ISCRIZIONI");
        String queryIscrizioni = "CREATE TABLE inscriptions (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER, champ_id INTEGER, nome TEXT, auto TEXT, team TEXT)";
        db.execSQL(queryIscrizioni);
        Log.e("DB", "CREO USER");
        String queryUser = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT, cognome TEXT, email TEXT UNIQUE, data TEXT," +
                "nazione TEXT, numero INTEGER, circuito_preferito TEXT," +
                "circuito_odiato TEXT, auto TEXT, foto TEXT, password TEXT)";
        db.execSQL(queryUser);
        Log.e("DB", "CREO IMPOSTAZIONI");
        String queryImpo = "CREATE TABLE rules (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "champ_id INTEGER, tipo TEXT, valore TEXT)";
        db.execSQL(queryImpo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSCRIPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RULES);
        onCreate(db);
    }

    // region GESTIONE TABELLA UTENTI

    public long addUser(String nome, String cognome, String email, String data, String nazione, Integer numero, String preferito, String odiato, String auto, String foto, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NOME, nome);
        cv.put(C_COGNOME, cognome);
        cv.put(C_EMAIL, email);
        cv.put(C_DATA, data);
        cv.put(C_NAZIONE, nazione);
        cv.put(C_NUMERO, numero);
        cv.put(C_CIRCUITO_P, preferito);
        cv.put(C_CIRCUITO_O, odiato);
        cv.put(C_AUTO, auto);
        cv.put(C_FOTO, foto);
        cv.put(C_PASSWORD, password);
        long res = db.insert(TABLE_USER, null, cv);
        db.close();
        return res;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    public boolean checkUserPassword(String email, String password) {
        String[] columns = {C_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = C_EMAIL + "=?" + " and " + C_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor c = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        int count = c.getCount();
        c.close();

        return (count > 0) ? true : false;
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE user.email = " + email;
        String[] columns = {C_ID, C_NOME, C_COGNOME, C_EMAIL, C_DATA, C_NAZIONE, C_NUMERO, C_CIRCUITO_P, C_CIRCUITO_O, C_AUTO, C_FOTO, C_PASSWORD};
        String selection = C_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public void updateImage(String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_FOTO, image);
        db.update(TABLE_USER, cv, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    public void updatePassword(String nuova_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_PASSWORD, nuova_password);
        db.update(TABLE_USER, cv, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    public void updateDatiGioco(Integer numero, String preferito, String odiato, String auto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NUMERO, numero);
        cv.put(C_CIRCUITO_P, preferito);
        cv.put(C_CIRCUITO_O, odiato);
        cv.put(C_AUTO, auto);
        db.update(TABLE_USER, cv, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    // endregion

    // region GETIONE TABELLA ISCRITTI

    public long addEntry(Integer id_campionato, String auto, String team) {
        String my_name = PersonalData.getNOME() + " " + PersonalData.getCOGNOME();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_ID_UTENTE, PersonalData.getID());
        cv.put(C_ID_CAMPIONATO, id_campionato);
        cv.put(C_NOME_PILOTA, my_name);
        cv.put(C_AUTO, auto);
        cv.put(C_TEAM, team);
        long res = db.insert(TABLE_INSCRIPTIONS, null, cv);
        db.close();
        Pilota p = new Pilota(my_name, team, auto);
        Utili.listaCampionati.getCampionato(id_campionato).addPilota(p);
        return res;
    }

    public void updatePiloti() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_INSCRIPTIONS;
        String[] columns = {C_ID_ENROLL, C_ID_UTENTE, C_ID_CAMPIONATO, C_NOME_PILOTA, C_AUTO, C_TEAM};
        Cursor cursor = db.query(TABLE_INSCRIPTIONS, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer id_campionato = cursor.getInt(2);
                String nome = cursor.getString(3);
                String auto = cursor.getString(4);
                String team = cursor.getString(5);

                Pilota p = new Pilota(nome, team, auto);
                Utili.listaCampionati.getCampionato(id_campionato).addPilota(p);
                cursor.moveToNext();
            }
        }
        Log.e("DB", "Pilots updated");
        cursor.close();
        db.close();

    }

    public void deleteEntry(Integer id_campionato) {
        //GESTISCO DB
        SQLiteDatabase db = this.getWritableDatabase();
        String clause = C_ID_CAMPIONATO + "=?" + " and " + C_ID_UTENTE + "=?";
        String[] selectionArgs = {id_campionato.toString(), PersonalData.getID().toString()};
        db.delete(TABLE_INSCRIPTIONS, clause, selectionArgs);
        db.close();

        //GESTISCO DATI LOCALI
        ListaCampionati lista = Utili.listaCampionati;
        Campionato c = lista.getCampionato(id_campionato);
        ArrayList<Pilota> piloti = new ArrayList<>();
        String nome_pilota = PersonalData.getNOME() + " " + PersonalData.getCOGNOME();
        for (int i = 0; i < c.getPiloti().size(); i++) {
            Pilota p = c.getPilota(i);
            if (!p.getNome().equals(nome_pilota)) {
                piloti.add(p);
            }
        }
        Utili.listaCampionati.getCampionato(id_campionato).setPiloti(piloti);
    }

    // endregion

    // region GETIONE TABELLA RULES

    public long changeRule(Integer id_campionato, String tipo, String valore, Integer position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_ID_CAMP, id_campionato);
        cv.put(C_TIPO, tipo);
        cv.put(C_VALORE, valore);
        long res = db.insert(TABLE_RULES, null, cv);
        db.close();
        // Modifico valore nella lista.
        Utili.listaCampionati.getCampionato(id_campionato).getImpostazioni().get(position).setValore(valore);
        return res;
    }

    public void updateRules() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RULES;
        String[] columns = {C_ID_CAMP, C_TIPO, C_VALORE};
        Cursor cursor = db.query(TABLE_RULES, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer id_campionato = cursor.getInt(0);
                String tipo = cursor.getString(1);
                String valore = cursor.getString(2);

                Impostazione impostazione = new Impostazione(tipo, valore);
                Utili.listaCampionati.getCampionato(id_campionato).updateImpostazione(impostazione);
                cursor.moveToNext();
            }
        }
        Log.e("DB", "Impostazioni updated");
        cursor.close();
        db.close();

    }

    public boolean isRulesEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RULES;
        String[] columns = {C_ID_CAMP, C_TIPO, C_VALORE};
        Cursor cursor = db.query(TABLE_RULES, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }

    }

    // endregion
}


