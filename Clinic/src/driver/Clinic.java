/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package driver;

import entity.Staff;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import adt.ListInterface;
import adt.SortedArrayList;
import adt.SortedLinkedList;
import entity.Consultation;
import entity.Medicine;
import entity.Patient;
import entity.Payment;
import adt.SortedListInterface;

/**
 *
 * @author Yau Hui Yi, Vickham Foo, Cheok Ding Wei, Melvin Wong Wai Hung, Wong Kah Ming
 */
public class Clinic {

    public static void main(String[] args) {
        int counter = 0;
        int exit;

        Payment payment = new Payment();
        Medicine medicine = new Medicine();
        Patient patient = new Patient();

        // Generate data
        SortedArrayList<Medicine> medicineList = medicine.dummyData();
        SortedListInterface<Payment> paymentList = payment.paymentDummyData((SortedArrayList<Medicine>) medicineList);
        ListInterface<Patient> patientList = Patient.autoAddPatient();
        ListInterface<Staff> staffList = Staff.autoAddStaff(patientList);
        patient.setPatientCounter(5);
        do {
            Staff staff = new Staff();
            Consultation consultation = new Consultation();
            exit = 0;
            if (counter == 0) {
                clinicLogo();
                counter = 1;
            }
            if (staff.login(staffList)) {
                staff.welcomeMessage();
                do {
                    int choice = mainMenu();
                    switch (choice) {
                        case 1:
                            //Consultation
                            consultation.queueMenu(patientList, staffList, (SortedLinkedList<Payment>) paymentList, medicineList);
                            break;
                        case 2:
                            //Payment
                            payment.displayPaymentMenu(new Payment(), (SortedLinkedList<Payment>) paymentList);
                            break;
                        case 3:
                            // Medicine
                            medicine.medicineModule(medicineList);
                            break;
                        case 4:
                            //Patient
                            patient.patientModule(patientList);
                            break;
                        case 5:
                            //Staff
                            staff.staffModule(staffList, patientList);
                            break;
                        case 0:
                            if (staff.logout()) {
                                counter = 0;
                            } else {
                                break;
                            }
                            break;
                        default:
                            errorMessage();
                            break;
                    }
                } while (counter != 0);
            } else {
                errorMessage();
            }
        } while (exit != 1);

    }

    public static void clinicLogo() {
        System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+", "--------------------------", "-------------------------", "-------------------------"));
        System.out.println(String.format("| %-30s %-52s|", "", ""));
        System.out.println(String.format("+ %-30s %-52s|", "", "CLINIC WONG FEI HUNG"));
        System.out.println(String.format("| %-30s %-52s|", "", ""));
        System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+", "--------------------------", "-------------------------", "-------------------------"));

    }

    public static int mainMenu() {
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  MAIN MENU   ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [1] CONSULTATION  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [2] PAYMENT   ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [3] MEDICINE   ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [4] IN-PATIENT  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [5] STAFF  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [0] LOGOUT  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        return choiceGetter();
    }

    public static void printer(String tempString) {
        System.out.printf("\n%-10s", tempString + ":");
    }

    public static int choiceGetter() {
        Scanner scanner = new Scanner(System.in);
        printer("Please enter your selection");
        String temp = scanner.nextLine();
        return parseInt(temp);

    }

    public static void errorMessage() {
        System.out.println("\nInvalid input!");
    }

}
