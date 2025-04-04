/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
/**
 *
 * @author zzzdi
 */
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import models.Car;
import models.Functions;
import models.InsuranceStatement;
import views.ShowContent;

public class Ctservice implements Functions {

    private final Map<String, Car> carMap = new HashMap<>();
    private final List<InsuranceStatement> statements = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Ctservice() {
        loadData();
    }

    private void loadData() {      
        try (BufferedReader carReader = new BufferedReader(new FileReader("cars.txt"))) {
            String line;
            while ((line = carReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String license = parts[0];
                    String owner = parts[1];
                    String phone = parts[2];
                    String brand = parts[3];
                    long value = Long.parseLong(parts[4]);
                    int seats = Integer.parseInt(parts[5]);
                    Date regDate = dateFormat.parse(parts[6]);
                    String place = parts[7];
                    carMap.put(license, new Car(license, owner, phone, brand, (int) value, seats, regDate, place));
                }
            }
           
        } catch (FileNotFoundException e) {
            System.out.println("cars.txt not found. Starting with empty car list.");
        } catch (IOException | ParseException e) {
            System.out.println("Error loading cars: " + e.getMessage());
        }
        try (BufferedReader stmtReader = new BufferedReader(new FileReader("statements.txt"))) {
            String line;
            while ((line = stmtReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0];
                    Date date = dateFormat.parse(parts[1]);
                    String license = parts[2];
                    String owner = parts[3];
                    int period = Integer.parseInt(parts[4]);
                    int fees = Integer.parseInt(parts[5]);
                    statements.add(new InsuranceStatement(id, date, license, owner, period, fees));
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("statements.txt not found. Starting with empty statement list.");
        } catch (IOException | ParseException e) {
            System.out.println("Error loading statements: " + e.getMessage());
        }
    }

    @Override
    public boolean addCar() {
        System.out.print("Enter License Plate: ");
        String license = sc.nextLine();
        if (carMap.containsKey(license)) {
            System.out.print("Car exists! Cancel? (Y/N): ");
            if (sc.nextLine().equalsIgnoreCase("Y")) {
                return false;
            }
        }

        String owner = getStringInput("Enter Owner: ", 2, 25, "Owner must be between 2 and 25 characters.");
        String phone = getPhoneNumber("Enter Phone Number: ");
        String brand = getStringInput("Enter Car Brand: ", 5, 12, "Car brand must be between 5 and 12 characters.");
        long value = getLongInput("Enter Value of Vehicle (e.g., 2000000000000): ", "Value must be greater than 999.");
        int seats = getIntInput("Enter Number of Seats: ", "Number of seats must be between 4 and 36.", 4, 36);
        Date regDate = getDateInput("Enter Registration Date (dd/MM/yyyy): ", "Registration date must be before today.");
        System.out.print("Enter Place of Registration: ");
        String place = sc.nextLine();

        carMap.put(license, new Car(license, owner, phone, brand, (int) value, seats, regDate, place));
        return true;
    }

    @Override
    public void findCar() {
        System.out.print("Enter License Plate to find: ");
        Car car = carMap.get(sc.nextLine());
        if (car != null) {
            ShowContent.displayCar(car);
        } else {
            System.out.println("Car not found.");
        }
    }

    @Override
    public boolean updateCar() {
        System.out.print("Enter License Plate to update: ");
        String license = sc.nextLine();
        Car car = carMap.get(license);
        if (car == null) {
            System.out.println("Car not found.");
            return false;
        }

        System.out.println("Leave blank to keep current value.");
        String owner = getStringInputWithDefault("Enter new Owner (" + car.getCarOwner() + "): ", car.getCarOwner(), 2, 25, "Owner must be between 2 and 25 characters.");
        String phone = getPhoneNumberWithDefault("Enter new Phone Number (" + car.getPhoneNumber() + "): ", car.getPhoneNumber());
        String brand = getStringInputWithDefault("Enter new Car Brand (" + car.getCarBrand() + "): ", car.getCarBrand(), 5, 12, "Car brand must be between 5 and 12 characters.");
        int seats = getIntInputWithDefault("Enter new Number of Seats (" + car.getNumberOfSeats() + "): ", car.getNumberOfSeats(), 4, 36, "Number of seats must be between 4 and 36.");

        carMap.put(license, new Car(license, owner, phone, brand, (int) car.getValueOfVehicle(), seats, car.getRegistrationDate(), car.getPlaceOfRegistration()));
        System.out.println("Car updated successfully.");
        return true;
    }

    @Override
    public boolean deleteCar() {
        System.out.print("Enter License Plate to delete: ");
        String license = sc.nextLine();
        if (carMap.remove(license) != null) {
            return true;
        }
        System.out.println("Car not found.");
        return false;
    }

    @Override
    public boolean addInsuranceStatement() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Insurance ID (4 chars): ");
        String id = sc.nextLine();
        if (id.length() != 4 || statements.stream().anyMatch(s -> s.getInsuranceID().equals(id))) {
            System.out.println("Invalid or duplicate ID.");
            return false;
        }

        System.out.print("Enter License Plate: ");
        String license = sc.nextLine();
        if (!carMap.containsKey(license)) {
            System.out.println("License not found.");
            return false;
        }

        Date date = getValidDate("Enter Date (dd/MM/yyyy): ");
        if (date == null) {
            return false;
        }

        System.out.print("Enter Period (12/24/36): ");
        int period = getValidPeriod();
        if (period == -1) {
            return false;
        }

        int fees = calculateFees(period, carMap.get(license).getValueOfVehicle());

        System.out.print("Enter Owner: ");
        String owner = sc.nextLine();
        if (owner.length() < 2 || owner.length() > 25) {
            System.out.println("Owner must be 2-25 chars.");
            return false;
        }

        statements.add(new InsuranceStatement(id, date, license, owner, period, fees));
        System.out.println("Added successfully.");
        return true;
    }

