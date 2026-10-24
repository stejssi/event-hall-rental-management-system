/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domen.KategorijaZakupca;
import domen.StavkaIznajmljivanja;
import domen.TerminZakupa;
import domen.Ugovor;
import domen.Zakupac;
import domen.Zaposleni;
import java.util.List;
import operacija.terminzakupa.IzmeniTerminZakupaSO;
import operacija.terminzakupa.PrikazTerminaZakupaSO;
import operacija.terminzakupa.UbaciTerminZakupaSO;
import operacija.ugovor.DodajUgovorSO;
import operacija.ugovor.IzmeniUgovorSO;
import operacija.ugovor.KreirajUgovorSO;
import operacija.ugovor.PrikazUgovoraSO;
import operacija.zakupci.IzmeniZakupcaSO;
import operacija.zakupci.DodajZakupcaSO;
import operacija.zakupci.KreirajZakupcaSO;
import operacija.zakupci.ObrisiZakupcaSO;
import operacija.zakupci.PrikazZakupcaSO;
import operacija.zaposleni.PrijaviZaposleniSO;
import operacija.zaposleni.PrikazZaposleniSO;

/**
 *
 * @author Stasa
 */
public class Controller {
    private static Controller instance;
    
    private Controller(){
        
    }

    public static Controller getInstance() {
        if (instance==null) {
          instance=new Controller();
        }
        return instance;
    }

    public Zaposleni login(Zaposleni z) throws Exception {
        PrijaviZaposleniSO operacija=new PrijaviZaposleniSO();
        operacija.izvrsi(z, null);
        //System.out.println("Klasa: Controller "+operacija.getZaposleni());
        return operacija.getZaposleni();
    }

    public List<Zakupac> ucitajZakupce(Zakupac filter) throws Exception {
        PrikazZakupcaSO operacija=new PrikazZakupcaSO();
        operacija.izvrsi(filter, null);
        return operacija.getZakupci();
    }
    public List<Zaposleni> ucitajZaposlene() throws Exception {
        PrikazZaposleniSO operacija=new PrikazZaposleniSO();
        operacija.izvrsi(null, null);
        return operacija.getZaposleni();
    }
    public void obrisiZakupca(Zakupac zakupac) throws Exception {
        ObrisiZakupcaSO operacija=new ObrisiZakupcaSO();
        operacija.izvrsi(zakupac, null);
    }

    public void dodajZakupca(Zakupac zakupacD) throws Exception {
        DodajZakupcaSO operacija=new DodajZakupcaSO();
        operacija.izvrsi(zakupacD, null);
    }
    public Zakupac kreirajZakupca(Zakupac zakupac) throws Exception {
        KreirajZakupcaSO operacija = new KreirajZakupcaSO();
        // radi INSERT praznog reda
        operacija.izvrsi(zakupac, null);

        // Vraćamo onog istog zakupca, ali sada sa dodeljenim ID-jem iz baze
        return operacija.getZakupac();
    }
    public void izmeniZakupca(Zakupac zakupacI) throws Exception {
        IzmeniZakupcaSO operacija=new IzmeniZakupcaSO();
        operacija.izvrsi(zakupacI, null);
    }

    public void dodajTerminZakupa(TerminZakupa t) throws Exception {
        UbaciTerminZakupaSO operacija = new UbaciTerminZakupaSO();
        operacija.izvrsi(t, null);
    }

    public List<TerminZakupa> ucitajTermine() throws Exception {
        PrikazTerminaZakupaSO operacija=new PrikazTerminaZakupaSO();
        operacija.izvrsi(null, null);
        return operacija.getTermini();
    }

    public List<Ugovor> ucitajUgovore(Ugovor filter) throws Exception {
        PrikazUgovoraSO operacija=new PrikazUgovoraSO();
        operacija.izvrsi(filter, null);
        return operacija.getUgovori();
    }

   

    public void izmeniUgovor(Ugovor u) throws Exception {
        IzmeniUgovorSO operacija=new IzmeniUgovorSO();
        operacija.izvrsi(u, null);
    }

    public void izmeniTerminZakupa(TerminZakupa tzI) throws Exception {
        IzmeniTerminZakupaSO operacija=new IzmeniTerminZakupaSO();
        operacija.izvrsi(tzI, null);
    }

    

    public Zakupac kreirajZakupca() throws Exception {
        KreirajZakupcaSO operacija = new KreirajZakupcaSO();
        operacija.izvrsi(null, null);
        return operacija.getZakupac();
    }

    public void dodajUgovor(Ugovor u) throws Exception {
        DodajUgovorSO operacija = new DodajUgovorSO();
        operacija.izvrsi(u, null);
    }

    public Ugovor kreirajUgovor(Ugovor ugovor) throws Exception {
        KreirajUgovorSO operacija = new KreirajUgovorSO();
        operacija.izvrsi(ugovor, null);
        return operacija.getUgovor(); // Vraća ugovor sa novim ID-jem
    }    
    
}
