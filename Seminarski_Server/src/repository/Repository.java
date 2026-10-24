/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import domen.Zaposleni;
import java.util.List;

/**
 *
 * @author Stasa
 */
public interface Repository <T>{
    List<T> getAll(T param, String uslov) throws Exception;
    //void add(T param) throws Exception;
    int add(T param) throws Exception; // sada vraća ID
    void edit(T param) throws Exception;
    void delete(T param) throws Exception;
    List<T> getAll() throws Exception;

}
