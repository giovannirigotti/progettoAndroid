package com.example.rigotti_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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
        String regex = "^[A-Za-z]+$";
        return (text.length() <= maxLenght && text.matches(regex)) ? true : false;
    }



    // METODI PER FACILITARE LA VITA AL PROGRAMMATORE

    public static void doToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }



    // METODI PER CONVERTIRE IMMAGINI Bitmap in String e viceversa

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    // LOGOUT

    public static Intent doLogout (Activity activity){
        Utili.setSTATUS(Utili.WAS_LOGGED);
        PersonalData.clearUserData();
        Intent i = new Intent(activity, LoginActivity.class);
        return i;
    }

}
