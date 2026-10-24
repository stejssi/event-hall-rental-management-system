/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Stasa
 */
public class Zaposleni implements ApstraktniDomenskiObjekat{
    private int idZaposlen;
    private String ime;
    private String prezime;
    private String emailAdresa;
    private String korisnickiNalog;
    private String lozinka;

    public Zaposleni() {
    }

    public Zaposleni(int idZaposlen, String ime, String prezime, String emailAdresa, String korisnickiNalog, String lozinka) {
        this.idZaposlen = idZaposlen;
        this.ime = ime;
        this.prezime = prezime;
        this.emailAdresa = emailAdresa;
        this.korisnickiNalog = korisnickiNalog;
        this.lozinka = lozinka;
    }

    public Zaposleni(int idZaposlen, String ime, String prezime) {
        this.idZaposlen = idZaposlen;
        this.ime = ime;
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    public int getIdZaposlen() {
        return idZaposlen;
    }

    public void setIdZaposlen(int idZaposlen) {
        this.idZaposlen = idZaposlen;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public String getKorisnickiNalog() {
        return korisnickiNalog;
    }

    public void setKorisnickiNalog(String korisnickiNalog) {
        this.korisnickiNalog = korisnickiNalog;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Zaposleni other = (Zaposleni) obj;
        if (!Objects.equals(this.korisnickiNalog, other.korisnickiNalog)) {
            return false;
        }
        return Objects.equals(this.lozinka, other.lozinka);
    }

    

    
    
    @Override
    public String vratiNazivTabele() {
        return "zaposleni";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("zaposleni.idZaposlen");
                String ime = rs.getString("zaposleni.ime");
                String prezime = rs.getString("zaposleni.prezime");
                String email = rs.getString("zaposleni.emailAdresa");
                String nalog = rs.getString("zaposleni.korisnickiNalog");
                String loz = rs.getString("zaposleni.lozinka");

                Zaposleni z = new Zaposleni(id, ime, prezime, email, nalog, loz);
                lista.add(z);
            }
            return lista;    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,emailAdresa,korisnickiNalog,lozinka";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'"+ime+"','"+prezime+"','"+emailAdresa+"','"+korisnickiNalog+"','"+lozinka+"'";    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zaposleni.idZaposlen="+idZaposlen;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='"+ime+"', prezime='"+prezime+"', emailAdresa='"+emailAdresa+"', korisnickiNalog='"+korisnickiNalog+"', lozinka='"+lozinka+"'";    
    }

    @Override
    public String vratiJoin() {
    return "";    
    }

    @Override
    public String vratiUslov(Object param) {
    return "";    
    }
    
    
}
