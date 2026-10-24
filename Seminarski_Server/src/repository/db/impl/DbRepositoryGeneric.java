/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domen.ApstraktniDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;


/**
 *
 * @author Stasa
 */
public class DbRepositoryGeneric implements DbRepository<ApstraktniDomenskiObjekat> {

    @Override
    public List<ApstraktniDomenskiObjekat> getAll(ApstraktniDomenskiObjekat param, String uslov) throws Exception {
        List<ApstraktniDomenskiObjekat> lista=new ArrayList<>();
       //select * from ______
       String upit="SELECT * FROM "+param.vratiNazivTabele();
        if (uslov!=null) {
          upit+=uslov;     //where id=3 celo to je uslov
        }
        System.out.println(upit);
        Statement st=DbConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs=st.executeQuery(upit);
        lista=param.vratiListu(rs);
        
        rs.close();
        st.close();
        
       return lista;
    }

    
        
     // Glavna add metoda koja vraća generisani ID 
    public int add(ApstraktniDomenskiObjekat param) throws Exception {
    String upit = "INSERT INTO " + param.vratiNazivTabele() +
                  "(" + param.vratiKoloneZaUbacivanje() + ") VALUES (" +
                  param.vratiVrednostiZaUbacivanje() + ")";

    System.out.println(upit);

    int generatedId = -1;
    
    // Uzimamo konekciju, ali je NE stavljamo u try-with-resources jer ne smemo da je zatvorimo
    Connection conn = DbConnectionFactory.getInstance().getConnection();
    
    // Samo Statement ide u try-with-resources
    try (Statement st = conn.createStatement()) {
        st.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
        
        try (ResultSet rs = st.getGeneratedKeys()) {
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
    }
    // Konekcija ostaje otvorena za sledeći poziv (stavke) i za commit/rollback
    return generatedId;
}

    @Override
    public void edit(ApstraktniDomenskiObjekat param) throws Exception {
        //UPDATE kategorijazakupca SET tipZakupca='PRAVNO_LICE', naziv='NVO',opis='Humanitarne org' WHERE idKategorijaZakupca=3
        //UPDATE _______ SET ______='______', _____='______', ________='_______' WHERE _________
        String upit="UPDATE "+param.vratiNazivTabele()+" SET "+param.vratiVrednostiZaIzmenu()+" WHERE "+param.vratiPrimarniKljuc();;
        
        System.out.println(upit);
        Statement st=DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        
        st.close();
    }

    @Override
    public void delete(ApstraktniDomenskiObjekat param) throws Exception {
        //DELETE FROM kategorijazakupca WHERE id=1
        String upit="DELETE FROM "+param.vratiNazivTabele()+" WHERE "+param.vratiPrimarniKljuc();
        
        System.out.println(upit);
        Statement st=DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        
        st.close();
    }

    @Override
    public List<ApstraktniDomenskiObjekat> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
      
    }

    

    
    
    
    
}
