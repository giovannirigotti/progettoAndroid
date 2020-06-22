package com.example.rigotti_project.Championship;

public class Gara {
    private Integer seq;
    private String data;
    private String circuito;

    // region COSTRUTTORI

    public Gara(){
        this.seq = null;
        this.data = null;
        this.circuito = null;
    }

    public Gara(Integer seq, String data, String circuito) {
        this.seq = seq;
        this.data = data;
        this.circuito = circuito;
    }

    // endregion

    // region GETTERS SETTERS

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCircuito() {
        return circuito;
    }

    public void setCircuito(String circuito) {
        this.circuito = circuito;
    }

    // endregion
}
