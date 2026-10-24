/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
/**
 *
 * @author Stasa
 */
public class Ugovor implements ApstraktniDomenskiObjekat{
    private int idUgovor;
    private Date datumUgovora;
    private double ukupnaCena;
    private NacinPlacanja nacinPlacanja;    
    private Zaposleni zaposleni; 
    private Zakupac zakupac;
    private List <StavkaIznajmljivanja> stavke=new ArrayList<>();

    public Ugovor() {
    }

    public Ugovor(int idUgovor, Date datumUgovora, double ukupnaCena, NacinPlacanja nacinPlacanja, Zaposleni zaposleni, Zakupac zakupac) {
        this.idUgovor = idUgovor;
        this.datumUgovora = datumUgovora;
        this.ukupnaCena = ukupnaCena;
        this.nacinPlacanja = nacinPlacanja;
        this.zaposleni = zaposleni;
        this.zakupac = zakupac;
    }

    public int getIdUgovor() {
        return idUgovor;
    }

    public void setIdUgovor(int idUgovor) {
        this.idUgovor = idUgovor;
    }

    public Date getDatumUgovora() {
        return datumUgovora;
    }

    public void setDatumUgovora(Date datumUgovora) {
        this.datumUgovora = datumUgovora;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public NacinPlacanja getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(NacinPlacanja nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public Zakupac getZakupac() {
        return zakupac;
    }

    public void setZakupac(Zakupac zakupac) {
        this.zakupac = zakupac;
    }

    public List<StavkaIznajmljivanja> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaIznajmljivanja> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return "Ugovor{" + "idUgovor=" + idUgovor + ", datumUgovora=" + datumUgovora + ", ukupnaCena=" + ukupnaCena + ", nacinPlacanja=" + nacinPlacanja + ", zaposleni=" + zaposleni + ", zakupac=" + zakupac + ", stavke=" + stavke + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "ugovor";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
            List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {

            Ugovor u = new Ugovor();
            u.setIdUgovor(rs.getInt("ugovor.idUgovor"));
            u.setDatumUgovora(rs.getDate("ugovor.datumUgovora"));
            u.setUkupnaCena(rs.getDouble("ugovor.ukupnaCena"));
            u.setNacinPlacanja(NacinPlacanja.valueOf(rs.getString("ugovor.nacinPlacanja")));

            Zaposleni z = new Zaposleni();
            z.setIdZaposlen(rs.getInt("zaposleni.idZaposlen"));
            z.setIme(rs.getString("zaposleni.ime"));
            z.setPrezime(rs.getString("zaposleni.prezime"));

            u.setZaposleni(z);

            Zakupac za = new Zakupac();
            za.setIdZakupac(rs.getInt("zakupac.idZakupac"));
            za.setNazivZakupac(rs.getString("zakupac.nazivZakupac"));

            u.setZakupac(za);
            lista.add(u);
        }

        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "datumUgovora,ukupnaCena,nacinPlacanja,zaposleni,zakupac";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return "'" + sdf.format(datumUgovora) + "'," +
                ukupnaCena + ",'" +
                nacinPlacanja + "'," +
                zaposleni.getIdZaposlen() + "," +
                zakupac.getIdZakupac();
    }
    @Override
    public String vratiPrimarniKljuc() {
        return "ugovor.idUgovor="+idUgovor;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    return "datumUgovora='" + sdf.format(datumUgovora) + "', "
            + "ukupnaCena=" + ukupnaCena + ", "
            + "nacinPlacanja='" + nacinPlacanja + "', "
            + "zaposleni=" + zaposleni.getIdZaposlen() + ", "
            + "zakupac=" + zakupac.getIdZakupac();
}    
    @Override
    public String vratiUslov(Object param) {
        Ugovor u = (Ugovor) param;
        String uslov = " WHERE 1=1 "; // Početni uslov koji je uvek tačan

        if (u.getDatumUgovora() != null) {
            // Pretvaramo običan datum u SQL format (yyyy-mm-dd)
            java.sql.Date sqlDatum = new java.sql.Date(u.getDatumUgovora().getTime());
            uslov += " AND ugovor.datumUgovora = '" + sqlDatum + "' ";
        }

        if (u.getUkupnaCena() > 0) {
            uslov += " AND ugovor.ukupnaCena <= " + u.getUkupnaCena();
        }

        if (u.getNacinPlacanja() != null) {
            uslov += " AND ugovor.nacinPlacanja = '" + u.getNacinPlacanja() + "' ";
        }

        if (u.getZaposleni() != null) {
            uslov += " AND ugovor.zaposleni = " + u.getZaposleni().getIdZaposlen();
        }

        if (u.getZakupac() != null) {
            uslov += " AND ugovor.zakupac = " + u.getZakupac().getIdZakupac();
        }
        if (this.getStavke() != null && !this.getStavke().isEmpty()) {
            Sala s = this.getStavke().get(0).getSala();
            if (s != null) {
                // 'ugovor' je naziv kolone u bazi, a 'idUgovor' je PK u tabeli ugovor
                uslov += " AND ugovor.idUgovor IN (SELECT ugovor FROM stavkaiznajmljivanja WHERE sala = " + s.getIdSale() + ") ";
            }
        }
        uslov += " ORDER BY ugovor.idUgovor ASC";
        return uslov;
}
    @Override
    public String vratiJoin() {
            return " JOIN zaposleni ON ugovor.zaposleni = zaposleni.idZaposlen "
             + " JOIN zakupac ON ugovor.zakupac = zakupac.idZakupac ";
    }

}
