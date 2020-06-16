package com.example.rigotti_project;

public class PersonalData {
    //dati utente;
    private static Integer ID;
    private static String NOME;
    private static String COGNOME;
    private static String EMAIL;
    private static String DATA;
    private static String NAZIONE;
    private static Integer NUMERO;
    private static String PREFERITO;
    private static String ODIATO;
    private static String AUTO;
    private static String FOTO;
    private static String PASSWORD;

    //metodi getter e setter

    public static Integer getID() {
        return ID;
    }

    public static void setID(Integer ID) {
        PersonalData.ID = ID;
    }

    public static String getNOME() {
        return NOME;
    }

    public static void setNOME(String NOME) {
        PersonalData.NOME = NOME;
    }

    public static String getCOGNOME() {
        return COGNOME;
    }

    public static void setCOGNOME(String COGNOME) {
        PersonalData.COGNOME = COGNOME;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static void setEMAIL(String EMAIL) {
        PersonalData.EMAIL = EMAIL;
    }

    public static String getDATA() {
        return DATA;
    }

    public static void setDATA(String DATA) {
        PersonalData.DATA = DATA;
    }

    public static String getNAZIONE() {
        return NAZIONE;
    }

    public static void setNAZIONE(String NAZIONE) {
        PersonalData.NAZIONE = NAZIONE;
    }

    public static Integer getNUMERO() {
        return NUMERO;
    }

    public static void setNUMERO(Integer NUMERO) {
        PersonalData.NUMERO = NUMERO;
    }

    public static String getPREFERITO() {
        return PREFERITO;
    }

    public static void setPREFERITO(String PREFERITO) {
        PersonalData.PREFERITO = PREFERITO;
    }

    public static String getODIATO() {
        return ODIATO;
    }

    public static void setODIATO(String ODIATO) {
        PersonalData.ODIATO = ODIATO;
    }

    public static String getAUTO() {
        return AUTO;
    }

    public static void setAUTO(String AUTO) {
        PersonalData.AUTO = AUTO;
    }

    public static String getFOTO() {
        return FOTO;
    }

    public static void setFOTO(String FOTO) {
        PersonalData.FOTO = FOTO;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        PersonalData.PASSWORD = PASSWORD;
    }

    public static void setAllData(Integer _id, String _nome, String _cognome, String _email, String _data, String _nazione,
                                  Integer _numero, String _preferito, String _odiato, String _auto, String _foto, String _password) {
        ID = _id;
        NOME = _nome;
        COGNOME = _cognome;
        EMAIL = _email;
        DATA = _data;
        NAZIONE = _nazione;
        NUMERO = _numero;
        PREFERITO = _preferito;
        ODIATO = _odiato;
        AUTO = _auto;
        FOTO = _foto;
        PASSWORD = _password;
    }

    public static void clearUserData() {
        ID = null;
        NOME = null;
        COGNOME = null;
        EMAIL = null;
        DATA = null;
        NAZIONE = null;
        NUMERO = null;
        PREFERITO = null;
        ODIATO = null;
        AUTO = null;
        FOTO = null;
        PASSWORD = null;
    }
}
