/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.ugovor;

import domen.Ugovor;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class KreirajUgovorSO extends ApstraktnaGenerickaOperacija {

    private Ugovor ugovor;

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema preduslova jer pravimo inicijalni ugovor
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        ugovor = (Ugovor) param;

        // Postavljamo minimalne validne podatke da baza ne odbije INSERT
        // (zaposleni i zakupac NOT NULL, pa koristimo one koji su poslati sa klijenta)
        ugovor.setUkupnaCena(0.0);
        
        // INSERT u bazu preko brokera
        int id = broker.add(ugovor);
        
        // Vraćamo dobijeni ID u objekat
        ugovor.setIdUgovor(id);
    }

    public Ugovor getUgovor() {
        return ugovor;
    }
}
