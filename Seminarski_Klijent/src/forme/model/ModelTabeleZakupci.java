/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.KategorijaZakupca;
import domen.Zakupac;
import forme.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stasa
 */
public class ModelTabeleZakupci extends AbstractTableModel {
    List<Zakupac> lista;
    String[] kolone={"IDZakupca","Naziv zakupca","Email","Telefon","Adresa","Kategorija zakupca"};

    public ModelTabeleZakupci(List<Zakupac> lista) {
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
        Zakupac z=lista.get(rowIndex);
        switch (columnIndex) {
            case 0:return z.getIdZakupac();
            case 1:return z.getNazivZakupac();
            case 2:return z.getEmailZakupca();
            case 3:return z.getTelefon();
            case 4:return z.getAdresa();
            case 5:
            // proveravamo null zbog praznog reda
            if (z.getKategorijaZakupca() != null) {
                return z.getKategorijaZakupca().getNaziv();
            } else {
                return ""; 
            }
            default:
                return "NA";
        }
    }

    public List<Zakupac> getLista() {
        return lista;
    }


    
}
