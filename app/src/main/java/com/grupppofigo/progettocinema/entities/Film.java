package com.grupppofigo.progettocinema.entities;

/**
 * Classe che descrive il film
 * Created by Nicola on 09/04/2018.
 */
public class Film {
    private int id;
    private String titolo;
    private int durata;
    private String genere;
    private String descrizione;

    /**
     * Costruttore parametrico
     * @param id
     * @param titolo
     * @param durata
     * @param genere
     * @param descrizione
     */
    public Film(int id, String titolo, int durata, String genere, String descrizione) {
        this.id = id;
        this.titolo = titolo;
        this.durata = durata;
        this.genere = genere;
        this.descrizione = descrizione;
    }

    /**
     * Costruttore vuoto
     */
    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * toString()
     * @return
     */
    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", durata=" + durata +
                ", genere='" + genere + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
