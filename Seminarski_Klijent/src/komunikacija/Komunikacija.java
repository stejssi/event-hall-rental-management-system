/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import domen.KategorijaZakupca;
import domen.Sala;
import domen.StavkaIznajmljivanja;
import domen.TerminZakupa;
import domen.Ugovor;
import domen.Zakupac;
import domen.Zaposleni;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class Komunikacija {
    private Socket soket;  
    private static Komunikacija instance;
    private Posiljalac posiljalac;
    private Primalac primalac;
    
    private Komunikacija(){
        
    }

    public static Komunikacija getInstance() {
        if (instance==null) {
            instance=new Komunikacija();
        }
        return instance;
    }
    
    public void konekcija() {
        try {
            soket=new Socket("localhost", 9000); 
            posiljalac=new Posiljalac(soket);
            primalac=new Primalac(soket);
        } catch (IOException ex) {
            System.out.println("SERVER NIJE POVEZAN");
        }
       
        
    }

    public Zaposleni login(String username, String pass) {
        Zaposleni z=new Zaposleni();
        z.setKorisnickiNalog(username);
        z.setLozinka(pass);
        Zahtev zahtev=new Zahtev(Operacija.LOGIN, z);
        
        posiljalac.posalji(zahtev);
        
        Odgovor odg=(Odgovor) primalac.primi();
        z=(Zaposleni) odg.getOdgovor();
        return z;
    }
    public List<Zaposleni> ucitajZaposlene() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_ZAPOSLENE, null);
        List<Zaposleni> zaposleni=new ArrayList<>();

        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        zaposleni=(List<Zaposleni>) odg.getOdgovor();

        return zaposleni;
    }

    public List<Zakupac> ucitajZakupce(Zakupac filter) {
        Zahtev zahtev=new Zahtev(Operacija.UCITAJ_ZAKUPCE, filter);
        List<Zakupac> zakupci=new ArrayList<>();
        
        posiljalac.posalji(zahtev);

        Odgovor odg=(Odgovor) primalac.primi();
        zakupci=(List<Zakupac>) odg.getOdgovor();
        
        
        return zakupci;
                
    }
    

    public void obrisiZakupca(Zakupac z) throws Exception {
        Zahtev zahtev=new Zahtev(Operacija.OBRISI_ZAKUPCA, z);        
        posiljalac.posalji(zahtev);
        
        Odgovor odg=(Odgovor) primalac.primi();
        if (odg.getOdgovor()==null) {
            System.out.println("USPEH");
           
        }else{
            System.out.println("GRESKA");
            
        }
        
        
    }

    public void dodajZakupca(Zakupac z) {
        Zahtev zahtev=new Zahtev(Operacija.DODAJ_ZAKUPCA, z);        
        posiljalac.posalji(zahtev);
        
        Odgovor odg=(Odgovor) primalac.primi();
        if (odg.getOdgovor()==null) {
            System.out.println("USPEH");
           
        }else{
            System.out.println("GRESKA");
        }
        
    }
    public Zakupac kreirajZakupca(Zakupac z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.KREIRAJ_ZAKUPCA, z);       
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        // Server treba da u odg.getOdgovor() vrati objekat Zakupac sa ID-jem
        return (Zakupac) odg.getOdgovor();
    }

    public void izmeniZakupca(Zakupac z) {
        Zahtev zahtev=new Zahtev(Operacija.IZMENI_ZAKUPCA, z);        
        posiljalac.posalji(zahtev);
        
        Odgovor odg=(Odgovor) primalac.primi();
        if (odg.getOdgovor()==null) {
            System.out.println("USPEH");
            Kordinator.getInstance().osveziFormu();
           
        }else{
            System.out.println("GRESKA");
        }
    }
    
    public void dodajTerminZakupa(TerminZakupa t) {
        Zahtev zahtev = new Zahtev(Operacija.UBACI_TERMIN_ZAKUPA, t);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();

        if (odg.getOdgovor() == null) {
            System.out.println("USPEH");
        } else {
            System.out.println("GRESKA");
        }
    }

    public List<TerminZakupa> ucitajTermine() {
        Zahtev zahtev=new Zahtev(Operacija.UCITAJ_TERMINE_ZAKUPA, null);
        List<TerminZakupa> termini=new ArrayList<>();
        
        posiljalac.posalji(zahtev);

        Odgovor odg=(Odgovor) primalac.primi();
        termini=(List<TerminZakupa>) odg.getOdgovor();
        
        
        return termini;
    }

    public void izmeniTerminZakupa(TerminZakupa t) throws Exception{
        Zahtev z = new Zahtev();
        z.setOperacija(Operacija.IZMENI_TERMIN_ZAKUPA);
        z.setParametar(t);

        posiljalac.posalji(z);
        Odgovor o = (Odgovor) primalac.primi();

        if(o.getOdgovor() instanceof Exception){
            throw (Exception) o.getOdgovor();
        }
    }    

    public List<Ugovor> ucitajUgovore(Ugovor filter) {
        // saljemo filter (koji može biti prazan ili popunjen parametrima pretrage)
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_UGOVORE, filter);
        List<Ugovor> lista = new ArrayList<>();

        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        // Proveravamo da li je ono što je stiglo u 'odgovoru' zapravo Exception
        if (odg.getOdgovor() instanceof Exception) {
            try { 
                throw (Exception) odg.getOdgovor();
            } catch (Exception ex) {
                System.getLogger(Komunikacija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        lista = (List<Ugovor>) odg.getOdgovor();
        return lista;
}


    public void izmeniUgovor(Ugovor u) throws Exception {
        Zahtev zahtev=new Zahtev(Operacija.IZMENI_UGOVOR, u);        
        posiljalac.posalji(zahtev);
        
        Odgovor odg=(Odgovor) primalac.primi();
        if (odg.getOdgovor()==null) {
            System.out.println("USPEH");
            Kordinator.getInstance().osveziFormuIUF();
           
        }else{
            System.out.println("GRESKA");
        }
    }

   public Ugovor kreirajUgovor(Ugovor u) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.KREIRAJ_UGOVOR, u);
        posiljalac.posalji(zahtev);

        Odgovor odg = (Odgovor) primalac.primi();
        
        return (Ugovor) odg.getOdgovor();
    }


    
    }

   



    

