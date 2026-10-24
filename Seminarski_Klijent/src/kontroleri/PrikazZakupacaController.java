/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.KategorijaZakupca;
import domen.Zakupac;
import forme.FormaMod;
import static forme.FormaMod.IZMENI;
import forme.PrikazZakupacaForma;
import forme.model.ModelTabeleZakupci;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class PrikazZakupacaController {
    private final PrikazZakupacaForma pzf;
    
    public PrikazZakupacaController(PrikazZakupacaForma pzf) {
        this.pzf = pzf;
        addActionListener();
        addMouseListener();
        
    }

    

    public void otvoriFormu() {
        pripremiFormu();
        pzf.setLocationRelativeTo(null); // Centriramo prikaz zakupaca
        pzf.setVisible(true);
}

    public void pripremiFormu() {
        // 1. Pozovi bazu samo JEDNOM
        List<Zakupac> zakupci = Komunikacija.getInstance().ucitajZakupce(new Zakupac()); 

        // 2. Postavi tabelu
        ModelTabeleZakupci mtz = new ModelTabeleZakupci(zakupci);
        pzf.getjTableZakupci().setModel(mtz);

        // 3. Prosledi već učitanu listu u ComboBox da ne bi ponovo išao u bazu
        popuniCmbBox(zakupci); 
}

    public void popuniCmbBox(List<Zakupac> sviZakupci) {
        pzf.getjComboBoxKategorija().removeAllItems();
        pzf.getjComboBoxKategorija().addItem(null);

        Set<KategorijaZakupca> setKategorija = new HashSet<>();

        if (sviZakupci != null) {
            for (Zakupac z : sviZakupci) {
                if (z.getKategorijaZakupca() != null) {
                    setKategorija.add(z.getKategorijaZakupca());
                }
            }
        }

        for (KategorijaZakupca k : setKategorija) {
            pzf.getjComboBoxKategorija().addItem(k);
        }
}
    
    
    private void addActionListener() {
        pzf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red=pzf.getjTableZakupci().getSelectedRow();
                if (red==-1) {
                    JOptionPane.showMessageDialog(pzf, "Sistem ne moze da obrise zakupce", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    ModelTabeleZakupci mtz=(ModelTabeleZakupci) pzf.getjTableZakupci().getModel();
                    Zakupac z=mtz.getLista().get(red);
                    // Dodajemo prozor za potvrdu
                    int odgovor = JOptionPane.showConfirmDialog(pzf, 
                            "Da li ste sigurni da želite da obrisete zakupca: " + z.getNazivZakupac() + "?", 
                            "Potvrda brisanja", 
                            JOptionPane.YES_NO_OPTION);

                    // Ako korisnik klikne "YES", tek onda brisemo
                    if (odgovor == JOptionPane.YES_OPTION) {
                        try {
                            Komunikacija.getInstance().obrisiZakupca(z);
                            JOptionPane.showMessageDialog(pzf, "Sistem je obrisao zakupca", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                            pripremiFormu(); 
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(pzf, "Sistem ne može da obriše zakupca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                        // Ako klikne "NO", ništa se ne dešava, prozor se samo zatvori

                    }
                }
            
        });
        pzf.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red=pzf.getjTableZakupci().getSelectedRow();
                if (red==-1) {
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupca", "GRESKA", JOptionPane.ERROR_MESSAGE);                }
                else{
                    ModelTabeleZakupci mtz=(ModelTabeleZakupci) pzf.getjTableZakupci().getModel();
                    Zakupac z=mtz.getLista().get(red);
                    JOptionPane.showMessageDialog(pzf, "Sistem je našao zakupca.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Kordinator.getInstance().dodajParam("zakupac", z);
                    Kordinator.getInstance().otvoriIzmeniZakupcaFormu(IZMENI);
                    
                    pripremiFormu();
                    
                }
            }
        });
        pzf.addBtnKreirajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                Zakupac novi = new Zakupac();

                // Server kreira prazan red u bazi i vraća nam ga sa ID-jem
                novi = Komunikacija.getInstance().kreirajZakupca(novi);
                // DODATNA PROVERA: Ako server nije vratio ID, znači da kreiranje nije uspelo
                if (novi == null || novi.getIdZakupac() <= 0) {
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da kreira zakupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                // Odmah osvežavamo tabelu na glavnoj formi da se vidi taj novi red
                pripremiFormu(); 

                // Prikazujemo potvrdu 
                JOptionPane.showMessageDialog(pzf, "Sistem je kreirao zakupca.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                //  i otvaramo formu za unos 
                Kordinator.getInstance().dodajParam("zakupac", novi);
                Kordinator.getInstance().otvoriKreirajZakupcaFormu(); 

                //Ponovo osvežimo tabelu kad popuni podatke
                pripremiFormu();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pzf, "Sistem ne može da kreira zakupca.", "Greška", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    });
        pzf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Prikupljamo podatke sa forme
                String naziv = pzf.getjTextFieldNaziv().getText().trim();
                String email = pzf.getjTextFieldEmail().getText().trim();
                String telefon = pzf.getjTextFieldTelefon().getText().trim();
                String adresa = pzf.getjTextFieldAdresa().getText().trim();
                KategorijaZakupca k = (KategorijaZakupca) pzf.getjComboBoxKategorija().getSelectedItem();
                //PROVERA: Ako je (naziv prazan) I (email prazan) I (adresa prazna)
                if (naziv.isEmpty() && email.isEmpty() && adresa.isEmpty() && telefon.isEmpty()&&k==null) {
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupca po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
                    pripremiFormu();
                    return; 
                }
                //Pakujemo ih u objekat koji sluzi kao filter
                Zakupac filter = new Zakupac();
                filter.setNazivZakupac(naziv);
                filter.setEmailZakupca(email);
                filter.setTelefon(telefon);
                filter.setAdresa(adresa);
                filter.setKategorijaZakupca(k);
                

                // Pozivamo server da nam vrati FILTRIRANU listu
                List<Zakupac> filtriraniZakupci = Komunikacija.getInstance().ucitajZakupce(filter);

                // Osvežavamo tabelu novom listom
                if (filtriraniZakupci == null || filtriraniZakupci.isEmpty()) {
                    JOptionPane.showMessageDialog(pzf, "Sistem ne može da nađe zakupca po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
                    pzf.getjTableZakupci().setModel(new ModelTabeleZakupci(new ArrayList<>()));
                } else {
                    ModelTabeleZakupci mtz = new ModelTabeleZakupci(filtriraniZakupci);
                    pzf.getjTableZakupci().setModel(mtz);
                    JOptionPane.showMessageDialog(pzf, "Sistem je našao zakupca po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        pzf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 // cisti polja
                pzf.getjTextFieldNaziv().setText("");
                pzf.getjTextFieldEmail().setText("");
                pzf.getjTextFieldTelefon().setText("");
                pzf.getjTextFieldAdresa().setText("");

                // vracamo cmbbox
                pzf.getjComboBoxKategorija().setSelectedIndex(0);

                pripremiFormu();

            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }
    private void addMouseListener() {
    pzf.getjTableZakupci().addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            // Proveravamo da li je red selektovan i da li je bio dupli klik
            int red = pzf.getjTableZakupci().getSelectedRow();
            
            if (e.getClickCount() == 2 && red != -1) {
                ModelTabeleZakupci mtz = (ModelTabeleZakupci) pzf.getjTableZakupci().getModel();
                Zakupac z = mtz.getLista().get(red);

                JOptionPane.showMessageDialog(pzf, "Sistem je našao zakupca.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                // 2. Slanje u Kordinator i otvaranje forme
                Kordinator.getInstance().dodajParam("zakupac", z);
                Kordinator.getInstance().otvoriIzmeniZakupcaFormu(FormaMod.PREGLEDAJ);                
                // 3. Osvežavanje tabele nakon povratka
                pripremiFormu();
            }
        }
    });
}
}
