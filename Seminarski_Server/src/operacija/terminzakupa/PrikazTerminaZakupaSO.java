/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.terminzakupa;

import domen.TerminZakupa;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class PrikazTerminaZakupaSO extends ApstraktnaGenerickaOperacija{
    List<TerminZakupa> termini;
    @Override
    protected void preduslovi(Object param) {
        //
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        termini=broker.getAll(new TerminZakupa(), "");
    }

    public List<TerminZakupa> getTermini() {
        return termini;
    }
}
