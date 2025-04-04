/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;
/**
 *
 * @author zzzdi
 */
import models.Car;
import models.InsuranceStatement;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShowContent {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void displayCar(Car car) {
        System.out.println("Vehicle Details:");
        System.out.println("License plate: " + car.getLicensePlate());
        System.out.println("Owner: " + car.getCarOwner());
        System.out.println("Phone: " + car.getPhoneNumber());
        System.out.println("Car brand: " + car.getCarBrand());
        System.out.println("Value of vehicle: $" + car.getValueOfVehicle()); 
        System.out.println("Number of seats: " + car.getNumberOfSeats());
        System.out.println("Registration date: " + dateFormat.format(car.getRegistrationDate()));
        System.out.println("Place of registration: " + car.getPlaceOfRegistration());
    }

   
    public static void displayInsuranceStatements(List<InsuranceStatement> statements, int year) {
        System.out.println("Report: INSURANCE STATEMENTS");
        System.out.println("From: 01/01/" + year + " To: 31/12/" + year);
        System.out.println("Sorted by: Insurance Fees");
        System.out.println("Sort type: ASC");
        System.out.println("| No. | Insurance Id | Established Date | License plate | Customer | Insurance period | Insurance fees |");
        int count = 1;
        for (InsuranceStatement stmt : statements) {
            System.out.printf("| %d | %s | %s | %s | %s | %d | $%d |\n",
                    count++, stmt.getInsuranceID(), dateFormat.format(stmt.getEstablishedDate()),
                    stmt.getLicensePlate(), stmt.getInsuranceOwner(), stmt.getInsurancePeriod(), stmt.getInsuranceFees());
        }
    }

    public static void displayUninsuredCars(List<Car> cars) {
        System.out.println("Report: UNINSURED CARS");
        System.out.println("Sorted by: Value of vehicle");
        System.out.println("Sort type: DESC");
        System.out.println("| No. | License plate | Registration Date | Vehicle Owner | Brand | Number of seats | Value of vehicle |");
        int count = 1;
        for (Car car : cars) {
            System.out.printf("| %d | %s | %s | %s | %s | %d | $%d |\n",
                    count++, car.getLicensePlate(), dateFormat.format(car.getRegistrationDate()),
                    car.getCarOwner(), car.getCarBrand(), car.getNumberOfSeats(), car.getValueOfVehicle());
        }
    }

    public static void reportSuccessOrFailure(boolean success, int functionCode) {
        String operation = "";
        switch (functionCode) {
            case 1: operation = "Add new car"; break;
            case 3: operation = "Update car information"; break;
            case 4: operation = "Delete car information"; break;
            case 5: operation = "Add an insurance statement"; break;
            case 8: operation = "Save data"; break;
            default: operation = "Operation"; break;
        }
        if (success) {
            System.out.println(operation + " was successful.");
        } else {
            System.out.println(operation + " failed.");
        }
    }
}