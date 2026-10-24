/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import com.sun.java.accessibility.util.AWTEventMonitor;
import domen.StatusTermina;
import domen.TerminZakupa;
import forme.IzmeniTerminZakupaForma;
import forme.PrikazTerminaZakupaForma;
import forme.model.ModelTabeleTerminZakupa;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class PrikazTerminaZakupaController {
    private final PrikazTerminaZakupaForma ptzf;

    public PrikazTerminaZakupaController(PrikazTerminaZakupaForma ptzf) {
        this.ptzf = ptzf;
        addActionListener();
    }
    
    public void otvoriFormu() {
        pripremiFormu();
        ptzf.setVisible(true);
    }
    public void pripremiFormu() {
        popuniCmbBox();
        try {
            // ucitavanje termina iz baze
            List<TerminZakupa> lista =Komunikacija.getInstance().ucitajTermine();
            ModelTabeleTerminZakupa model =new ModelTabeleTerminZakupa(lista);
            ptzf.getjTableTerminZakupa().setModel(model);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ptzf,"Sistem ne moze da ucita termine");
        }
    }
        public void popuniCmbBox() {
        JComboBox cmb = ptzf.getjComboBoxStatus();
        cmb.removeAllItems();
        
        cmb.addItem(null);   // prazna opcija-prva

        for (StatusTermina s : StatusTermina.values()) {
            cmb.addItem(s);
        }
    }
    private void addActionListener() {
        ptzf.addBtnUbaciActionListener(e -> {

            try {
                String vrsta = ptzf.getjTextFieldVrsta().getText();
                StatusTermina status =(StatusTermina) ptzf.getjComboBoxStatus().getSelectedItem();
                // VALIDACIJA
                if (vrsta.isEmpty()) {JOptionPane.showMessageDialog(ptzf,"Sistem ne moze da zapamti termin zakupa.","GRESKA",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (status == null) {JOptionPane.showMessageDialog(ptzf,"Sistem ne moze da zapamti termin zakupa.","GRESKA",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TerminZakupa t = new TerminZakupa();
                t.setVrstaZakupa(vrsta);
                t.setStatusTermina(status);

                Komunikacija.getInstance().dodajTerminZakupa(t);
                JOptionPane.showMessageDialog(ptzf,"Sistem je zapamtio termin zakupa.","USPEH",JOptionPane.INFORMATION_MESSAGE);
                ptzf.getjTextFieldVrsta().setText("");          // Čisti tekstualno polje
                ptzf.getjComboBoxStatus().setSelectedIndex(0);   // Vraća ComboBox na null (prvu opciju)
                pripremiFormu();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(ptzf,"Sistem ne moze da zapamti termin zakupa.","GRESKA",JOptionPane.ERROR_MESSAGE);
            }

        });
        ptzf.addBtnIzmeniActionListener(e -> {
            int red = ptzf.getjTableTerminZakupa().getSelectedRow();

            if (red == -1) {
                JOptionPane.showMessageDialog(ptzf, "Sistem ne moze da azurira termin zakupa. Niste selektovali red.", "GRESKA", JOptionPane.ERROR_MESSAGE);
            } else {
                ModelTabeleTerminZakupa mttz = (ModelTabeleTerminZakupa) ptzf.getjTableTerminZakupa().getModel();
                TerminZakupa t = mttz.getLista().get(red);                
                Kordinator.getInstance().dodajParam("termin", t);
                Kordinator.getInstance().otvoriIzmeniTerminZakupaFormu();

            }
        });
    }    



}

    
    
    

