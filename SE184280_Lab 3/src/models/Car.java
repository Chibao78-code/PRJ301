/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author zzzdi
 */
public class Car implements Serializable{
   private String licensePlate;
    private String carOwner;
    private String phoneNumber;
    private String carBrand;
    private long valueOfVehicle;
    private int numberOfSeats;
    private Date registrationDate;
    private String placeOfRegistration;

    public Car() {
    }

    public Car(String licensePlate, String carOwner, String phoneNumber, String carBrand, int valueOfVehicle, int numberOfSeats, Date registrationDate, String placeOfRegistration) {
        this.licensePlate = licensePlate;
        this.carOwner = carOwner;
        this.phoneNumber = phoneNumber;
        this.carBrand = carBrand;
        this.valueOfVehicle = valueOfVehicle;
        this.numberOfSeats = numberOfSeats;
        this.registrationDate = registrationDate;
        this.placeOfRegistration = placeOfRegistration;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public long getValueOfVehicle() {
    return valueOfVehicle;
}

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getPlaceOfRegistration() {
        return placeOfRegistration;
    }
    
}
