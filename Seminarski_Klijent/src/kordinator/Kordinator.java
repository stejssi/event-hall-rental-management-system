/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kordinator;

import domen.TerminZakupa;
import domen.Ugovor;
import domen.Zaposleni;
import forme.DodajZakupcaForma;
import forme.FormaMod;
import forme.GlavnaForma;
import forme.IzmeniTerminZakupaForma;
import forme.IzmeniUgovorForma;
import forme.LoginForma;
import forme.PrikazTerminaZakupaForma;
import forme.PrikazUgovoraForma;
import forme.PrikazZakupacaForma;
import java.util.HashMap;
import java.util.Map;
import kontroleri.IzmeniZakupcaController;
import kontroleri.GlavnaFormaController;
import kontroleri.IzmeniTerminZakupaController;
import kontroleri.IzmeniUgovorController;
import kontroleri.LoginController;
import kontroleri.PrikazTerminaZakupaController;
import kontroleri.PrikazUgovoraController;
import kontroleri.PrikazZakupacaController;

/**
 *
 * @author Stasa
 */
public class Kordinator {
    private static Kordinator instance;
    private Zaposleni ulogovani;
    private LoginController loginController;
    private GlavnaFormaController glavnaFormaController;
    private PrikazZakupacaController pzController;
    private IzmeniZakupcaController dzController;
    private IzmeniUgovorController iuController;
    private IzmeniTerminZakupaController itController;
    private PrikazTerminaZakupaController terminController;
    private PrikazUgovoraController puController;
    
    private Map<String, Object> parametri;
   
    private Kordinator(){
       parametri=new HashMap<>();
    }

    public static Kordinator getInstance() {
        if (instance==null) {
          instance=new Kordinator();
        }
        return instance;
    }

    public void otvoriLoginFormu() {
      loginController=new LoginController(new LoginForma());
      loginController.otvoriFormu();
    }

    public void otvoriGlavnuFormu() {
      glavnaFormaController=new GlavnaFormaController(new GlavnaForma());
      glavnaFormaController.otvoriFormu();    }
    
    public void otvoriPrikazZakupacaFormu() {
      pzController=new PrikazZakupacaController(new PrikazZakupacaForma());
      pzController.otvoriFormu();   
    
    }
    public Zaposleni getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Zaposleni ulogovani) {
        this.ulogovani = ulogovani;
    }


    public void dodajParam(String s, Object o){
        parametri.put(s, o);
    }

    public Object vratiParam(String s){
        return parametri.get(s);
    }

    // Objedinjena metoda za Izmenu i Pregled (detaljan prikaz)
    public void otvoriIzmeniZakupcaFormu(FormaMod mod) {
        // Kreiramo kontroler i novu formu
        dzController = new IzmeniZakupcaController(new DodajZakupcaForma());
        dzController.otvoriFormu(mod); 
    }

    public void otvoriKreirajZakupcaFormu() {
        dzController = new IzmeniZakupcaController(new DodajZakupcaForma());
        dzController.otvoriFormu(FormaMod.DODAJ); 
    }

    public void osveziFormu() {
        pzController.osveziFormu();
    }
    
    public void otvoriTerminZakupaFormu() {
        terminController = new PrikazTerminaZakupaController(new PrikazTerminaZakupaForma());
        terminController.otvoriFormu();
    }

    public void otvoriIzmeniTerminZakupaFormu() {
       // Termin koji želimo da izmenimo
    TerminZakupa t = (TerminZakupa) vratiParam("termin"); 

    // Kreiramo controller sa formom i ugovorom
    itController = new IzmeniTerminZakupaController(new IzmeniTerminZakupaForma(), t);
    itController.otvoriFormu();
    }
   
    public void otvoriPrikazUgovoraFormu() {
      puController=new PrikazUgovoraController(new PrikazUgovoraForma());
      puController.otvoriFormu();   
    
    }

   public void otvoriIzmeniUgovorFormu(FormaMod mod) {
    Ugovor u = (Ugovor) vratiParam("ugovor"); 
        try {
            iuController = new IzmeniUgovorController(new IzmeniUgovorForma(), u);
        } catch (Exception ex) {
            System.getLogger(Kordinator.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    // Prosledi mod koji smo dobili kao parametar
    iuController.otvoriFormu(mod);
}
    public void osveziFormuIUF() {
        if (puController != null) {
            puController.pripremiFormu(); 
        }
    }
    public void osveziTermine() {
      if (terminController != null) {
         terminController.pripremiFormu();
     }
    }
    

}
