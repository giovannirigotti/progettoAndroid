package com.example.rigotti_project.Support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private String queryCreate;
    public static final String DATABASE_NAME = "register_db";
    public static final String TABLE_NAME = "user";
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


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        queryCreate = "CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT, cognome TEXT, email TEXT UNIQUE, data TEXT," +
                "nazione TEXT, numero INTEGER, circuito_preferito TEXT," +
                "circuito_odiato TEXT, auto TEXT, foto TEXT, password TEXT)";
        db.execSQL(queryCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

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
        long res = db.insert(TABLE_NAME, null, cv);
        db.close();
        return res;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    public boolean checkUserPassword(String email, String password) {
        String[] columns = {C_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = C_EMAIL + "=?" + " and " + C_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = c.getCount();
        c.close();

        return (count > 0) ? true : false;
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user.email = " + email;
        String[] columns = {C_ID, C_NOME, C_COGNOME, C_EMAIL, C_DATA, C_NAZIONE, C_NUMERO, C_CIRCUITO_P, C_CIRCUITO_O, C_AUTO, C_FOTO, C_PASSWORD};
        String selection = C_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public void updateImage(String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_FOTO, image);
        db.update(TABLE_NAME, cv, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    public void updatePassword(String nuova_password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_PASSWORD, nuova_password);
        db.update(TABLE_NAME, cv, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }

    public void updateDatiGioco(Integer numero, String preferito, String odiato, String auto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NUMERO, numero);
        cv.put(C_CIRCUITO_P, preferito);
        cv.put(C_CIRCUITO_O, odiato);
        cv.put(C_AUTO, auto);
        db.update(TABLE_NAME, cv, C_ID + "= ?", new String[]{PersonalData.getID().toString()});
        db.close();
    }
}


