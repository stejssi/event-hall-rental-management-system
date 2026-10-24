/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.zaposleni;

import domen.Zaposleni;
import java.util.List;
import operacija.*;

/**
 *
 * @author Stasa
 */
public class PrikazZaposleniSO extends ApstraktnaGenerickaOperacija{
    List<Zaposleni> zaposleni;
    @Override
    protected void preduslovi(Object param) {
        //
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        zaposleni=broker.getAll(new Zaposleni(), "");
    }

    public List<Zaposleni> getZaposleni() {
        return zaposleni;
    }
}
