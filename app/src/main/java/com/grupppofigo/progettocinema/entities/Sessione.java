package com.grupppofigo.progettocinema.entities;

import java.io.Serializable;

public class Sessione implements Serializable {
    private int id;
    private String startSessione;
    private String endSession;
    private int idUtente;

    public Sessione(int id, String startSessione, String endSession, int idUtente) {
        this.id = id;
        this.startSessione = startSessione;
        this.endSession = endSession;
        this.idUtente = idUtente;
    }

    public Sessione() {
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
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
