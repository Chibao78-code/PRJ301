/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Scanner;

/**
 *
 * @author zzzdi
 */
public class Inpututils {
     private static final Scanner sc = new Scanner(System.in);

    public static int getInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
