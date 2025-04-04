/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validations;
/**
 *
 * @author zzzdi
 */
import java.util.Date;
import java.util.regex.Pattern;

public class ValidCar {
    private static final String LICENSE_REGEX = "^5[0-9][A-Z][1-9][0-9]{5}$";
    private static final String PHONE_REGEX = "^(03|05|07|08|09)\\d{8}$";

    public static boolean validLicensePlate(String license) {
        return Pattern.matches(LICENSE_REGEX, license);
    }

    public static boolean validCarOwner(String owner) {
        return owner.length() >= 2 && owner.length() <= 25;
    }

    public static boolean validPhoneNumber(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }

    public static boolean validCarBrand(String brand) {
        return brand.length() >= 5 && brand.length() <= 12;
    }

    public static boolean validValueOfVehicle(int value) {
        return value > 999;
    }

    public static boolean validRegistrationDate(Date date) {
        return date.before(new Date());
    }

    public static boolean validNumberOfSeats(int seats) {
        return seats >= 4 && seats <= 36;
    }

    public static String getPlaceFromLicense(String licensePlate) {
        char districtCode = licensePlate.charAt(2);
        switch (districtCode) {
            case 'X': return "Thu Duc";
            case 'S': return "Binh Thanh";
            case 'T': return "District 1";
            case 'V': return "Go Vap";
            case 'P': return "Tan Binh";
            default: return "Unknown District";
        }
    }
}