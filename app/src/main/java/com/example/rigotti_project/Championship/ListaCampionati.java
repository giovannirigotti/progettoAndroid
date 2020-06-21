package com.example.rigotti_project.Championship;

import java.util.ArrayList;

public class ListaCampionati {

    private ArrayList<Campionato> campionati;

    // region COSTRUTTORI

    public ListaCampionati() {
        this.campionati = null;
    }

    public ListaCampionati(ArrayList<Campionato> campionati) {
        this.campionati = campionati;
    }

    // endregion

    // region GETTERS  SETTER

    public ArrayList<Campionato> getCampionati() {
        return campionati;
    }

    public void setCampionati(ArrayList<Campionato> campionati) {
        this.campionati = campionati;
    }

    public Campionato getCampionato(Integer position){
        Campionato c = new Campionato();
        c = campionati.get(position);
        return c;
    }

    // endregion
}
