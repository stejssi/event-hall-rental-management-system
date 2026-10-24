/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.ugovor;

import domen.Sala;
import domen.StavkaIznajmljivanja;
import domen.Ugovor;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class PrikazUgovoraSO extends ApstraktnaGenerickaOperacija {
    List<Ugovor> ugovori;
    @Override
    protected void preduslovi(Object param) {
        //
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Ugovor filterUgovor = (Ugovor) param;

        // Rezultat ce biti: JOIN... JOIN... WHERE 1=1 AND...
        String sqlUslov = " " + filterUgovor.vratiJoin() + filterUgovor.vratiUslov(filterUgovor);

        // Ucitavamo filtrirane ugovore iz baze
        ugovori = (List<Ugovor>)(List<?>) broker.getAll(filterUgovor, sqlUslov);

        // Ucitavamo sve sale (trebaju nam da bismo ih povezali sa stavkama)
        List<Sala> sveSale = (List<Sala>)(List<?>) broker.getAll(new Sala(), "");

        // Za svaki nadjeni ugovor ucitavamo njegove stavke
        for (Ugovor u : ugovori) {
            String uslovStavke = " JOIN sala ON stavkaiznajmljivanja.sala = sala.idSale "
                               + " WHERE stavkaiznajmljivanja.ugovor = " + u.getIdUgovor();

            List<StavkaIznajmljivanja> stavke = (List<StavkaIznajmljivanja>)(List<?>) broker.getAll(new StavkaIznajmljivanja(), uslovStavke);

            // Povezujemo svaku stavku sa objektom Sale iz liste svih sala
            for (StavkaIznajmljivanja s : stavke) {
                for (Sala sala : sveSale) {
                    if (sala.getIdSale() == s.getSala().getIdSale()) {
                        s.setSala(sala);
                        break;
                    }
                }
            }
            // Dodajemo listu stavki u ugovor
            u.setStavke(stavke);
        }
    }

    public List<Ugovor> getUgovori() {
        return ugovori;
    }
}
