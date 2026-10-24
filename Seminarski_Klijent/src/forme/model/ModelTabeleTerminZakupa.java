/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.TerminZakupa;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stasa
 */
public class ModelTabeleTerminZakupa extends AbstractTableModel{
    List<TerminZakupa> lista;
    
    public ModelTabeleTerminZakupa(List<TerminZakupa> lista) {
        this.lista=lista;
    }

    String[] kolone={"ID","Vrsta zakupa","Status termina"};

    
    
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
        TerminZakupa z=lista.get(rowIndex);
        switch (columnIndex) {
            case 0:return z.getIdTerminZakupa();
            case 1:return z.getVrstaZakupa();
            case 2:return z.getStatusTermina();


            default:
                return "NA";
        }
    }

    public List<TerminZakupa> getLista() {
        return lista;
    }

  

}
