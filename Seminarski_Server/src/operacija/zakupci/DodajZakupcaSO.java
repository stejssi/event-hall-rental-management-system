/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.zakupci;

import domen.Zakupac;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class DodajZakupcaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception{
        if (param == null || !(param instanceof Zakupac)) {
        throw new Exception("Sistem ne moze da kreira zakupca");
    }

    Zakupac z = (Zakupac) param;

    if (z.getNazivZakupac() == null || z.getNazivZakupac().isEmpty()) {
        throw new Exception("GRESKA NAZIV ZAKUPCA");
    }

    if (z.getEmailZakupca() == null || !z.getEmailZakupca().contains("@")) {
        throw new Exception("GRESKA EMAIL");
    }

    if (z.getTelefon() == null || z.getTelefon().isEmpty()) {
        throw new Exception("GRESKA TELEFON");
    }

    if (z.getAdresa() == null || z.getAdresa().isEmpty()) {
        throw new Exception("GRESKA ADRESA");
    }

    if (z.getKategorijaZakupca() == null ||
        z.getKategorijaZakupca().getIdKategorijaZakupca() <= 0) {
        throw new Exception("GRESKA KATEGORIJA ZAKUPCA");
    }
        
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.add((Zakupac)param);
    }
    
}
