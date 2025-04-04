/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;



/**
 *
 * @author zzzdi
 */
import java.io.IOException;

public interface Functions {
    boolean addCar();
    void findCar();
    boolean updateCar();
    boolean deleteCar();
    boolean addInsuranceStatement();
    void listInsuranceStatements();
    void reportUninsuredCars();
    boolean saveData();
    void quitProgram() throws IOException, ClassNotFoundException;
}
