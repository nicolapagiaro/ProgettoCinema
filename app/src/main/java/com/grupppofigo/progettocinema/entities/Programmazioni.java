package com.grupppofigo.progettocinema.entities;

import java.util.ArrayList;

/**
 * Classe che rappresenta tutte le programmazioni per un film
 * per mostrarle nell'activity della prenotazione
 */
public class Programmazioni {
    private ArrayList<Giorno> giorni;

    public Programmazioni() {
        giorni = new ArrayList<>();
    }

    public ArrayList<Giorno> getGiorni() {
        return giorni;
    }

    public void setGiorni(ArrayList<Giorno> giorni) {
        this.giorni = giorni;
    }

    @Override
    public String toString() {
        return "Programmazioni{" +
                "giorni=" + giorni.toString() +
                '}';
    }
}
