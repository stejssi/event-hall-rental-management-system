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
public class Zakupac implements ApstraktniDomenskiObjekat{
    private int idZakupac;
    private String nazivZakupac;
    private String emailZakupca;
    private String telefon;
    private String adresa;
    private KategorijaZakupca kategorijaZakupca;

    public Zakupac() {
    }

    public Zakupac(int idZakupac, String nazivZakupac, String emailZakupca, String telefon, String adresa, KategorijaZakupca kategorijaZakupca) {
        this.idZakupac = idZakupac;
        this.nazivZakupac = nazivZakupac;
        this.emailZakupca = emailZakupca;
        this.telefon = telefon;
        this.adresa = adresa;
        this.kategorijaZakupca = kategorijaZakupca;
    }

    public Zakupac(int idZakupac, String nazivZakupac) {
        this.idZakupac = idZakupac;
        this.nazivZakupac = nazivZakupac;
    }

    public int getIdZakupac() {
        return idZakupac;
    }

    public void setIdZakupac(int idZakupac) {
        this.idZakupac = idZakupac;
    }

    public String getNazivZakupac() {
        return nazivZakupac;
    }

    public void setNazivZakupac(String nazivZakupac) {
        this.nazivZakupac = nazivZakupac;
    }

    public String getEmailZakupca() {
        return emailZakupca;
    }

    public void setEmailZakupca(String emailZakupca) {
        this.emailZakupca = emailZakupca;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public KategorijaZakupca getKategorijaZakupca() {
        return kategorijaZakupca;
    }

    public void setKategorijaZakupca(KategorijaZakupca kategorijaZakupca) {
        this.kategorijaZakupca = kategorijaZakupca;
    }

    @Override
    public String toString() {
        return nazivZakupac;
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
        final Zakupac other = (Zakupac) obj;
        return this.idZakupac == other.idZakupac;
    }

    
    @Override
    public String vratiNazivTabele() {
        return "zakupac";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Zakupac z = new Zakupac();
            z.setIdZakupac(rs.getInt("zakupac.idZakupac"));
            z.setNazivZakupac(rs.getString("zakupac.nazivZakupac"));
            z.setEmailZakupca(rs.getString("zakupac.emailZakupca"));
            z.setTelefon(rs.getString("zakupac.telefon"));
            z.setAdresa(rs.getString("zakupac.adresa"));

            // Kreiramo objekat KategorijaZakupca
            KategorijaZakupca kz = new KategorijaZakupca();
            kz.setIdKategorijaZakupca(rs.getInt("kategorijazakupca.idKategorijaZakupca"));
            kz.setNaziv(rs.getString("kategorijazakupca.naziv"));
            kz.setOpis(rs.getString("kategorijazakupca.opis"));

            // zbog enuma
            String tipString = rs.getString("kategorijazakupca.tipZakupca");
            if (tipString != null) {
                kz.setTipZakupca(TipZakupca.valueOf(tipString));
            }

            // Povezujemo zakupca sa popunjenom kategorijom
            z.setKategorijaZakupca(kz);

            lista.add(z);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "nazivZakupac,emailZakupca,telefon,adresa,kategorijaZakupca";    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'"+nazivZakupac+"','"+emailZakupca+"','"+telefon+"','"+adresa+"',"+kategorijaZakupca.getIdKategorijaZakupca();    
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zakupac.idZakupac=" + idZakupac;    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public String vratiVrednostiZaIzmenu() {
        int katId;
    
    // Proveravamo da li je objekat kategorijaZakupca null pre nego što pozovemo njegov metod
    if (this.kategorijaZakupca != null) {
        katId = this.kategorijaZakupca.getIdKategorijaZakupca();
    } else {
        // Ako je null, dodeljujemo ID 1 koji odgovara podrazumevanoj kategoriji u bazi
        katId = 1; 
    }

    return "nazivZakupac='" + nazivZakupac + "', emailZakupca='" + emailZakupca + 
           "', telefon='" + telefon + "', adresa='" + adresa + 
           "', kategorijaZakupca=" + katId;    
    }
    
    public void inicijalizujPraznogZakupca() {

        nazivZakupac = "";
        emailZakupca = "";
        telefon = "";
        adresa = "";

        kategorijaZakupca = new KategorijaZakupca();
        kategorijaZakupca.setIdKategorijaZakupca(1);
}
    
    @Override
    public String vratiJoin() {
        return " JOIN kategorijazakupca ON zakupac.kategorijaZakupca = kategorijazakupca.idKategorijaZakupca ";
    }


    @Override
    public String vratiUslov(Object param) {
        Zakupac z = (Zakupac) param;
        String uslov = " WHERE 1=1 ";

        if (z.getNazivZakupac() != null && !z.getNazivZakupac().isEmpty()) {
            // Koristimo LIKE '%...%' da bismo mogli da trazimo i delove naziva
            uslov += " AND zakupac.nazivZakupac LIKE '%" + z.getNazivZakupac() + "%' ";
        }

        if (z.getEmailZakupca() != null && !z.getEmailZakupca().isEmpty()) {
            uslov += " AND zakupac.emailZakupca LIKE '%" + z.getEmailZakupca() + "%' ";
        }

        if (z.getTelefon() != null && !z.getTelefon().isEmpty()) {
            uslov += " AND zakupac.telefon LIKE '%" + z.getTelefon() + "%' ";
        }

        if (z.getAdresa() != null && !z.getAdresa().isEmpty()) {
            uslov += " AND zakupac.adresa LIKE '%" + z.getAdresa() + "%' ";
        }

        if (z.getKategorijaZakupca() != null) {
            uslov += " AND zakupac.kategorijaZakupca = " + z.getKategorijaZakupca().getIdKategorijaZakupca();
        }

        return uslov;
    }

}
