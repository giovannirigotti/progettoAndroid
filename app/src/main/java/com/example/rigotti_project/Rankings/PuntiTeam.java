package com.example.rigotti_project.Rankings;

public class PuntiTeam implements Comparable{
    private String team, auto;
    private Integer punti;

    public PuntiTeam(){
        this.team = null;
        this.auto = null;
        this.punti = null;
    }

    public PuntiTeam(String team, String auto, Integer punti){
        this.team = team;
        this.auto = auto;
        this.punti = punti;
    }

    // region GETTER SETTER

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
        PuntiTeam pp = (PuntiTeam) o;
        int punti = pp.getPunti();
        return punti-this.punti;
    }

}
