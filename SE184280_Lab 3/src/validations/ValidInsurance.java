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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidInsurance {
    public static boolean isValidInsuranceId(String insuranceId) {
        return insuranceId != null && insuranceId.length() == 4;
    }

    public static boolean isValidEstablishedDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidInsurancePeriod(String periodStr) {
        try {
            int period = Integer.parseInt(periodStr);
            return period == 12 || period == 24 || period == 36;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidInsuranceOwner(String owner) {
        return owner != null && owner.length() >= 2 && owner.length() <= 25;
    }
}