package com.example.rigotti_project.Championship;

public class Impostazione {
    private String tipo;
    private String valore;

    public Impostazione(String tipo, String valore) {
        this.tipo = tipo;
        this.valore = valore;
    }

    //region GETTERS SETTERS

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    // endregion
}
