/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Stasa
 */
public class ZaposleniTermin implements ApstraktniDomenskiObjekat{
    private Zaposleni zaposleni;
    private TerminZakupa terminZakupa;
    private Date datumOd;
    private Date datumDo;
    private String napomena;

    public ZaposleniTermin() {
    }

    public ZaposleniTermin(Zaposleni zaposleni, TerminZakupa terminZakupa, Date datumOd, Date datumDo, String napomena) {
        this.zaposleni = zaposleni;
        this.terminZakupa = terminZakupa;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.napomena = napomena;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public TerminZakupa getTerminZakupa() {
        return terminZakupa;
    }

    public void setTerminZakupa(TerminZakupa terminZakupa) {
        this.terminZakupa = terminZakupa;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @Override
    public String toString() {
        return "ZaposleniTermin{" + "zaposleni=" + zaposleni + ", terminZakupa=" + terminZakupa + ", datumOd=" + datumOd + ", datumDo=" + datumDo + ", napomena=" + napomena + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "zaposlenitermin";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

            while (rs.next()) {

                ZaposleniTermin zt = new ZaposleniTermin();

                Zaposleni z = new Zaposleni();
                z.setIdZaposlen(rs.getInt("zaposleni.idZaposlen"));
                z.setIme(rs.getString("zaposleni.ime"));
                z.setPrezime(rs.getString("zaposleni.prezime"));

                zt.setZaposleni(z);

                TerminZakupa tz = new TerminZakupa();
                tz.setIdTerminZakupa(rs.getInt("terminzakupa.idTerminZakupa"));
                tz.setStatusTermina(StatusTermina.valueOf(rs.getString("terminzakupa.statusTermina")));
                zt.setTerminZakupa(tz);

                zt.setDatumOd(rs.getDate("zaposlenitermin.datumOd"));
                zt.setDatumDo(rs.getDate("zaposlenitermin.datumDo"));
                zt.setNapomena(rs.getString("zaposlenitermin.napomena"));

                lista.add(zt);
            }

            return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "zaposleni,terminZakupa,datumOd,datumDo,napomena";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return zaposleni.getIdZaposlen()+","+terminZakupa.getIdTerminZakupa()+",'"+datumOd+"','"+datumDo+"','"+napomena+"'";    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zaposlenitermin.zaaposleni=" + zaposleni.getIdZaposlen() + " AND zaposlenitermin.terminZakupa=" + terminZakupa.getIdTerminZakupa();    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "datumOd='"+datumOd+"', datumDo='"+datumDo+"', napomena='"+napomena+"'";
    }
    
//    @Override
//    public String vratiJoin() {
//        return "JOIN zaposleni ON zaposlenitermin.zaposleni = zaposleni.idZaposlen "
//             + "JOIN terminzakupa ON zaposlenitermin.terminZakupa = terminzakupa.idTerminZakupa ";
//    }
//    

    @Override
    public String vratiJoin() {
    return "";    
    }

    @Override
    public String vratiUslov(Object param) {
    return "";    
    }
}
