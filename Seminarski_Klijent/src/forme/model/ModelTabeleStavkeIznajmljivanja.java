/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.StavkaIznajmljivanja;
import domen.Ugovor;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stasa
 */
public class ModelTabeleStavkeIznajmljivanja extends AbstractTableModel {
    List<StavkaIznajmljivanja> lista;
    String[] kolone={"rb","Datum","Kolicina","Cena","Iznos stavke","Sala"};

    public ModelTabeleStavkeIznajmljivanja(List<StavkaIznajmljivanja> lista) {
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
        StavkaIznajmljivanja s=lista.get(rowIndex);
        switch (columnIndex) {
            case 0:return s.getRb();
            case 1:return s.getDatum();
            case 2:return s.getKolicina();
            case 3:return s.getCena();
            case 4:return s.getIznosStavke();
            case 5:return s.getSala().getNaziv();

            default:
                return "NA";
        }
    }

    public List<StavkaIznajmljivanja> getLista() {
        return lista;
    }

    public void setLista(List<StavkaIznajmljivanja> lista) {
        this.lista = lista;
    }

    public void dodajStavku(StavkaIznajmljivanja s){
        lista.add(s);
        fireTableDataChanged();
    }

    public void obrisiStavku(int red){
        lista.remove(red);

        for(int i=0;i<lista.size();i++){
            lista.get(i).setRb(i+1);
        }

        fireTableDataChanged();
    }
}


    

