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
public class ObrisiZakupcaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) {
        if (param==null || !(param instanceof Zakupac)) {
            try {
                throw new Exception("Sistem ne moze da obrise zakupce");
            } catch (Exception ex) {
                System.getLogger(ObrisiZakupcaSO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.delete((Zakupac)param);
    }
    
}
