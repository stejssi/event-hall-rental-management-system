/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Stasa
 */
public class StavkaIznajmljivanja implements ApstraktniDomenskiObjekat{
    private Ugovor ugovor; 
    private int rb;        
    private Date datum;
    private int kolicina;
    private double cena;
    private double iznosStavke;
    private Sala sala;

    public StavkaIznajmljivanja() {
    }

    public StavkaIznajmljivanja(Ugovor ugovor, int rb, Date datum, int kolicina, double cena, double iznosStavke, Sala sala) {
        this.ugovor = ugovor;
        this.rb = rb;
        this.datum = datum;
        this.kolicina = kolicina;
        this.cena = cena;
        this.iznosStavke = iznosStavke;
        this.sala = sala;
    }

    public Ugovor getUgovor() {
        return ugovor;
    }

    public void setUgovor(Ugovor ugovor) {
        this.ugovor = ugovor;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getIznosStavke() {
        return iznosStavke;
    }

    public void setIznosStavke(double iznosStavke) {
        this.iznosStavke = iznosStavke;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StavkaIznajmljivanja other = (StavkaIznajmljivanja) obj;
        if (this.rb != other.rb) {
            return false;
        }
        return Objects.equals(this.ugovor, other.ugovor);
    }

    @Override
    public String toString() {
        return "StavkaIznajmljivanja{" + "ugovor=" + ugovor + ", rb=" + rb + ", datum=" + datum + ", kolicina=" + kolicina + ", cena=" + cena + ", iznosStavke=" + iznosStavke + ", sala=" + sala + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "stavkaiznajmljivanja";

    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            //novi prazan objekat
            StavkaIznajmljivanja stavka = new StavkaIznajmljivanja();

            Ugovor u = new Ugovor();
            u.setIdUgovor(rs.getInt("stavkaiznajmljivanja.ugovor"));    //jer stavka ne može postojati bez veze sa ugovorom        
            stavka.setUgovor(u);

            stavka.setRb(rs.getInt("stavkaiznajmljivanja.rb"));
            stavka.setDatum(rs.getDate("stavkaiznajmljivanja.datum"));
            stavka.setKolicina(rs.getInt("stavkaiznajmljivanja.kolicina"));
            stavka.setCena(rs.getDouble("stavkaiznajmljivanja.cena"));
            stavka.setIznosStavke(rs.getDouble("stavkaiznajmljivanja.iznosStavke"));

            Sala s = new Sala();
            s.setIdSale(rs.getInt("idSale"));
            s.setNaziv(rs.getString("naziv"));

            stavka.setSala(s);
            lista.add(stavka);
        }

            return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ugovor,rb,datum,kolicina,cena,iznosStavke,sala";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return ugovor.getIdUgovor()+","+rb+",'"+datum+"',"+kolicina+","+cena+","+iznosStavke+","+sala.getIdSale();
    }

    @Override
    public String vratiPrimarniKljuc() {
        //primarni ključ se sastoji od ID-ja ugovora i rednog broja stavke.
        return "stavkaiznajmljivanja.ugovor=" + ugovor.getIdUgovor() + " AND stavkaiznajmljivanja.rb=" + rb;  
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "datum='"+datum+"', kolicina="+kolicina+", cena="+cena+", iznosStavke="+iznosStavke+", sala="+sala.getIdSale();
    }
    
//    @Override
//    public String vratiJoin() {
//        return "JOIN ugovor ON stavkaiznajmljivanja.ugovor = ugovor.idUgovor "
//             + "JOIN sala ON stavkaiznajmljivanja.sala = sala.idSale ";
//    }

    @Override
    public String vratiJoin() {
    return "";    
    }

    @Override
    public String vratiUslov(Object param) {
    return "";    
    }
}
