/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.terminzakupa;

import domen.TerminZakupa;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class IzmeniTerminZakupaSO extends ApstraktnaGenerickaOperacija{

     @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof TerminZakupa)) {
            throw new Exception("Sistem ne moze da zapamti termin zakupa");
        }

        TerminZakupa t = (TerminZakupa) param;

        if (t.getVrstaZakupa() == null || t.getVrstaZakupa().isEmpty()) {
            throw new Exception("Vrsta zakupa mora biti uneta");
        }

        if (t.getStatusTermina() == null) {
            throw new Exception("Status termina mora biti unet");
        }    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        broker.edit((TerminZakupa) param);

    }

}
