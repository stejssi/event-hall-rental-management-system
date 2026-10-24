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
import static forme.FormaMod.DODAJ;
import static forme.FormaMod.IZMENI;
import forme.PrikazUgovoraForma;
import forme.PrikazZakupacaForma;
import forme.model.ModelTabeleStavkeIznajmljivanja;
import forme.model.ModelTabeleUgovori;
import forme.model.ModelTabeleZakupci;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class PrikazUgovoraController {
    private final PrikazUgovoraForma puf;
    
    public PrikazUgovoraController(PrikazUgovoraForma puf) {
        this.puf = puf;
        addActionListener();
        addMouseListener();
        
    }

    

    public void otvoriFormu() {
        pripremiFormu();
        puf.setVisible(true);
    }

    public void pripremiFormu() {
        List<Ugovor> ugovori=Komunikacija.getInstance().ucitajUgovore(new Ugovor());
        System.out.println(ugovori);
        if (ugovori == null) {
        ugovori = new ArrayList<>();
    }

        ModelTabeleUgovori mtu=new ModelTabeleUgovori(ugovori);
        puf.getjTableUgovor().setModel(mtu);
        
        List<StavkaIznajmljivanja> stavke=new ArrayList<>();
        ModelTabeleStavkeIznajmljivanja mts=new ModelTabeleStavkeIznajmljivanja(stavke);
        puf.getjTableStavke().setModel(mts);
        
        popuniCmbBox();
        
    }
    public void popuniCmbBox() {

        // NACIN PLACANJA
        JComboBox cmbNacin = puf.getjComboBoxNacin();
        cmbNacin.removeAllItems();
        cmbNacin.addItem(null);
        
        for (NacinPlacanja n : NacinPlacanja.values()) {
            cmbNacin.addItem(n);
        }

        // ZAPOSLENI
        JComboBox cmbZaposleni = puf.getjComboBoxZaposleni();
        cmbZaposleni.removeAllItems();        
        cmbZaposleni.addItem(null);
        
        List<Zaposleni> zaposleni = Komunikacija.getInstance().ucitajZaposlene();
        System.out.println("Zaposleni: " + zaposleni);

        if (zaposleni != null) {
            for (Zaposleni z : zaposleni) {
                cmbZaposleni.addItem(z);
            }
        }

        // ZAKUPCI
        JComboBox cmbZakupac = puf.getjComboBoxZakupac();
        cmbZakupac.removeAllItems();
        cmbZakupac.addItem(null);
        List<Zakupac> zakupci = Komunikacija.getInstance().ucitajZakupce(new Zakupac());
        System.out.println("Zakupci: " + zakupci);

        if (zakupci != null) {
            for (Zakupac z : zakupci) {
                cmbZakupac.addItem(z);
            }
        }
        // Sale (Set se koristi da se ne bi ponavljale sale)
        JComboBox cmbSala=puf.getjComboBoxSala();
        cmbSala.removeAllItems();
        cmbSala.addItem(null);
        List<Ugovor> ugovori = Komunikacija.getInstance().ucitajUgovore(new Ugovor());
        Set<Sala> saleSet = new HashSet<>();
        for (Ugovor u : ugovori) {
            for (StavkaIznajmljivanja s : u.getStavke()) {
                saleSet.add(s.getSala());
            }
        }
        for (Sala s : saleSet) {
            puf.getjComboBoxSala().addItem(s);
        }
        
    }
    private void addActionListener() {
    puf.addBtnPretraziActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // 1. Prikupljanje podataka iz polja na formi
            String datumString = puf.getjTextFieldDatum().getText().trim();
            Date filterDatum = null;
            if (!datumString.isEmpty()) {
                filterDatum = new java.text.SimpleDateFormat("dd.MM.yyyy").parse(datumString);
            }

            String cenaTekst = puf.getjTextFieldUkupnaCena().getText().trim();
            Double cena;
            if (cenaTekst.isEmpty()) {
                cena = 0.0;
            } else {
                cena = Double.parseDouble(cenaTekst);
            }

            NacinPlacanja nacin = (NacinPlacanja) puf.getjComboBoxNacin().getSelectedItem();
            Zaposleni zaposleni = (Zaposleni) puf.getjComboBoxZaposleni().getSelectedItem();
            Zakupac zakupac = (Zakupac) puf.getjComboBoxZakupac().getSelectedItem();
            Sala sala=(Sala) puf.getjComboBoxSala().getSelectedItem();

            // 2. Kreiranje filter objekta (Ugovor koji nosi parametre pretrage)
            Ugovor filterUgovor = new Ugovor();
            filterUgovor.setDatumUgovora(filterDatum);
            filterUgovor.setUkupnaCena(cena);
            filterUgovor.setNacinPlacanja(nacin);
            filterUgovor.setZaposleni(zaposleni);
            filterUgovor.setZakupac(zakupac);
            if (sala != null) {
                // stavka koja sluzi samo da prenese salu do servera
                StavkaIznajmljivanja pomocnaStavka = new StavkaIznajmljivanja();
                pomocnaStavka.setSala(sala);

                List<StavkaIznajmljivanja> listaZaFilter = new ArrayList<>();
                listaZaFilter.add(pomocnaStavka);

                filterUgovor.setStavke(listaZaFilter);
            }
            

            //POZIV OPERACIJE NA SERVERU
            // Šaljemo filterUgovor, a PrikazUgovoraSO će na osnovu njega napraviti WHERE uslov
            List<Ugovor> filtriraniUgovori = Komunikacija.getInstance().ucitajUgovore(filterUgovor);
            // Osvežavanje tabele sa novim podacima
            if (filtriraniUgovori == null || filtriraniUgovori.isEmpty()) {
                JOptionPane.showMessageDialog(puf, "Sistem ne može da nađe ugovore po zadatim vrednostima.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
                puf.getjTableUgovor().setModel(new ModelTabeleUgovori(new ArrayList<>())); //praznimo tabelu
            } else {
                // Postavljamo novu listu u tabelu
                ModelTabeleUgovori mtu = new ModelTabeleUgovori(filtriraniUgovori);
                puf.getjTableUgovor().setModel(mtu);
                // Provera da li je barem jedan kriterijum popunjen
                if (!datumString.isEmpty() || !cenaTekst.isEmpty() || nacin != null || zaposleni != null || zakupac != null || sala != null) {
                    JOptionPane.showMessageDialog(puf, "Sistem je našao ugovore po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                }
                 else {
                // Ako su sva polja prazna (niko ništa nije uneo)
                JOptionPane.showMessageDialog(puf, "Sistem ne može da nađe ugovore po zadatim kriterijumima.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(puf, "Sistem ne može da nađe ugovore po zadatim kriterijumima.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
});
        puf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                
                
            }
        });
        
            puf.addBtnIzmeniActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 1. Proverimo koji je red selektovan
            int red = puf.getjTableUgovor().getSelectedRow();

            // SCENARIO 8.1: Ako niko nije selektovao red, sistem "ne može da nađe" ugovor za obradu
            if (red == -1) {
                JOptionPane.showMessageDialog(puf, "Sistem ne može da nađe ugovor.", "Greška", JOptionPane.ERROR_MESSAGE);
                return; // Prekida se izvršenje scenarija (IA)
            }

            //
            ModelTabeleUgovori mtu = (ModelTabeleUgovori) puf.getjTableUgovor().getModel();                   
            Ugovor selektovaniUgovor = mtu.getLista().get(red);//Uzmemo taj ugovor iz modela tabele

            
            JOptionPane.showMessageDialog(puf, "Sistem je našao ugovor.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

            Kordinator.getInstance().dodajParam("ugovor", selektovaniUgovor);

            try {
                Kordinator.getInstance().otvoriIzmeniUgovorFormu(IZMENI);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
        
        puf.addBtnKreirajActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                Ugovor u = new Ugovor();
                //moramo imati zaposlenog i zakupca zbog baze
                Zaposleni z = (Zaposleni) puf.getjComboBoxZaposleni().getItemAt(1);
                Zakupac zak = (Zakupac) puf.getjComboBoxZakupac().getItemAt(1);

                // provera
                if (z == null || zak == null) {
                    JOptionPane.showMessageDialog(puf, "Sistem ne može da kreira ugovor");
                    return;
                }
                u.setZaposleni(z); 
                u.setZakupac(zak);
                u.setDatumUgovora(new Date());
                u.setUkupnaCena(0.0);
                u.setNacinPlacanja(NacinPlacanja.GOTOVINA); 
                // SISTEM KREIRA UGOVOR (SO)
                Ugovor kreiran = Komunikacija.getInstance().kreirajUgovor(u);

                // SISTEM PRIKAZUJE PORUKU             
                pripremiFormu();
                JOptionPane.showMessageDialog(puf, "Sistem je kreirao ugovor");
                // Otvaramo formu za unos podataka 
                Kordinator.getInstance().dodajParam("ugovor", kreiran);
                Kordinator.getInstance().otvoriIzmeniUgovorFormu(DODAJ);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(puf, "Sistem ne može da kreira ugovor.");
                ex.printStackTrace(); //
            }
        }
    });
}

    private void addMouseListener() {
    puf.getjTableUgovor().addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int red = puf.getjTableUgovor().getSelectedRow();
            if (red != -1) {
                // 1. Dobavljanje ugovora iz modela
                ModelTabeleUgovori mtu = (ModelTabeleUgovori) puf.getjTableUgovor().getModel();
                Ugovor ugovor = mtu.getLista().get(red);

                // 2. Osvežavanje tabele stavki (na svaki klik)
                puf.getjTableStavke().setModel(new ModelTabeleStavkeIznajmljivanja(ugovor.getStavke()));

                // 3. Provera za dupli klik
                if (e.getClickCount() == 2) {
                    // Poruka o uspehu pre otvaranja forme
                    JOptionPane.showMessageDialog(puf, "Sistem je našao ugovor.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    // Slanje ugovora u Koordinator i otvaranje forme
                    Kordinator.getInstance().dodajParam("ugovor", ugovor);
                    try {
                        Kordinator.getInstance().otvoriIzmeniUgovorFormu(FormaMod.PREGLEDAJ);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    });
}
}
