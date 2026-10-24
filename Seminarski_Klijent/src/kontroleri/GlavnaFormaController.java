/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Zaposleni;
import forme.GlavnaForma;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class GlavnaFormaController {
    private final GlavnaForma gf;
    
     public GlavnaFormaController(GlavnaForma gf) {
        this.gf = gf;
        addActionListener();
        
    }

    private void addActionListener() {
//
    }

    public void otvoriFormu() {
        Zaposleni ulogovani=Kordinator.getInstance().getUlogovani();
        String imePrezime=ulogovani.getIme()+" "+ulogovani.getPrezime();
        gf.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Glavna ide preko celog ekrana
        gf.setVisible(true);
        gf.getjLabelUlogovan().setText(imePrezime);
    }
}
