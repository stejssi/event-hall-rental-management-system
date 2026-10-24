/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.zakupci;

import domen.KategorijaZakupca;
import operacija.*;
import domen.Zakupac;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Stasa
 */
public class PrikazZakupcaSO extends ApstraktnaGenerickaOperacija{
    List<Zakupac> zakupci;
    private List<KategorijaZakupca> kategorije; // Dodajemo listu za kategorije

    @Override
    protected void preduslovi(Object param) {
        //
    }


    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Zakupac filter = (Zakupac) param;
        String sql = filter.vratiJoin() + filter.vratiUslov(filter);
        
        //  Ucitavamo zakupce
        zakupci = broker.getAll(new Zakupac(), sql);
        
        // izvlacimo kategorije za zakupca
        // Koristimo Set da izbegnemo duplikate
        Set<KategorijaZakupca> setKategorija = new HashSet<>();
        for (Zakupac z : zakupci) {
            if (z.getKategorijaZakupca() != null) {
                setKategorija.add(z.getKategorijaZakupca());
            }
        }
        kategorije = new ArrayList<>(setKategorija);
    }

    public List<Zakupac> getZakupci() { 
        return zakupci; 
    }
    public List<KategorijaZakupca> getKategorije() { 
        return kategorije; 
    }
}
    

