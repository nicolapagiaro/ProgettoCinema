package com.grupppofigo.progettocinema.entities;

public class PostoPrenotato {
    private int id;
    private int idPrenotazione;
    private int numeroPosto;


    /**
     * Costruttore parametrico
     * @param id
     * @param idPrenotazione
     * @param numeroPosto
     */
    public PostoPrenotato(int id, int idPrenotazione, int numeroPosto) {
        this.id = id;
        this.idPrenotazione = idPrenotazione;
        this.numeroPosto = numeroPosto;
    }

    /**
     * Costruttore vuoto
     */
    public PostoPrenotato() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(int idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public int getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(int numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    /**
     * toString()
     * @return
     */
    @Override
    public String toString() {
        return "PostoPrenotato{" +
                "id=" + id +
                ", idPrenotazione=" + idPrenotazione +
                ", numeroPosto=" + numeroPosto +
                '}';
    }
}
