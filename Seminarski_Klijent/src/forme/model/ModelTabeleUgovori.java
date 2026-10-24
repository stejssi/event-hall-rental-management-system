/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.NacinPlacanja;
import domen.StavkaIznajmljivanja;
import domen.Ugovor;
import domen.Zakupac;
import domen.Zaposleni;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stasa
 */
public class ModelTabeleUgovori extends AbstractTableModel {

    List<Ugovor> lista;
    String[] kolone={"IDUgovora","Datum ugovora","Ukupna cena","Nacin placanja","Zaposleni","Zakupac", "Sale"};

    public ModelTabeleUgovori(List<Ugovor> lista) {
        this.lista = lista;
    }
    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ugovor u=lista.get(rowIndex);
        switch (columnIndex) {
            case 0:return u.getIdUgovor();
            case 1:return u.getDatumUgovora();
            case 2:return u.getUkupnaCena();
            case 3:return u.getNacinPlacanja();
            case 4:return u.getZaposleni().getIme()+" "+u.getZaposleni().getPrezime();
            case 5:return u.getZakupac().getNazivZakupac();
            case 6: 
            List<StavkaIznajmljivanja> stavke = u.getStavke();
            if (stavke == null || stavke.isEmpty()) return "";

            // sve sale koje se pojavljuju u tom ugovoru
            Set<String> naziviSala = new HashSet<>();
            for(StavkaIznajmljivanja s : stavke) {
                if(s.getSala() != null) naziviSala.add(s.getSala().getNaziv());
            }
            return String.join(", ", naziviSala);
            default:
                return "NA";
        }
    }

    public List<Ugovor> getLista() {
        return lista;
    }

    public void setLista(List<Ugovor> lista) {
        this.lista = lista;
    }

    



}

    
    
    

