package com.grupppofigo.progettocinema.entities;

public class Prenotazione {
    private int id;
    private int idProgrammazione;
    private int idUtente;

    public Prenotazione(int id, int idProgrammazione, int idUtente) {
        this.id = id;
        this.idProgrammazione = idProgrammazione;
        this.idUtente = idUtente;
    }

    public Prenotazione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProgrammazione() {
        return idProgrammazione;
    }

    public void setIdProgrammazione(int idProgrammazione) {
        this.idProgrammazione = idProgrammazione;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", idProgrammazione=" + idProgrammazione +
                ", idUtente=" + idUtente +
                '}';
    }
}
