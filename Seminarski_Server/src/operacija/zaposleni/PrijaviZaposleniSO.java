/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.zaposleni;

import operacija.*;
import domen.Zaposleni;
import java.util.List;
import operacija.zakupci.ObrisiZakupcaSO;

/**
 *
 * @author Stasa
 */
public class PrijaviZaposleniSO extends ApstraktnaGenerickaOperacija {
    Zaposleni zaposleni;
    @Override
    protected void preduslovi(Object param) {
if (param==null || !(param instanceof Zaposleni)) {
            try {
                throw new Exception("Sistem ne moze da pronadje zaposlenog");
            } catch (Exception ex) {
                System.getLogger(PrijaviZaposleniSO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception{
        List<Zaposleni> sviZaposleni=broker.getAll((Zaposleni)param, null);
        //System.out.println("Klasa: LoginOperacija SO "+sviZaposleni);
        if (sviZaposleni.contains((Zaposleni)param)) {
            for (Zaposleni z : sviZaposleni) {
                if (z.equals((Zaposleni)param)) {
                    zaposleni=z;
                    return;
                }
            }
        }else{
        zaposleni=null;    
        }  
        
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }
    
}
