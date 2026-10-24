/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Stasa
 */
public class Konfiguracija {
    private static Konfiguracija instance;
    private Properties konfiguracija;
    
    private Konfiguracija(){
        konfiguracija=new Properties();
       
            try {
                konfiguracija.load(new FileInputStream("config\\config.properties"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                System.getLogger(Konfiguracija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.getLogger(Konfiguracija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
    }

    public static Konfiguracija getInstance() {
        if (instance==null) {
            instance=new Konfiguracija();
        }
        return instance;
    }

    public String getProperty(String key) {
        return konfiguracija.getProperty(key, "n/a");
    }

    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }
    
    public void sacuvajIzmene(){
        try {
            konfiguracija.store(new FileOutputStream("config\\config.properties"), null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.getLogger(Konfiguracija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.getLogger(Konfiguracija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
}
