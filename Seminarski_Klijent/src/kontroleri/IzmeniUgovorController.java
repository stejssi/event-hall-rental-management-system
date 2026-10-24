/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.NacinPlacanja;
import domen.Sala;
import domen.StavkaIznajmljivanja;
import domen.Ugovor;
import domen.Zakupac;
import domen.Zaposleni;
import forme.FormaMod;
import forme.IzmeniUgovorForma;
import forme.model.ModelTabeleStavkeIznajmljivanja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kordinator.Kordinator;
import java.sql.Date;

/**
 *
 * @author Stasa
 */
public class IzmeniUgovorController {
private final IzmeniUgovorForma iuf;
    private Ugovor ugovor;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public IzmeniUgovorController(IzmeniUgovorForma iuf, Ugovor ugovor) throws Exception {
        this.iuf = iuf;
        this.ugovor = ugovor;
        popuniComboBoxeve();
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod) {
        try {
            // Prvo popunimo kombo boksove (podaci sa servera)
            popuniComboBoxeve();
            // Zatim podesimo izgled polja zavisno od moda (DODAJ/IZMENI)
            pripremiFormu(mod);
            // Na kraju prikažemo formu
            iuf.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(iuf, "Greška pri otvaranju forme: " + e.getMessage());
        }
    
    }

private void pripremiFormu(FormaMod mod) throws Exception {
    // Zajednička podešavanja za oba moda - polja koja se ne kucaju ručno
    iuf.getjTextFieldIznos().setEnabled(false);
    iuf.getjTextFieldUkupnaCena().setEnabled(false); 
    iuf.getjTextFieldCena().setEnabled(false);

    switch (mod) {
        case DODAJ:
            popuniComboBoxeve(); 
            
            iuf.setTitle("Kreiranje ugovora");
            iuf.getjButtonKreiraj().setText("Zapamti ugovor"); 
            iuf.getjButtonIzmeni().setVisible(false);
            iuf.getjButtonKreiraj().setVisible(true);

            // Inicijalizujemo praznu tabelu
            iuf.getjTableStavke().setModel(new ModelTabeleStavkeIznajmljivanja(new ArrayList<>()));
            
            // Postavljanje početnih vrednosti
            iuf.getjTextFieldUkupnaCena().setText("0.0");
            iuf.getjComboBoxZaposleni().setSelectedIndex(-1);
            iuf.getjComboBoxZakupac().setSelectedIndex(-1);
            iuf.getjTextFieldDatum().setText("");
            
            // Postavljanje današnjeg datuma kao podrazumevanog
            if (ugovor.getDatumUgovora() == null) {
                ugovor.setDatumUgovora(new java.sql.Date(System.currentTimeMillis()));
            }
            iuf.getjTextFieldDatum().setText(new java.text.SimpleDateFormat("dd.MM.yyyy").format(ugovor.getDatumUgovora()));
            break;

        case IZMENI:
            popuniComboBoxeve();
            
            iuf.setTitle("Izmena ugovora - Broj: " + ugovor.getIdUgovor());
            iuf.getjButtonKreiraj().setVisible(false);
            iuf.getjButtonIzmeni().setVisible(true);
            iuf.getjButtonIzmeni().setEnabled(true);
            
            // Selektujemo postojeće vrednosti iz objekta ugovor
            iuf.getjComboBoxNacin().setSelectedItem(ugovor.getNacinPlacanja());
            selektujZaposlenog(ugovor.getZaposleni());
            iuf.getjComboBoxZakupac().setSelectedItem(ugovor.getZakupac());

            // Popunjavanje datuma i ukupne cene
            if (ugovor.getDatumUgovora() != null) {
                iuf.getjTextFieldDatum().setText(new java.text.SimpleDateFormat("dd.MM.yyyy").format(ugovor.getDatumUgovora()));
            }
            iuf.getjTextFieldUkupnaCena().setText(String.valueOf(ugovor.getUkupnaCena()));

            // Onemogućavamo promenu strana ugovora
            iuf.getjComboBoxZaposleni().setEnabled(false);
            iuf.getjComboBoxZakupac().setEnabled(false);

            // Punjenje tabele postojećim stavkama
            ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(ugovor.getStavke());
            iuf.getjTableStavke().setModel(mtsi);
            break;
        case PREGLEDAJ:
                popuniComboBoxeve();

                iuf.setTitle("Detaljan pregled ugovora - Broj: " + ugovor.getIdUgovor());

                // Krijemo dugmice
                iuf.getjButtonKreiraj().setVisible(false);
                iuf.getjButtonIzmeni().setVisible(false);
                iuf.getjButtonDodajS().setVisible(false); // Onemogući dodavanje stavki
                iuf.getjButtonUkloniS().setVisible(false);

                // Selektujemo podatke (za zaposlenog imamo metodu da ne bi equals menjali zbog logina)
                selektujZaposlenog(ugovor.getZaposleni()); 
                iuf.getjComboBoxZakupac().setSelectedItem(ugovor.getZakupac());
                iuf.getjComboBoxNacin().setSelectedItem(ugovor.getNacinPlacanja());

                // Korisnik ne može ništa da menja
                iuf.getjComboBoxZaposleni().setEnabled(false);
                iuf.getjComboBoxZakupac().setEnabled(false);
                iuf.getjComboBoxNacin().setEnabled(false);
                iuf.getjTextFieldDatum().setEnabled(false);
                iuf.getjTableStavke().setEnabled(false); // Tabela samo za gledanje

                // Popunjavamo ostalo
                if (ugovor.getDatumUgovora() != null) {
                    // Formatiramo datum iz baze u String za text field
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    iuf.getjTextFieldDatum().setText(sdf.format(ugovor.getDatumUgovora()));
                }
                iuf.getjTextFieldUkupnaCena().setText(String.valueOf(ugovor.getUkupnaCena()));
                iuf.getjTableStavke().setModel(new ModelTabeleStavkeIznajmljivanja(ugovor.getStavke()));
                break;

        default:
            throw new AssertionError();
    }
}

