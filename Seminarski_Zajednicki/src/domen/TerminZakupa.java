package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Stasa
 */
public class TerminZakupa implements ApstraktniDomenskiObjekat{
    private int idTerminZakupa;
    private String vrstaZakupa;
    private StatusTermina statusTermina;

    public TerminZakupa() {
    }

    public TerminZakupa(int idTerminZakupa, String vrstaZakupa, StatusTermina statusTermina) {
        this.idTerminZakupa = idTerminZakupa;
        this.vrstaZakupa = vrstaZakupa;
        this.statusTermina = statusTermina;
    }

    public int getIdTerminZakupa() {
        return idTerminZakupa;
    }

    public void setIdTerminZakupa(int idTerminZakupa) {
        this.idTerminZakupa = idTerminZakupa;
    }

    public String getVrstaZakupa() {
        return vrstaZakupa;
    }

    public void setVrstaZakupa(String vrstaZakupa) {
        this.vrstaZakupa = vrstaZakupa;
    }

    public StatusTermina getStatusTermina() {
        return statusTermina;
    }

    public void setStatusTermina(StatusTermina statusTermina) {
        this.statusTermina = statusTermina;
    }

    @Override
    public String toString() {
        return "TerminZakupa{" + "idTerminZakupa=" + idTerminZakupa + ", vrstaZakupa=" + vrstaZakupa + ", statusTermina=" + statusTermina + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "terminzakupa";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("terminzakupa.idTerminZakupa");
                String vrsta = rs.getString("terminzakupa.vrstaZakupa");
                StatusTermina status = StatusTermina.valueOf(rs.getString("terminzakupa.statusTermina"));

                TerminZakupa tz = new TerminZakupa(id, vrsta, status);
                lista.add(tz);
            }
            return lista;    
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "vrstaZakupa,statusTermina";    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'"+vrstaZakupa+"','"+statusTermina+"'"; 
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "terminzakupa.idTerminZakupa=" + idTerminZakupa;    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "vrstaZakupa='"+vrstaZakupa+"', statusTermina='"+statusTermina+"'";    
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
