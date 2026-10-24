/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.StatusTermina;
import domen.TerminZakupa;
import forme.IzmeniTerminZakupaForma;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class IzmeniTerminZakupaController {
    private final IzmeniTerminZakupaForma itzf;
    private TerminZakupa termin; // termin koji menjamo

    public IzmeniTerminZakupaController(IzmeniTerminZakupaForma itzf, TerminZakupa termin) {
        this.itzf = itzf;
        this.termin = termin;
        popuniPolja();
        addActionListener();
    }

    private void popuniPolja() {
        itzf.getjTextFieldVrsta().setText(termin.getVrstaZakupa());
        itzf.getjComboBoxStatus().setSelectedItem(termin.getStatusTermina());
    }

    private void addActionListener() {
        itzf.getjButtonSacuvaj().addActionListener(e -> {
            try {
                termin.setVrstaZakupa(itzf.getjTextFieldVrsta().getText());
                termin.setStatusTermina((StatusTermina) itzf.getjComboBoxStatus().getSelectedItem());
                Komunikacija.getInstance().izmeniTerminZakupa(termin);
                JOptionPane.showMessageDialog(itzf, "Termin uspešno izmenjen.");
                Kordinator.getInstance().osveziTermine();
                itzf.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(itzf, "Greška pri izmeni termina: " + ex.getMessage());
            }
        });
    }

    public void otvoriFormu() {
        itzf.setVisible(true);
    }
}
