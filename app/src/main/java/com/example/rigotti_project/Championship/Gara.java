package com.example.rigotti_project.Championship;

public class Gara {
    private String seq;
    private String data;
    private String circuito;

    public Gara(String seq, String data, String circuito) {
        this.seq = seq;
        this.data = data;
        this.circuito = circuito;
    }

    // region GETTERS SETTERS

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
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
