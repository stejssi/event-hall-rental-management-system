/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.ugovor;

import domen.StavkaIznajmljivanja;
import domen.Ugovor;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Stasa
 */
public class IzmeniUgovorSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Ugovor)) {
            throw new Exception("Sistem ne može da izmeni ugovor: parametar nije validan.");
        }

        Ugovor u = (Ugovor) param;

        if (u.getIdUgovor() <= 0) {
            throw new Exception("ID ugovora mora biti veći od 0.");
        }

        if (u.getDatumUgovora() == null) {
            throw new Exception("Datum ugovora mora biti unet.");
        }


        if (u.getNacinPlacanja() == null) {
            throw new Exception("Način plaćanja mora biti unet.");
        }

        if (u.getZaposleni() == null || u.getZaposleni().getIdZaposlen() <= 0) {
            throw new Exception("Zaposleni mora biti izabran i imati validan ID.");
        }

        if (u.getZakupac() == null || u.getZakupac().getIdZakupac() <= 0) {
            throw new Exception("Zakupac mora biti izabran i imati validan ID.");
        }
    }

   @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Ugovor u = (Ugovor) param;

        // 1. Ažuriraj zaglavlje ugovora
        broker.edit(u);

        StavkaIznajmljivanja pomocna = new StavkaIznajmljivanja();
        pomocna.setUgovor(u); //punimo objekat id-jem ugovora koji trenutno menjam 

        // Pravimo upit koji spaja tabele 
        String uslovZaStare = " JOIN sala ON stavkaiznajmljivanja.sala = sala.idSale "
                            + " WHERE stavkaiznajmljivanja.ugovor = " + u.getIdUgovor();

        // Poziv brokera sa uslovom
        List<StavkaIznajmljivanja> stareStavke = (List<StavkaIznajmljivanja>) (Object) broker.getAll(pomocna, uslovZaStare);

            //POREDJENJE
            List<StavkaIznajmljivanja> noveStavke = u.getStavke();
            // A) DELETE: Ako je stavka u bazi, a NEMA JE u novoj listi
            for (StavkaIznajmljivanja stara : stareStavke) {
                if (!noveStavke.contains(stara)) {
                    broker.delete(stara);
                }
            }

            // B) ADD / EDIT: Prolazimo kroz nove stavke
            if (noveStavke != null) {
                for (StavkaIznajmljivanja nova : noveStavke) {
                    nova.setUgovor(u);
                    if (stareStavke.contains(nova)) {
                        // Stavka postoji i u bazi i u novoj listi -> UPDATE
                        broker.edit(nova);
                    } else {
                        // Stavka je nova (nije je bilo u bazi) -> ADD
                        broker.add(nova);
                    }
                }
            }
        }
}

    

