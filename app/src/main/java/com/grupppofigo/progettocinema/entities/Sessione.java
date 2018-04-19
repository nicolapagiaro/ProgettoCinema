package com.grupppofigo.progettocinema.entities;

import java.io.Serializable;

public class Sessione implements Serializable {
    private int id;
    private String startSessione;
    private String endSession;

    public Sessione(int id, String startSessione, String endSession) {
        this.id = id;
        this.startSessione = startSessione;
        this.endSession = endSession;
    }

    public Sessione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartSessione() {
        return startSessione;
    }

    public void setStartSessione(String startSessione) {
        this.startSessione = startSessione;
    }

    public String getEndSession() {
        return endSession;
    }

    public void setEndSession(String endSession) {
        this.endSession = endSession;
    }

    @Override
    public String toString() {
        return "Sessione{" +
                "id=" + id +
                ", startSessione='" + startSessione + '\'' +
                ", endSession='" + endSession + '\'' +
                '}';
    }
}
