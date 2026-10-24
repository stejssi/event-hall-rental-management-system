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
public class KategorijaZakupca implements ApstraktniDomenskiObjekat{
    private int idKategorijaZakupca;
    private TipZakupca tipZakupca;
    private String naziv;
    private String opis;

    public KategorijaZakupca() {
    }

    public KategorijaZakupca(int idKategorijaZakupca, TipZakupca tipZakupca, String naziv, String opis) {
        this.idKategorijaZakupca = idKategorijaZakupca;
        this.tipZakupca = tipZakupca;
        this.naziv = naziv;
        this.opis = opis;
    }

    public KategorijaZakupca(int idKategorijaZakupca, String naziv) {                
        this.idKategorijaZakupca = idKategorijaZakupca;
        this.naziv = naziv;

    }

    public int getIdKategorijaZakupca() {
        return idKategorijaZakupca;
    }

    public void setIdKategorijaZakupca(int idKategorijaZakupca) {
        this.idKategorijaZakupca = idKategorijaZakupca;
    }

    public TipZakupca getTipZakupca() {
        return tipZakupca;
    }

    public void setTipZakupca(TipZakupca tipZakupca) {
        this.tipZakupca = tipZakupca;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        KategorijaZakupca k = (KategorijaZakupca) obj;
        return idKategorijaZakupca == k.idKategorijaZakupca;
    }

    @Override
    public int hashCode() {
        return idKategorijaZakupca;
    }

    @Override
    public String vratiNazivTabele() {
        return "kategorijazakupca";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
       List<ApstraktniDomenskiObjekat> lista =new ArrayList<>();
        while (rs.next()) {
            int idKategorijaZakupca=rs.getInt("kategorijazakupca.idKategorijaZakupca");
            // Konverzija String vrednosti iz baze u Enum TipZakupca
            TipZakupca tip = TipZakupca.valueOf(rs.getString("kategorijazakupca.tipZakupca"));            
            String naziv=rs.getString("kategorijazakupca.naziv");
            String opis=rs.getString("kategorijazakupca.opis");
           KategorijaZakupca k=new KategorijaZakupca(idKategorijaZakupca, tip, naziv, opis);
           lista.add(k);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "tipZakupca,naziv,opis";     //INSERT INTO kategorijazakupca (____,____,____) VALUES ('PRAVNO_LICE','NVO','Humanitarne organizacije i udruženja')
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        //(      'PRAVNO_LICE','NVO','Humanitarne'       )    //INSERT INTO kategorijazakupca (tipZakupca,naziv,opis) VALUES ('______','_____','_____')
         return "'"+tipZakupca+"','"+naziv+"','"+opis+"'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        //SELECT * FROM kategorijazakupca WHERE kategorijazakupca.idKategorijaZakupca=1
        return "kategorijazakupca.idKategorijaZakupca="+idKategorijaZakupca;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        //UPDATE kategorijazakupca SET tipZakupca='PRAVNO_LICE', naziv='NVO',opis='Humanitarne org' WHERE idKategorijaZakupca=3
        return "tipZakupca='"+tipZakupca+"', naziv='"+naziv+"', opis='"+opis+"'";
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
