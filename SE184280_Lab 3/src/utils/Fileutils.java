/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
/**
 *
 * @author zzzdi
 */

import java.io.*;
import java.util.HashMap;
import java.util.List;
import models.Car;
import models.InsuranceStatement;

public class Fileutils {
    public static HashMap<String, Car> readCarsFromFile(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (HashMap<String, Car>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        }
    }

    public static boolean writeCarsToFile(String path, HashMap<String, Car> cars) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(cars);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving cars: " + e.getMessage());
            return false;
        }
    }

    public static HashMap<String, InsuranceStatement> readInsurancesFromFile(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (HashMap<String, InsuranceStatement>) ois.readObject();
        } catch (EOFException e) {
            return new HashMap<>();
        }
    }

    public static boolean writeInsurancesToFile(String path, HashMap<String, InsuranceStatement> insurances) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(insurances);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving insurances: " + e.getMessage());
            return false;
        }
    }

    public static List<InsuranceStatement> readStatementsFromFile(String STATEMENTS_PATH) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}