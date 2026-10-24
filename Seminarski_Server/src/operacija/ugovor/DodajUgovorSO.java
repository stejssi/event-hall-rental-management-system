/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.ugovor;

import java.sql.Connection;
import domen.StavkaIznajmljivanja;
import domen.Ugovor;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbConnectionFactory;

/**
 *
 * @author Stasa
 */
public class DodajUgovorSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {

        if(param == null || !(param instanceof Ugovor)){
            throw new Exception("Sistem ne može da zapamti ugovor.");
        }

        Ugovor u = (Ugovor) param;

        if(u.getDatumUgovora() == null){
            throw new Exception("Datum mora biti unet.");
        }

        if(u.getZakupac() == null){
            throw new Exception("Zakupac mora biti izabran.");
        }

        if(u.getZaposleni() == null){
            throw new Exception("Zaposleni mora biti izabran.");
        }

    }

    
    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Ugovor u = (Ugovor) param;

        // 1. Dodajemo ugovor u bazu. Broker vraća generisani ID 
        int idUgovora = broker.add(u);  

        // 2. Postavljamo taj ID u objekat ugovora
        u.setIdUgovor(idUgovora);

        // 3. Prolazimo kroz sve stavke koje su došle sa forme
        if (u.getStavke() != null) {
            for (StavkaIznajmljivanja s : u.getStavke()) {
                // 4. Svakoj stavki postavljamo ugovor koji sada ima svoj ID
                //  broker.add(s) zna koji 'idUgovor' da upiše u tabelu stavki
                s.setUgovor(u);  

                // 5. Čuvamo stavku u bazu
                broker.add(s);   
            }
        }
    }
}



