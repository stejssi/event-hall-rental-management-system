/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.zakupci;

import domen.ApstraktniDomenskiObjekat;
import domen.KategorijaZakupca;
import domen.Zakupac;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class KreirajZakupcaSO extends ApstraktnaGenerickaOperacija {

private Zakupac zakupac;


    @Override
    protected void preduslovi(Object param) throws Exception {
        // nema provere jer pravimo prazan objekat
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
    zakupac = (Zakupac) param;
        
        // Postavljamo inicijalne vrednosti koje baza dozvoljava (prazni stringovi)
        zakupac.setNazivZakupac(""); 
        zakupac.setEmailZakupca("");
        zakupac.setTelefon("");
        zakupac.setAdresa("");
        
        // stavljamo ID koji postoji u tabeli 'kategorija' jer NOT NULL na stranom ključu zahteva validnu vezu
        KategorijaZakupca k = new KategorijaZakupca();
        k.setIdKategorijaZakupca(1); 
        zakupac.setKategorijaZakupca(k);

        // BROKER upisuje u bazu i vraća generisan ID
        int id = broker.add(zakupac);
        zakupac.setIdZakupac(id);
    }

    public Zakupac getZakupac() {
        return zakupac;
    }
}
