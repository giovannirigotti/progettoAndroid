package com.example.rigotti_project.Championship;

import java.util.ArrayList;


public class Campionato {

    private Integer id;
    private String nome;
    private Integer id_logo;
    private ArrayList<Gara> calendario;
    private ArrayList<Impostazione> impostazioni;
    private String auto;
    private ArrayList<Pilota> piloti;

    //region COSTRUTTORI

    public Campionato() {
        this.id = null;
        this.nome = null;
        this.id_logo = null;
        this.calendario = null;
        this.impostazioni = null;
        this.auto = null;
        this.piloti = null;
    }

    public Campionato(Integer id, String nome, Integer id_logo, ArrayList<Gara> calendario, ArrayList<Impostazione> impostazioni, String auto, ArrayList<Pilota> piloti) {
        this.id = id;
        this.nome = nome;
        this.id_logo = id_logo;
        this.calendario = calendario;
        this.impostazioni = impostazioni;
        this.auto = auto;
        this.piloti = piloti;
    }

    //endregion

    //region GETTERS SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId_logo() {
        return id_logo;
    }

    public void setId_logo(Integer id_logo) {
        this.id_logo = id_logo;
    }

    public ArrayList<Gara> getCalendario() {
        return calendario;
    }

    public void setCalendario(ArrayList<Gara> calendario) {
        this.calendario = calendario;
    }

    public ArrayList<Impostazione> getImpostazioni() {
        return impostazioni;
    }

    public void setImpostazioni(ArrayList<Impostazione> impostazioni) {
        this.impostazioni = impostazioni;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    public ArrayList<Pilota> getPiloti() {
        return piloti;
    }

    public void setPiloti(ArrayList<Pilota> piloti) {
        this.piloti = piloti;
    }

    // endregion

    // region METODI UTILI

    public Gara getGara(Integer position) {
        Gara gara = calendario.get(position);
        return gara;
    }

    public Pilota getPilota(Integer position) {
        Pilota p = piloti.get(position);
        return p;
    }

    public void updatePiloti(ArrayList<Pilota> nuovi_piloti){
        piloti = nuovi_piloti;
    }

    public void addPilota(Pilota nuovo_pilota){
        piloti.add(nuovo_pilota);
    }

    //endregion

}



