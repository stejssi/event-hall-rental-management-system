/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Zaposleni;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kordinator.Kordinator;

/**
 *
 * @author Stasa
 */
public class LoginController {
    private final LoginForma lf;

    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListener();
        
    }

    private void addActionListener() {
        
        lf.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijava(e);
            }

            private void prijava(ActionEvent e) {
                //System.out.println("Kliknuto na dugme!");
                String username=lf.getjTextFieldUsername().getText().trim();
                String pass=String.valueOf(lf.getjPasswordField1().getPassword());
                
                Komunikacija.getInstance().konekcija();
                Zaposleni ulogovani=Komunikacija.getInstance().login(username, pass);
                if (ulogovani==null) {
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra nisu ispravni.", "Greska", JOptionPane.ERROR_MESSAGE);
                    
                }else{
                    Kordinator.getInstance().setUlogovani(ulogovani);
                    try {
                        Kordinator.getInstance().setUlogovani(ulogovani);
                        JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra su ispravni.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                        // Pokušaj otvaranja glavne forme
                        Kordinator.getInstance().otvoriGlavnuFormu();
                        lf.dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni", "Greska", JOptionPane.ERROR_MESSAGE);                        ex.printStackTrace();
                    }

                }
                
            }
        });


    }

    public void otvoriFormu() {
        lf.setVisible(true);
    }
    
    
}
