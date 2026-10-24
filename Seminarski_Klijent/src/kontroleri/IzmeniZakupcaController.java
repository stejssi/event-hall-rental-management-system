/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.KategorijaZakupca;
import domen.Zakupac;
import domen.Zaposleni;
import forme.DodajZakupcaForma;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class IzmeniZakupcaController {
    private final DodajZakupcaForma dzf;
    
    public IzmeniZakupcaController(DodajZakupcaForma dzf) {
        this.dzf = dzf;
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiformu(mod);
        popuniCmbBox();
        dzf.setVisible(true);
    }

    private void addActionListener() {
    // Dugme Dodaj (Kreiraj)
    dzf.kreirajAddActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
               try {
                    // Provera da li je ID uopšte upisan u polje
                    String idStr = dzf.getjTextFieldId().getText().trim();
                    if (idStr.isEmpty()) {
                        throw new Exception("Sistem nije preuzeo ID iz baze!");
                    }
                    int id = Integer.parseInt(idStr);

                    String naziv = dzf.getjTextFieldNaziv().getText().trim();
                    String email = dzf.getjTextFieldEmail().getText().trim();
                    String telefon = dzf.getjTextFieldTelefon().getText().trim();
                    String adresa = dzf.getjTextFieldAdresa().getText().trim();
                    KategorijaZakupca k = (KategorijaZakupca) dzf.getjComboBoxKategorija().getSelectedItem();

                    // Validacija
                    if (naziv.isEmpty() || email.isEmpty() || !email.contains("@") || telefon.isEmpty() || adresa.isEmpty()) {
                        JOptionPane.showMessageDialog(dzf, "Sistem ne moze da zapamti zakupca.", "GRESKA",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (k == null) {
                        JOptionPane.showMessageDialog(dzf, "Sistem ne moze da zapamti zakupca.", "GRESKA",JOptionPane.ERROR_MESSAGE);
                    return;
                    }

                    // Pravimo objekat sa ID-jem za UPDATE
                    Zakupac z = new Zakupac(id, naziv, email, telefon, adresa, k);

                    // Šaljemo na IZMENU jer red već postoji u bazi (prazan)
                    Komunikacija.getInstance().izmeniZakupca(z);

                    JOptionPane.showMessageDialog(dzf, "Sistem je zapamtio zakupca");
                    Kordinator.getInstance().osveziFormu();
                    dzf.dispose();

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dzf, "Sistem ne moze da zapamti zakupca.", "GRESKA",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    // Dugme Izmeni
    dzf.izmeniAddActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(dzf.getjTextFieldId().getText());
                String naziv = dzf.getjTextFieldNaziv().getText().trim();
                String email = dzf.getjTextFieldEmail().getText().trim();
                String telefon = dzf.getjTextFieldTelefon().getText().trim();
                String adresa = dzf.getjTextFieldAdresa().getText().trim();
                KategorijaZakupca k = (KategorijaZakupca) dzf.getjComboBoxKategorija().getSelectedItem();

                if (naziv.isEmpty() || email.isEmpty() || telefon.isEmpty() || adresa.isEmpty() || k == null) {
                JOptionPane.showMessageDialog(dzf,"Sistem ne može da zapamti zakupca","GRESKA",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Kreiraj objekat sa ID-jem postojećeg zakupca
                Zakupac z = new Zakupac(id, naziv, email, telefon, adresa, k);

                // Pozovi server da izmeni zakupca
                Komunikacija.getInstance().izmeniZakupca(z);
                JOptionPane.showMessageDialog(dzf,"Sistem je zapamtio zakupca", "USPEH",JOptionPane.INFORMATION_MESSAGE);

                // Osveži tabelu u glavnoj formi i zatvori ovu
                Kordinator.getInstance().osveziFormu();
                dzf.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dzf,"Sistem ne može da zapamti zakupca","GRESKA",JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}
    private void pripremiformu(FormaMod mod) {
        // Preuzimamo zakupca kojeg je Kordinator sačuvao (onog sa novim ID-jem)
    Zakupac z = (Zakupac) Kordinator.getInstance().vratiParam("zakupac");

        switch (mod) {
            case DODAJ: // Ovo koristimo odmah nakon što smo "rezervisali" red u bazi
                dzf.setTitle("Kreiraj zakupca"); 
                dzf.getjButtonDodaj().setVisible(true); // Prikaži dugme DODAJ
                dzf.getjButtonAzuriraj().setVisible(false); // Sakrij dugme IZMENI

                if (z != null) {
                    dzf.getjTextFieldId().setText(String.valueOf(z.getIdZakupac()));
                }
                dzf.getjTextFieldId().setEditable(false); 
                break;

            case IZMENI:
                dzf.setTitle("Izmeni zakupca");
                dzf.getjButtonDodaj().setVisible(false);
                dzf.getjButtonAzuriraj().setVisible(true);

                if (z != null) {
                popuniPolja(z);
            }
                dzf.getjTextFieldId().setEnabled(false);
                break;
           case PREGLEDAJ: // DODATAK ZA DUPLI KLIK
                    dzf.setTitle("Detalji zakupca");
                    dzf.getjButtonDodaj().setVisible(false);
                    dzf.getjButtonAzuriraj().setVisible(false);

                    if (z != null) {
                        popuniPolja(z);
                    }

                    // ZAKLJUČAVANJE SVIH POLJA
                    dzf.getjTextFieldId().setEnabled(false);
                    dzf.getjTextFieldNaziv().setEnabled(false);
                    dzf.getjTextFieldEmail().setEnabled(false);
                    dzf.getjTextFieldTelefon().setEnabled(false);
                    dzf.getjTextFieldAdresa().setEnabled(false);
                    dzf.getjComboBoxKategorija().setEnabled(false);
                    break;


            default:
                throw new AssertionError();
        }
    }

    public void popuniCmbBox() {
    // Čistimo ComboBox pre punjenja
    dzf.getjComboBoxKategorija().removeAllItems();

    try {
        // 1. Učitavamo sve zakupce (koristeći JOIN )
        List<Zakupac> zakupci = Komunikacija.getInstance().ucitajZakupce(new Zakupac());

        // 2. Set koristimo da izbegnemo duplikate kategorija
        Set<KategorijaZakupca> setKategorija = new HashSet<>();

        if (zakupci != null && !zakupci.isEmpty()) {
            for (Zakupac z : zakupci) {
                // Ako zakupac ima dodeljenu kategoriju, dodajemo je u set
                if (z.getKategorijaZakupca() != null) {
                    setKategorija.add(z.getKategorijaZakupca());
                }
            }
        }
        for (KategorijaZakupca kat : setKategorija) {
            dzf.getjComboBoxKategorija().addItem(kat);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    
    }

    private void popuniPolja(Zakupac z) {
    dzf.getjTextFieldId().setText(String.valueOf(z.getIdZakupac()));
    dzf.getjTextFieldNaziv().setText(z.getNazivZakupac());
    dzf.getjTextFieldEmail().setText(z.getEmailZakupca());
    dzf.getjTextFieldTelefon().setText(z.getTelefon());
    dzf.getjTextFieldAdresa().setText(z.getAdresa());
    dzf.getjComboBoxKategorija().setSelectedItem(z.getKategorijaZakupca());
}
}
