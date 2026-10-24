/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import controller.Controller;
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
import komunikacija.Odgovor;
import komunikacija.Posiljalac;
import komunikacija.Primalac;
import komunikacija.Zahtev;

/**
 *
 * @author Stasa
 */
public class ObradaKlijentskihZahteva extends Thread{
    Socket soket;
    Posiljalac posiljalac;
    Primalac primalac;
    boolean kraj=false;
    

    public ObradaKlijentskihZahteva(Socket soket) {
        this.soket=soket;
        posiljalac=new Posiljalac(soket);
        primalac=new Primalac(soket);
    }
    
    

    @Override
    public void run() {
        while (!kraj) { 
                       
                Zahtev zahtev = (Zahtev) primalac.primi();

                if (zahtev == null) {
                    System.out.println("Klijent se diskonektovao.");
                    break;
                } 
                Odgovor odgovor = new Odgovor();
                try {
                    switch (zahtev.getOperacija()) {
                    case LOGIN:
                        Zaposleni z = (Zaposleni) zahtev.getParametar();
                        z = Controller.getInstance().login(z);
                        odgovor.setOdgovor(z);
                        break;
                    case UCITAJ_ZAKUPCE: 
                        Zakupac filterZakupac=(Zakupac) zahtev.getParametar();
                        List<Zakupac> zakupci=Controller.getInstance().ucitajZakupce(filterZakupac);                       
                        odgovor.setOdgovor(zakupci);
                        break;
                    case UCITAJ_ZAPOSLENE:           
                        List<Zaposleni> zaposleni=Controller.getInstance().ucitajZaposlene();                       
                        odgovor.setOdgovor(zaposleni);
                        break;
                    case OBRISI_ZAKUPCA:
                        try{
                            Zakupac zakupac=(Zakupac) zahtev.getParametar();
                            Controller.getInstance().obrisiZakupca(zakupac);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){
                            odgovor.setOdgovor(e);
                        }
                        break; 
                    case KREIRAJ_ZAKUPCA:
                        // Uzimamo prazan objekat iz zahteva
                        Zakupac prazanZakupac = (Zakupac) zahtev.getParametar(); 
                        // metoda vraca zakupca sa ID-jem
                        Zakupac kreiranZakupac = Controller.getInstance().kreirajZakupca(prazanZakupac);

                        // Pakujemo tog zakupca sa ID-jem u odgovor da bi ga klijent dobio
                        odgovor.setOdgovor(kreiranZakupac);
                        break;

                    case DODAJ_ZAKUPCA:
                        Zakupac zZaDodavanje = (Zakupac) zahtev.getParametar();
                        Controller.getInstance().dodajZakupca(zZaDodavanje);
                        odgovor.setOdgovor(null); 
                        break;
                    case IZMENI_ZAKUPCA:
                        Zakupac zakupacI=(Zakupac) zahtev.getParametar();
                        Controller.getInstance().izmeniZakupca(zakupacI);
                        odgovor.setOdgovor(null);
                        break;
                    case UBACI_TERMIN_ZAKUPA:
                        try {
                            TerminZakupa tz = (TerminZakupa) zahtev.getParametar();
                            Controller.getInstance().dodajTerminZakupa(tz);
                            odgovor.setOdgovor(null);
                        } catch (Exception e) {
                            e.printStackTrace(); 
                            odgovor.setOdgovor(e);
                        }
                        break;

                    case UCITAJ_TERMINE_ZAKUPA:
                        try {
                            List<TerminZakupa> termini = Controller.getInstance().ucitajTermine();
                            odgovor.setOdgovor(termini);
                        } catch (Exception e) {
                            e.printStackTrace();
                            odgovor.setOdgovor(e);
                        }
                        break;
                    case IZMENI_TERMIN_ZAKUPA: 
                        try {
                            TerminZakupa tzI = (TerminZakupa) zahtev.getParametar();
                            Controller.getInstance().izmeniTerminZakupa(tzI);
                            odgovor.setOdgovor(null); 
                        } catch (Exception e) {
                            e.printStackTrace();
                            odgovor.setOdgovor(e);
                        }
                        break;
                        case UCITAJ_UGOVORE:
                            try {
                                Ugovor filterUgovor = (Ugovor) zahtev.getParametar();
                                List<Ugovor> ugovori = Controller.getInstance().ucitajUgovore(filterUgovor);
                                odgovor.setOdgovor(ugovori);
                            } catch (Exception e) {
                                e.printStackTrace();
                                odgovor.setOdgovor(e);
                            }
                            break;
                    case KREIRAJ_UGOVOR: // Ovo je novi korak: "Sistem kreira ugovor"
                        Ugovor uZaKreiranje = (Ugovor) zahtev.getParametar();
                        Ugovor kreiranUgovor = Controller.getInstance().kreirajUgovor(uZaKreiranje);
                        // Vraćamo objekat koji sada ima dodeljen ID iz baze
                        odgovor.setOdgovor(kreiranUgovor);
                        break;

                    case IZMENI_UGOVOR: 
                        Ugovor uZaIzmenu = (Ugovor) zahtev.getParametar();
                        Controller.getInstance().izmeniUgovor(uZaIzmenu);
                        odgovor.setOdgovor(null); // Ili poruka o uspehu
                        break;
                    
                    
                    default:
                        System.out.println("GRESKA, TA OPERACIJA NE POSTOJI");
                }
                
                
                posiljalac.posalji(odgovor);

            } catch (java.net.SocketException | java.io.EOFException ex) {
                System.out.println("Klijent je zatvorio aplikaciju.");
                kraj = true;
            } catch (Exception ex) {
                System.out.println("Greška u obradi zahteva: " + ex.getMessage());
                // ex.printStackTrace(); 
                kraj = true;
            }
        }
}
    
    public void prekiniNit(){
        kraj=true;
        try {
            soket.close();
        } catch (IOException ex) {
            //ex.printStackTrace();        
        }
        interrupt();
    }
    
}
