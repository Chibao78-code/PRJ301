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
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private static final List<String> mainMenu = new ArrayList<>();

    static {
        mainMenu.add("||=====================================||");
        mainMenu.add("|| 1. Add new car                     ||");
        mainMenu.add("|| 2. Find a car by license plate     ||");
        mainMenu.add("|| 3. Update car information          ||");
        mainMenu.add("|| 4. Delete car information          ||");
        mainMenu.add("|| 5. Add an insurance statement      ||");
        mainMenu.add("|| 6. List of insurance statements    ||");
        mainMenu.add("|| 7. Report uninsured cars           ||");
        mainMenu.add("|| 8. Save data                       ||");
        mainMenu.add("|| 9. Quit                            ||");
        mainMenu.add("||=====================================||");
    }

    public static List<String> getMainMenu() {
        return mainMenu;
    }
}