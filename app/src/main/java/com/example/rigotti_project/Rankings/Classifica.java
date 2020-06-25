package com.example.rigotti_project.Rankings;

import java.util.ArrayList;

public class Classifica {
    private Integer id;
    private String nome;
    private Integer logo;
    private ArrayList<PuntiPilota> classifica_piloti;
    private ArrayList<PuntiTeam> classifica_team;

    public Classifica() {
        this.id = null;
        this.nome = null;
        this.logo = null;
        this.classifica_piloti = null;
        this.classifica_team = null;
    }

    public Classifica(Integer id, String nome, Integer logo, ArrayList<PuntiPilota> classifica_piloti, ArrayList<PuntiTeam> classifica_team) {
        this.id = id;
        this.nome = nome;
        this.logo = logo;
        this.classifica_piloti = classifica_piloti;
        this.classifica_team = classifica_team;
    }

    // region GETTER SETTER
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

    public Integer getLogo() {
        return logo;
    }

    public void setLogo(Integer logo) {
        this.logo = logo;
    }

    public ArrayList<PuntiPilota> getClassifica_piloti() {
        return classifica_piloti;
    }

    public void setClassifica_piloti(ArrayList<PuntiPilota> classifica_piloti) {
        this.classifica_piloti = classifica_piloti;
    }

    public ArrayList<PuntiTeam> getClassifica_team() {
        return classifica_team;
    }

    public void setClassifica_team(ArrayList<PuntiTeam> classifica_team) {
        this.classifica_team = classifica_team;
    }
    // endregion
}
