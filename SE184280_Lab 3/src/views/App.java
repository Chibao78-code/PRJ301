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
import controllers.Ctmenu;
import controllers.Ctservice;
import models.Functions;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Functions service = new Ctservice();
        int choice;
        do {
            Ctmenu.showMenu();
            choice = Ctmenu.getChoice("Enter your choice: ", 1, 9);
            switch (choice) {
                case 1:
                    ShowContent.reportSuccessOrFailure(service.addCar(), 1);
                    break;
                case 2:
                    service.findCar();
                    break;
                case 3:
                    ShowContent.reportSuccessOrFailure(service.updateCar(), 3);
                    break;
                case 4:
                    ShowContent.reportSuccessOrFailure(service.deleteCar(), 4);
                    break;
                case 5:
                    ShowContent.reportSuccessOrFailure(service.addInsuranceStatement(), 5);
                    break;
                case 6:
                    service.listInsuranceStatements();
                    break;
                case 7:
                    service.reportUninsuredCars();
                    break;
                case 8:
                    ShowContent.reportSuccessOrFailure(service.saveData(), 8);
                    break;
                case 9:
                    service.quitProgram();
                    break;
            }
        } while (true);
    }
}