package com.grupppofigo.progettocinema.entities;

/**
 * Classe che descrive il film
 * Created by Nicola on 09/04/2018.
 */
public class Film {
    private int id;
    private String titolo;
    private int durata;
    private Genere genere;
    private String descrizione;
    private int voto;
    private String immagine;

    public Film(int id, String titolo, int durata, Genere genere, String descrizione, int voto, String immagine) {
        this.id = id;
        this.titolo = titolo;
        this.durata = durata;
        this.genere = genere;
        this.descrizione = descrizione;
        this.voto = voto;
        this.immagine = immagine;
    }

    /**
     * Costruttore vuoto
     */
    public Film() {
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
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

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
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
