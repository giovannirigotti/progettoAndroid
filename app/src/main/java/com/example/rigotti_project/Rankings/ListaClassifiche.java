package com.example.rigotti_project.Rankings;

import com.example.rigotti_project.Championship.ListaCampionati;

import java.util.ArrayList;

public class ListaClassifiche {
    private ArrayList<Classifica> lista_classifiche;

    public ListaClassifiche(){
        this.lista_classifiche = new ArrayList<>();
    }
    public ListaClassifiche(ArrayList<Classifica> lista_classifiche){
        this.lista_classifiche = lista_classifiche;
    }

    // region GETTER SETTER
    public ArrayList<Classifica> getLista_classifiche() {
        return lista_classifiche;
    }

    public void setLista_classifiche(ArrayList<Classifica> lista_classifiche) {
        this.lista_classifiche = lista_classifiche;
    }
    // endregion
}
