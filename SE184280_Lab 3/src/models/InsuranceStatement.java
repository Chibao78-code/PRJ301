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
import java.util.Date;

public class InsuranceStatement {
    private String insuranceID;
    private Date establishedDate;
    private String licensePlate;
    private String insuranceOwner;
    private int insurancePeriod;
    private int insuranceFees;

    
    public InsuranceStatement(String insuranceID, Date establishedDate, String licensePlate,String insuranceOwner, int insurancePeriod, int insuranceFees) {
        this.insuranceID = insuranceID;
        this.establishedDate = establishedDate;
        this.licensePlate = licensePlate;
        this.insuranceOwner = insuranceOwner;
        this.insurancePeriod = insurancePeriod;
        this.insuranceFees = insuranceFees;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public Date getEstablishedDate() {
        return establishedDate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getInsuranceOwner() {
        return insuranceOwner;
    }

    public int getInsurancePeriod() {
        return insurancePeriod;
    }

    public int getInsuranceFees() {
        return insuranceFees;
    }
}