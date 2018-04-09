package com.grupppofigo.progettocinema.entities;

/**
 * Classe che rappresenta la progrmmazione
 * Created by Nicola on 09/04/2018.
 */
public class Programmazione {
    private int id;
    private int idFilm;
    private int idSala;
    private String data;
    private String ora;

    /**
     * Costruttore parametrico
     * @param id
     * @param idFilm
     * @param idSala
     * @param data
     * @param ora
     */
    public Programmazione(int id, int idFilm, int idSala, String data, String ora) {
        this.id = id;
        this.idFilm = idFilm;
        this.idSala = idSala;
        this.data = data;
        this.ora = ora;
    }

    /**
     * Costruttore vuoto
     */
    public Programmazione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    /**
     * toString()
     * @return
     */
    @Override
    public String toString() {
        return "Programmazione{" +
                "id=" + id +
                ", idFilm=" + idFilm +
                ", idSala=" + idSala +
                ", data='" + data + '\'' +
                ", ora='" + ora + '\'' +
                '}';
    }
}
