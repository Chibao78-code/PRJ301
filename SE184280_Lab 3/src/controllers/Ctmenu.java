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

import views.Menu;
import java.util.Scanner;

public class Ctmenu {
    private static final Scanner sc = new Scanner(System.in);

    public static void showMenu() {
        for (String line : Menu.getMainMenu()) {
            System.out.println(line);
        }
    }

    public static int getChoice(String prompt, int min, int max) {
        int choice;
        while (true) {
            System.out.print(prompt);
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}