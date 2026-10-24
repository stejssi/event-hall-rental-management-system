/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stasa
 */
public class Sala implements ApstraktniDomenskiObjekat{
    private int idSale;
    private String naziv;
    private double cena;
    private String opis;

    public Sala() {
    }

    public Sala(int idSale, String naziv, double cena, String opis) {
        this.idSale = idSale;
        this.naziv = naziv;
        this.cena = cena;
        this.opis = opis;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String vratiNazivTabele() {
        return "sala";

    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("sala.idSale");
                String naz = rs.getString("sala.naziv");
                double c = rs.getDouble("sala.cena");
                String op = rs.getString("sala.opis");

                Sala s = new Sala(id, naz, c, op);
                lista.add(s);
            }
            return lista;    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv,cena,opis";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'"+naziv+"',"+cena+",'"+opis+"'";   //posto je cena int i pise se broj ne trebaju nam navodnici

    }

    @Override
    public String vratiPrimarniKljuc() {
        return "sala.idSale="+idSale;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naziv='"+naziv+"', cena="+cena+", opis='"+opis+"'";    }

    @Override
    public String vratiJoin() {
    return "";    
    }

    @Override
    public String vratiUslov(Object param) {
    return "";    
    }
    
    
}
