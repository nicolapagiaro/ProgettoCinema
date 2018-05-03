package com.grupppofigo.progettocinema.entities;

import java.util.ArrayList;

public class Giorno {
    private String data;
    private ArrayList<String> orari;

    public Giorno() {
        orari = new ArrayList<>();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<String> getOrari() {
        return orari;
    }

    public void setOrari(ArrayList<String> orari) {
        this.orari = orari;
    }

    @Override
    public String toString() {
        return "Giorno{" +
                "data='" + data + '\'' +
                ", orari=" + orari +
                '}';
    }
}