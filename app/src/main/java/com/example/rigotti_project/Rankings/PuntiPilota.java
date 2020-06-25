package com.example.rigotti_project.Rankings;


public class PuntiPilota implements Comparable {
    private String nome, team, auto;
    private Integer punti;

    public PuntiPilota(){
        this.nome = null;
        this.team = null;
        this.auto = null;
        this.punti = null;
    }

    public PuntiPilota(String nome, String team, String auto, Integer punti){
        this.nome = nome;
        this.team = team;
        this.auto = auto;
        this.punti = punti;
    }

    // region GETTER SETTER

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

    public Integer getPunti() {
        return punti;
    }

    public void setPunti(Integer punti) {
        this.punti = punti;
    }
    // endregion

    @Override
    public int compareTo(Object o) {
        PuntiPilota pp = (PuntiPilota) o;
        int punti = pp.getPunti();
        return punti-this.punti;
    }
}
