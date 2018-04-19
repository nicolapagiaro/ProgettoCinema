package com.grupppofigo.progettocinema.entities;

import java.io.Serializable;

public class Genere implements Serializable {
    private int id;
    private String nome;

    public Genere(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Genere() {
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

    @Override
    public String toString() {
        return "Genere{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