    private void addActionListener() {

        // Čuvanje podataka (zajedničko za oba dugmeta)
        iuf.kreirajAddActionListener(e -> sacuvajPodatke());
        iuf.izmeniAddActionListener(e -> sacuvajPodatke());

        // Dodavanje stavke u tabelu
        iuf.dodajSAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validacija unosa
                    if (iuf.getjTextFieldKolicina().getText().isEmpty()) {
                        JOptionPane.showMessageDialog(iuf, "Unesite količinu!");
                        return;
                    }

                    Sala sala = (Sala) iuf.getjComboBoxSalaStavke().getSelectedItem();
                    if (sala == null) {
                        JOptionPane.showMessageDialog(iuf, "Izaberite salu.");
                        return;
                    }

                    int kolicina = Integer.parseInt(iuf.getjTextFieldKolicina().getText().trim());
                    double cena = Double.parseDouble(iuf.getjTextFieldCena().getText().trim());
                    double iznos = cena * kolicina;
                    iuf.getjTextFieldIznos().setText(String.valueOf(iznos));

                    java.util.Date utilDatum = sdf.parse(iuf.getjTextFieldDatumStavke().getText().trim());
                    java.sql.Date sqlDatum = new java.sql.Date(utilDatum.getTime());

                    StavkaIznajmljivanja s = new StavkaIznajmljivanja();
                    s.setDatum(sqlDatum);
                    s.setSala(sala);
                    s.setKolicina(kolicina);
                    s.setCena(cena);
                    s.setIznosStavke(iznos);

                    ModelTabeleStavkeIznajmljivanja mts = (ModelTabeleStavkeIznajmljivanja) iuf.getjTableStavke().getModel();
                    s.setRb(mts.getLista().size() + 1);
                    mts.dodajStavku(s);

                    azurirajUkupnuCenuUgovora();
                    isprazniPoljaZaStavku();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(iuf, "Greška pri dodavanju stavke: " + ex.getMessage());
                }
            }
        });

        // Uklanjanje stavke iz tabele
        iuf.ukloniSAddActionListener(e -> {
            int red = iuf.getjTableStavke().getSelectedRow();
            if (red == -1) {
                JOptionPane.showMessageDialog(iuf, "Selektujte stavku u tabeli.");
                return;
            }
            ModelTabeleStavkeIznajmljivanja mts = (ModelTabeleStavkeIznajmljivanja) iuf.getjTableStavke().getModel();
            mts.obrisiStavku(red);
            azurirajUkupnuCenuUgovora();
        });

        // Automatsko postavljanje cene na osnovu sale
        iuf.getjComboBoxSalaStavke().addActionListener(e -> {
            Sala selektovanaSala = (Sala) iuf.getjComboBoxSalaStavke().getSelectedItem();
            if (selektovanaSala != null) {
                iuf.getjTextFieldCena().setText(String.valueOf(selektovanaSala.getCena()));
                iuf.getjTextFieldIznos().setText("");
            }
        });
    }

    private void popuniComboBoxeve() throws Exception {
        // Nacin placanja (Enum)
        iuf.getjComboBoxNacin().removeAllItems();
        for (NacinPlacanja n : NacinPlacanja.values()) {
            iuf.getjComboBoxNacin().addItem(n);
        }

        // Zaposleni
        iuf.getjComboBoxZaposleni().removeAllItems();
        for (Zaposleni z : Komunikacija.getInstance().ucitajZaposlene()) {
            iuf.getjComboBoxZaposleni().addItem(z);
        }

        // Zakupci
        iuf.getjComboBoxZakupac().removeAllItems();
        for (Zakupac z : Komunikacija.getInstance().ucitajZakupce(new Zakupac())) {
            iuf.getjComboBoxZakupac().addItem(z);
        }

        // Sale (Set se koristi da se ne bi ponavljale sale)
        iuf.getjComboBoxSalaStavke().removeAllItems();
        List<Ugovor> ugovori = Komunikacija.getInstance().ucitajUgovore(new Ugovor());
        Set<Sala> saleSet = new HashSet<>();
        for (Ugovor u : ugovori) {
            for (StavkaIznajmljivanja s : u.getStavke()) {
                saleSet.add(s.getSala());
            }
        }
        for (Sala s : saleSet) {
            iuf.getjComboBoxSalaStavke().addItem(s);
        }

        // Reset selekcije
        iuf.getjComboBoxNacin().setSelectedIndex(-1);
    //iuf.getjComboBoxZaposleni().setSelectedIndex(-1);
    iuf.getjComboBoxZakupac().setSelectedIndex(-1);
        iuf.getjComboBoxSalaStavke().setSelectedIndex(-1);
        iuf.getjTextFieldCena().setText("");
        iuf.getjTextFieldIznos().setText("");
    }
    private void azurirajUkupnuCenuUgovora() {
        ModelTabeleStavkeIznajmljivanja mts = (ModelTabeleStavkeIznajmljivanja) iuf.getjTableStavke().getModel();
        double ukupno = 0;
        for (StavkaIznajmljivanja s : mts.getLista()) {
            ukupno += s.getIznosStavke();
        }
        iuf.getjTextFieldUkupnaCena().setText(String.valueOf(ukupno));
    }

    private void isprazniPoljaZaStavku() {
        iuf.getjTextFieldDatumStavke().setText("");
        iuf.getjTextFieldKolicina().setText("");
        iuf.getjTextFieldCena().setText("");
        iuf.getjTextFieldIznos().setText("");
    }
    private void selektujZaposlenog(Zaposleni zIzUgovora) {
        if (zIzUgovora == null) return;

        for (int i = 0; i < iuf.getjComboBoxZaposleni().getItemCount(); i++) {
            Zaposleni zIzListe = (Zaposleni) iuf.getjComboBoxZaposleni().getItemAt(i);
            // Poredimo ID-jeve jer nemamo equals
            if (zIzListe != null && zIzListe.getIdZaposlen() == zIzUgovora.getIdZaposlen()) {
                iuf.getjComboBoxZaposleni().setSelectedIndex(i);
                break;
            }
        }
}

    private void sacuvajPodatke() {
        try {
            ModelTabeleStavkeIznajmljivanja mts = (ModelTabeleStavkeIznajmljivanja) iuf.getjTableStavke().getModel();

            // Sakupljanje podataka sa forme
            double ukupnaCena = Double.parseDouble(iuf.getjTextFieldUkupnaCena().getText());
            NacinPlacanja nacin = (NacinPlacanja) iuf.getjComboBoxNacin().getSelectedItem();
            java.util.Date utilDatum = sdf.parse(iuf.getjTextFieldDatum().getText());

            // Ažuriranje objekta
            ugovor.setDatumUgovora(new java.sql.Date(utilDatum.getTime()));
            ugovor.setUkupnaCena(ukupnaCena);
            ugovor.setNacinPlacanja(nacin);
            ugovor.setZaposleni((Zaposleni) iuf.getjComboBoxZaposleni().getSelectedItem());
            ugovor.setZakupac((Zakupac) iuf.getjComboBoxZakupac().getSelectedItem());

            List<StavkaIznajmljivanja> stavke = mts.getLista(); // Ovo je lista iz Modela Tabele
            for (StavkaIznajmljivanja s : stavke) {
                s.setUgovor(ugovor);
            }
            ugovor.setStavke(stavke);

            // Slanje na server
            Komunikacija.getInstance().izmeniUgovor(ugovor);

            JOptionPane.showMessageDialog(iuf, "Sistem je zapamtio ugovor.");
            iuf.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(iuf, "Sistem ne može da zapamti ugovor");
        }
    }

    }