    private Date getValidDate(String prompt) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        System.out.print(prompt);
        try {
            return df.parse(sc.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid date.");
            return null;
        }
    }

    private int getValidPeriod() {
        try {
            int p = Integer.parseInt(sc.nextLine());
            return (p == 12 || p == 24 || p == 36) ? p : -1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return -1;
        }
    }

    private int calculateFees(int period, long value) {
        double rate;
        if (period == 12) {
            rate = 0.025;

        } else if (period == 24) {
            rate = 0.035;
        } else {
            rate = 0.05;
        }
        return (int) (value * rate);
    }

    @Override
    public void listInsuranceStatements() {
        int year = getIntInput("Enter Year: ", "Please enter a valid year.", 1900, 9999);
        List<InsuranceStatement> filtered = statements.stream()
                .filter(stmt -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(stmt.getEstablishedDate());
                    return cal.get(Calendar.YEAR) == year;
                })
                .sorted(Comparator.comparingInt(InsuranceStatement::getInsuranceFees))
                .collect(Collectors.toList());
        ShowContent.displayInsuranceStatements(filtered, year);
    }

    @Override
    public void reportUninsuredCars() {
        List<Car> uninsured = carMap.values().stream()
                .filter(car -> statements.stream().noneMatch(s -> s.getLicensePlate().equals(car.getLicensePlate())))
                .sorted(Comparator.comparingLong(Car::getValueOfVehicle).reversed())
                .collect(Collectors.toList());
        ShowContent.displayUninsuredCars(uninsured);
    }

    @Override
    public boolean saveData() {
        try {          
            try (PrintWriter carWriter = new PrintWriter(new File("cars.txt"))) {
                for (Car car : carMap.values()) {
                    carWriter.println(car.getLicensePlate() + ","
                            + car.getCarOwner() + ","
                            + car.getPhoneNumber() + ","
                            + car.getCarBrand() + ","
                            + car.getValueOfVehicle() + ","
                            + car.getNumberOfSeats() + ","
                            + dateFormat.format(car.getRegistrationDate()) + ","
                            + car.getPlaceOfRegistration());
                }
                System.out.println("Cars saved to cars.txt");
            }

            try (PrintWriter stmtWriter = new PrintWriter(new File("statements.txt"))) {
                for (InsuranceStatement stmt : statements) {
                    stmtWriter.println(stmt.getInsuranceID() + ","
                            + dateFormat.format(stmt.getEstablishedDate()) + ","
                            + stmt.getLicensePlate() + ","
                            + stmt.getInsuranceOwner() + ","
                            + stmt.getInsurancePeriod() + ","
                            + stmt.getInsuranceFees());
                }
                System.out.println("Insurance statements saved to statements.txt");
            }

            return true;
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void quitProgram() throws IOException, ClassNotFoundException {
        System.out.print("Save data before quitting? (Y/N): ");
        if (sc.nextLine().equalsIgnoreCase("Y")) {
            if (saveData()) {
                System.out.println("Data saved successfully before quitting.");
            } else {
                System.out.println("Failed to save data before quitting.");
            }
        }
        System.out.println("Goodbye!");
        System.exit(0);
    }
    private String getStringInput(String prompt, int minLength, int maxLength, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.length() >= minLength && input.length() <= maxLength) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private String getStringInputWithDefault(String prompt, String defaultValue, int minLength, int maxLength, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.isEmpty()) {
                return defaultValue;
            }
            if (input.length() >= minLength && input.length() <= maxLength) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private String getPhoneNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String phone = sc.nextLine();
            if (phone.matches("^(03|05|07|08|09)\\d{8}$")) {
                return phone;
            }
            System.out.println("Phone number must be 10 digits and start with 03, 05, 07, 08, or 09.");
        }
    }

    private String getPhoneNumberWithDefault(String prompt, String defaultValue) {
        while (true) {
            System.out.print(prompt);
            String phone = sc.nextLine();
            if (phone.isEmpty()) {
                return defaultValue;
            }
            if (phone.matches("^(03|05|07|08|09)\\d{8}$")) {
                return phone;
            }
            System.out.println("Phone number must be 10 digits and start with 03, 05, 07, 08, or 09.");
        }
    }

    private long getLongInput(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            try {
                long value = Long.parseLong(sc.nextLine().replace(".", ""));
                if (value > 999) {
                    return value;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private int getIntInput(String prompt, String errorMessage, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private int getIntInputWithDefault(String prompt, int defaultValue, int min, int max, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private Date getDateInput(String prompt, String additionalValidationMessage) {
        while (true) {
            System.out.print(prompt);
            try {
                Date date = dateFormat.parse(sc.nextLine());
                if (additionalValidationMessage == null || date.before(new Date())) {
                    return date;
                }
                System.out.println(additionalValidationMessage);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
    }
}
