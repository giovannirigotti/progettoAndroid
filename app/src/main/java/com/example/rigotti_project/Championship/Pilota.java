package com.example.rigotti_project.Championship;

public class Pilota {
    private String nome;
    private String team;
    private String auto;

    public Pilota(String nome, String team, String auto) {
        this.nome = nome;
        this.team = team;
        this.auto = auto;
    }

    //region GETTERS SETTERS

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String auto) {
        this.auto = auto;
    }

    // endregion
}
