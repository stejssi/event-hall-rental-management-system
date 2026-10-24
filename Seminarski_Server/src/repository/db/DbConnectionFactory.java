/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Stasa
 */
public class DbConnectionFactory {  //Konekcija
    private static DbConnectionFactory instance;
    private Connection connection;
    
    private DbConnectionFactory(){
        try {
            if (connection==null || connection.isClosed()) {
                String url=konfiguracija.Konfiguracija.getInstance().getProperty("url");
                String username=konfiguracija.Konfiguracija.getInstance().getProperty("username");
                String password=konfiguracija.Konfiguracija.getInstance().getProperty("password");
                connection=DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);   
            }
            
        } catch (SQLException ex) {
            System.getLogger(DbConnectionFactory.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static DbConnectionFactory getInstance() {
        if (instance==null) {
           instance=new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    
    
}
