package com.grupppofigo.progettocinema.entities;

/**
 * Classe che rappresenta la sala
 * Created by Nicola on 09/04/2018.
 */
public class Sala {
    private int id;
    private String nome;
    private int nPosti;

    /**
     * Costruttore parametrico
     * @param id
     * @param nome
     * @param nPosti
     */
    public Sala(int id, String nome, int nPosti) {
        this.id = id;
        this.nome = nome;
        this.nPosti = nPosti;
    }

    /**
     * Costruttore vuoto
     */
    public Sala() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getnPosti() {
        return nPosti;
    }

    public void setnPosti(int nPosti) {
        this.nPosti = nPosti;
    }

    /**
     * toString()
     * @return
     */
    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nPosti=" + nPosti +
                '}';
    }
}
