/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.Serializable;

/**
 *
 * @author Stasa
 */
public enum Operacija implements Serializable {
    LOGIN, 
    UCITAJ_ZAKUPCE, 
    OBRISI_ZAKUPCA, 
    DODAJ_ZAKUPCA, 
    IZMENI_ZAKUPCA,
    UBACI_TERMIN_ZAKUPA, 
    UCITAJ_TERMINE_ZAKUPA, 
    IZMENI_TERMIN_ZAKUPA,
    UCITAJ_UGOVORE, 
    UCITAJ_STAVKE, 
    UCITAJ_ZAPOSLENE, 
    IZMENI_UGOVOR, 
    KREIRAJ_UGOVOR, 
    UCITAJ_KATEGORIJE, 
    KREIRAJ_ZAKUPCA, 
    UCITAJ_SALE
}
