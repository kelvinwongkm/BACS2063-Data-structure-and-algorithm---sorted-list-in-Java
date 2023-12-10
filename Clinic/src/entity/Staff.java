/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import adt.ArrayList;
import adt.LinkedList;
import driver.Clinic;
import static driver.Clinic.printer;
import static java.lang.Integer.parseInt;
import java.util.Arrays;
import java.util.Scanner;
import adt.ListInterface;
import java.util.Objects;

/**
 *
 * @author Vickham Foo
 */
public class Staff {

    private String staffID;
    private String staffName;
    private Character gender;
    private int staffAge;
    private String position;
    private int[] timeTable;

    public Staff() {
    }

    public Staff(String staffID, String staffName, Character gender, int staffAge, String position, int[] timeTable) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.gender = gender;
        this.staffAge = staffAge;
        this.position = position;
        this.timeTable = timeTable;
    }

    public Staff(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public int getStaffAge() {
        return staffAge;
    }

    public void setStaffAge(int staffAge) {
        this.staffAge = staffAge;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int[] getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(int[] timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Staff other = (Staff) obj;
        if (this.staffAge != other.staffAge) {
            return false;
        }
        if (!Objects.equals(this.staffID, other.staffID)) {
            return false;
        }
        if (!Objects.equals(this.staffName, other.staffName)) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        return Arrays.equals(this.timeTable, other.timeTable);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.staffID);
        hash = 71 * hash + Objects.hashCode(this.staffName);
        hash = 71 * hash + Objects.hashCode(this.gender);
        hash = 71 * hash + this.staffAge;
        hash = 71 * hash + Objects.hashCode(this.position);
        hash = 71 * hash + Arrays.hashCode(this.timeTable);
        return hash;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffID=" + staffID + ", staffName=" + staffName + ", gender=" + gender + ", staffAge=" + staffAge + ", position=" 
                + position + ", timeTable=" + Arrays.toString(timeTable) + '}';
    }

    //LOGIN
    public boolean login(ListInterface<Staff> staffList) {
        ListInterface<Staff> adminList = adminList(staffList);
        Scanner scanner = new Scanner(System.in);
        Clinic.printer("User ID");
        String tempUserID = scanner.nextLine().toUpperCase();
        for (int i = 0; i <adminList.getNumberOfEntries(); i++) {
            Admin admin = (Admin) adminList.getEntry(i);
            if (tempUserID == null ? admin.getStaffID() == null : tempUserID.equals(admin.getStaffID())) {
                Clinic.printer("Password");
                String tempPassword = scanner.nextLine();
                if (tempPassword == null ? admin.getPassword() == null : tempPassword.equals(admin.getPassword())) {
                    this.staffID = adminList.getEntry(i).getStaffID();
                    this.staffName = adminList.getEntry(i).getStaffName();
                    this.gender = adminList.getEntry(i).getGender();
                    this.staffAge = adminList.getEntry(i).getStaffAge();
                    this.position = adminList.getEntry(i).getPosition();
                    return true;
                }
            }

        }

        return false;
    }

    //LOGOUT
    public boolean logout() {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        do {
            Clinic.printer("Are you sure you want to logout?(Y/N)");
            String tempString = scanner.nextLine().toUpperCase();
            if (!tempString.equals("Y") && !tempString.equals("N")) {
                Clinic.errorMessage();
                counter = 1;
            } else if (tempString.equals("Y")) {
                System.out.println("Thanks for using our system. Bye!\n\n");
                return true;
            } else if (tempString.equals("N")) {
                counter = 0;
            }
        } while (counter == 1);
        return false;

    }

    //WELCOME MESSAGE
    public void welcomeMessage() {

        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-25s|\n", "  Welcome," + staffName + '!');
        System.out.printf("+-%-24s-+\n", "------------------------");

    }

    //STAFF MENU 
    public int staffMenu() {
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  STAFF   ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [1] ADD STAFF  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [2] VIEW STAFF  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [3] UPDATE STAFF  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [4] REMOVE STAFF  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [5] TIMETABLE  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [0] RETURN  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        return Clinic.choiceGetter();

    }

    //STAFF MODULE
    public void staffModule(ListInterface<Staff> staffList, ListInterface<Patient> patientList) {
        int tempChoice;
        do {
            tempChoice = staffMenu();
            switch (tempChoice) {
                case 1:
                    addStaff(staffList);
                    break;
                case 2:
                    viewStaff(staffList, patientList);
                    break;
                case 3:
                    updateStaff(staffList, patientList);
                    break;
                case 4:
                    removeStaff(staffList);
                    break;
                case 5:
                    staffTimetable(staffList);
                    break;

            }
        } while (tempChoice != 0);

    }

    //STAFF LIST GENERATOR
    public static ListInterface<Staff> autoAddStaff(ListInterface<Patient> patientList) {
        ListInterface<Staff> staffList = new ArrayList<>();
        ListInterface<Patient> consultationList1 = autoGenerateConsultationList(patientList, "S003");
        ListInterface<Patient> consultationList2 = autoGenerateConsultationList(patientList, "S004");
        ListInterface<Patient> consultationList3 = autoGenerateConsultationList(patientList, "S005");
        staffList.add(new Admin("S001", "Vickham Foo", 'M', 28, "Admin", new int[]{1, 1, 1, 1, 1, 1}, "abc12345"));
        staffList.add(new Admin("S002", "Cheok Ding Wei", 'M', 30, "Admin", new int[]{2, 2, 2, 2, 2, 2}, "bac23456"));
        staffList.add(new Doctor("S003", "Dr. Melvin Wong Fei Hung", 'M', 36, "Doctor", new int[]{1, 1, 1, 1, 1, 2}, consultationList1));
        staffList.add(new Doctor("S004", "Dr. Wong Kah Ming", 'M', 34, "Doctor", new int[]{2, 2, 1, 2, 2, 1}, consultationList2));
        staffList.add(new Doctor("S005", "Dr. Yau Hui Yi", 'F', 30, "Doctor", new int[]{1, 1, 1, 2, 1, 1}, consultationList3));
        return staffList;
    }

    public static ListInterface<Patient> autoGenerateConsultationList(ListInterface<Patient> patientList, String tempID) {
        ListInterface<Patient> consultationList = new LinkedList();
        ListInterface<Patient> staffPatientList = new LinkedList();
        int counter = 1;
        if (counter == 1) {
            staffPatientList.add(new Patient("P0001", "222", "Mel", 'M', 20, "1", new String[][]{{"2022-09-01 08:17:40", "S003", "Allergies", "Paracetamol", "3"}}));
            staffPatientList.add(new Patient("P0002", "444", "Ban", 'F', 12, "1", new String[][]{{"2022-09-01 12:23:56", "S004", "Colds and Flu", "Paracetamol", "5"}}));
            staffPatientList.add(new Patient("P0003", "777", "Kel", 'M', 19, "1", new String[][]{{"2022-09-04 12:17:20", "S004", "Allergies", "Paracetamol", "1"}}));
            staffPatientList.add(new Patient("P0004", "333", "Stone", 'F', 11, "1", new String[][]{{"2022-09-08 09:48:13", "S005", "Diarrhea", "Paracetamol", "3"}}));
            staffPatientList.add(new Patient("P0005", "111", "Fy", 'M', 10, "1", new String[][]{{"2022-09-09 10:50:56", "S003", "Allergies", "Paracetamol", "2"}}));

            Patient patient = new Patient();
            String[][] array;

            for (int i = 1; i <=patientList.getNumberOfEntries(); i++) {
                patient = staffPatientList.getEntry(i);
                array = patient.getConsultationHistory();
                if (array[0][1].equals(tempID)) {
                    consultationList.add(patient);
                }

            }
            counter = 2;
        } else if (counter == 2) {
            Patient patient = new Patient();
            String[][] array;

            for (int i = 0; i <patientList.getNumberOfEntries(); i++) {
                patient = patientList.getEntry(i);
                array = patient.getConsultationHistory();
                if (array[0][1].equals(tempID)) {
                    consultationList.add(patient);
                }

            }
            counter = 2;

        }
        return consultationList;
    }

    //GENERATE DOCTOR LIST
    public static ListInterface<Staff> doctorList(ListInterface<Staff> staffList) {
        ListInterface<Staff> doctorList = new ArrayList();
        for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
            Staff staff = staffList.getEntry(i);
            if (staff.position.equals("Doctor")) {
                doctorList.add(staff);
            }

        }
        return doctorList;
    }

    //GENERATE ADMIN LIST
    public static ListInterface<Staff> adminList(ListInterface<Staff> staffList) {
        ListInterface<Staff> adminList = new ArrayList();
        for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
            Staff staff = staffList.getEntry(i);
            if (staff.position.equals("Admin")) {
                adminList.add(staff);
            }

        }
        return adminList;
    }

    // ADD STAFF 
    public static void addStaff(ListInterface<Staff> staffList) {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        int counter1 = 1;
        ListInterface<Patient> consultationList = new LinkedList();
        do {
            String tempChoice;
            String tempID;
            String tempPassword = "abc12345";
            int shift[] = {};

            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "       NAME      ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "   EG. ALI  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.print("\nEnter the name > ");
            String tempName = scanner.nextLine();

            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "      GENDER    ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            String tempGender = genderSelector();

            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "       AGE     ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "   EG. 35  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.print("\nEnter the age > ");
            int tempAge = scanner.nextInt();

            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "      POSITION    ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            String tempPosition = positionSelector();

            if (tempPosition.equals("Admin")) {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      PASSWORD    ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "   EG. ABC1234  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the password > ");
                tempPassword = scanner.next();
            }

            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "     WORKING SHIFT   ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            String tempShift = shiftSelector();
            if (tempShift.equals("Morning")) {
                shift = new int[]{1, 1, 1, 1, 1, 1};
            } else if (tempShift.equals("Night")) {
                shift = new int[]{2, 2, 2, 2, 2, 0};
            }
            scanner.nextLine();
            do {
                Clinic.printer("Are you sure you want to add this staff?(Y/N)");
                String tempString = scanner.nextLine().toUpperCase();
                if (!tempString.equals("Y") && !tempString.equals("N")) {
                    Clinic.errorMessage();
                    counter1 = 1;
                } else if (tempString.equals("Y")) {
                    counter1 = 0;
                    if (tempPosition.equals("Admin")) {
                        tempID = String.format("S%03d", staffList.getNumberOfEntries() + 1);
                        staffList.add(new Admin(tempID, tempName, tempGender.charAt(0), tempAge, tempPosition, shift, tempPassword));
                        //successfullyMsg("Admin");
                    } else {
                        tempID = String.format("S%03d", staffList.getNumberOfEntries() + 1);
                        staffList.add(new Doctor(tempID, tempName, tempGender.charAt(0), tempAge, tempPosition, shift, consultationList));
                        //successfullyMsg("Doctor");
                    }
                    System.out.println("\nStaff Successfully added!");
                } else if (tempString.equals("N")) {
                    counter1 = 0;
                }
            } while (counter1 == 1);
                                   

           // scanner.nextLine();
            do {
                printer("Add more?(Y/N)");
                tempChoice = scanner.nextLine().toUpperCase();
                if (tempChoice.equals("Y")) {
                    counter = 0;
                } else if (tempChoice.equals("N")) {
                    counter = 1;
                } else if (!tempChoice.equals("Y") && !tempChoice.equals("N")) {
                    Clinic.errorMessage();
                    counter = 3;
                }
            } while (counter == 3);

        } while (counter != 1);

    }

    public static int removeStaffMenu() {

        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "       DELETE MENU     ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [1] REMOVE STAFF    ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  [0] RETURN  ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        return Clinic.choiceGetter();

    }

    //REMOVE STAFF    
    public static void removeStaff(ListInterface<Staff> staffList) {
        int counter = 1;
        int tempChoice;
        int choice;
        int entry = 0;
        do {
            choice = removeStaffMenu();
            switch (choice) {
                case 1:
                    Scanner scanner = new Scanner(System.in);
                    displayStaffDetails(0, staffList, 0);
                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                    System.out.printf("| %-24s |\n", "       STAFF ID       ");
                    System.out.printf("+-%-24s-+\n", "------------------------");
                    System.out.printf("| %-24s |\n", "       EG. S001        ");
                    System.out.printf("+-%-24s-+\n", "------------------------");
                    System.out.print("\nEnter the staff ID > ");
                    String tempStaffID = scanner.next();
                    for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
                        if (staffList.getEntry(i).staffID.equals(tempStaffID)) {
                            displayStaffDetails(1, staffList, i);
                            tempChoice = i;
                            entry = 1;
                            scanner.nextLine();
                            do {
                                Clinic.printer("Are you sure you want to remove this staff?(Y/N)");
                                String tempString = scanner.nextLine().toUpperCase();
                                if (!tempString.equals("Y") && !tempString.equals("N")) {
                                    Clinic.errorMessage();
                                    counter = 1;
                                } else if (tempString.equals("Y")) {
                                    counter = 0;
                                    staffList.remove(tempChoice);
                                    System.out.println("\nStaff Successfully deleted!");
                                } else if (tempString.equals("N")) {
                                    counter = 0;
                                }
                            } while (counter == 1);

                        }
                    }
                    if (entry == 0) {
                        System.out.println("NO RECORD FOUND !");

                    }
                    break;
            }
        } while (choice != 0);
    }

    //VIEW STAFF
    public static void viewStaff(ListInterface<Staff> staffList, ListInterface<Patient> patientList) {
        Scanner scanner = new Scanner(System.in);
        ListInterface<Staff> adminList = adminList(staffList);
        ListInterface<Staff> doctorList = doctorList(staffList);
        ListInterface<Staff> sortedStaffList = new ArrayList();
        int tempChoice;
        int viewOption;
        int entry = 0;
        do {
            tempChoice = viewStaffMenu(0);
            switch (tempChoice) {
                case 1:
                    displayStaffDetails(0, staffList, 0);
                    sortedStaffList = sortStaffName(staffList);
                    break;
                case 2:
                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                    System.out.printf("| %-24s |\n", "       ADMIN     ");
                    System.out.printf("+-%-24s-+\n", "------------------------");
                    displayStaffDetails(0, adminList, 0);
                    sortedStaffList = sortStaffName(adminList);
                    System.out.println("\n");
                    break;
                case 3:
                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                    System.out.printf("| %-24s |\n", "       DOCTOR   ");
                    System.out.printf("+-%-24s-+\n", "------------------------");
                    displayStaffDetails(0, doctorList, 0);
                    sortedStaffList = sortStaffName(doctorList);
                    break;
            }
            if (tempChoice != 0) {
                do {
                    viewOption = viewStaffMenu(1);
                    switch (viewOption) {
                        case 1:
                            //sorting by name         
                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                            System.out.printf("| %-24s|\n", "      SORTED RESULT      ");
                            System.out.printf("+-%-24s-+\n", "------------------------");
                            displayStaffDetails(0, sortedStaffList, 0);
                            break;
                        case 2:
                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                            System.out.printf("| %-24s |\n", "       STAFF ID       ");
                            System.out.printf("+-%-24s-+\n", "------------------------");
                            System.out.printf("| %-24s |\n", "       EG. S001        ");
                            System.out.printf("+-%-24s-+\n", "------------------------");
                            System.out.print("\nEnter the staff ID > ");
                            String staffID = scanner.next();
                            for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
                                if (staffID.equals(staffList.getEntry(i).staffID)) {
                                    displayStaffDetails(1, staffList, i);
                                    entry = 1;
                                    if (staffList.getEntry(i).position.equals("Doctor")) {
                                        doctorConsultHistory(staffList, i, patientList);
                                        entry = 1;
                                    }
                                }
                            }
                            if (entry == 0) {
                                System.out.println("NO RECORD FOUND !");
                            }
                            break;
                        case 0:
                            System.out.println("");
                            break;
                    }
                } while (viewOption != 0);
            }
        } while (tempChoice != 0);
        System.out.println("\n");

    }

    public static ListInterface<Staff> sortStaffName(ListInterface<Staff> staffList) {
        String[] staffNameArray = new String[staffList.getNumberOfEntries()];
        ListInterface<Staff> sortedStaffList = new ArrayList();
        Staff tempStaff = new Staff();
        int index = 0;
        int counter = 0;
        for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
            staffNameArray[index] = staffList.getEntry(i).staffName;
            index++;
        }
        sortedStaffList.insertionSort(staffNameArray, staffNameArray.length);

        while (counter != staffList.getNumberOfEntries()) {

            for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
                tempStaff = staffList.getEntry(i);
                if (tempStaff.staffName.equals(staffNameArray[counter])) {
                    sortedStaffList.add(tempStaff);
                    counter++;
                    break;
                }
            }

        }

        return sortedStaffList;
    }

    public static int viewStaffMenu(int mode) {
        if (mode == 0) {
            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "       VIEW MENU     ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] ALL STAFF    ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [2] ADMIN  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [3] DOCTOR ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [0] RETURN ");
            System.out.printf("+-%-24s-+\n", "------------------------");

        } else if (mode == 1) {
            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "       VIEW OPTION      ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] SORT BY NAME (A-Z)");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [2] SEARCH STAFF  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [0] RETURN  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
        }
        return Clinic.choiceGetter();

    }

    public static void displayStaffDetails(int mode, ListInterface<Staff> staffList, int counter) {
        System.out.println("");
        System.out.println(String.format("+-%-3s---%-15s---%-25s-------%-15s---%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-40s%-7s%-40s       |", "", "STAFF DETAILS", ""));
        if (mode == 0) {
            for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff ID", staffList.getEntry(i).staffID));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff Name", staffList.getEntry(i).staffName));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff Gender", staffList.getEntry(i).gender));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff Age", staffList.getEntry(i).staffAge));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Position", staffList.getEntry(i).position));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            }
        } else if (mode == 1) {
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff ID", staffList.getEntry(counter).staffID));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff Name", staffList.getEntry(counter).staffName));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff Gender", staffList.getEntry(counter).gender));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff Age", staffList.getEntry(counter).staffAge));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Position", staffList.getEntry(counter).position));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
        }
    }

        public static void displayStaffShift(int mode, ListInterface<Staff> staffList, int counter) {
        System.out.println("");
        System.out.println(String.format("+-%-3s---%-15s---%-25s-------%-15s---%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-40s%-7s%-40s       |", "", "STAFF DETAILS", ""));
        if (mode == 0) {
            for (int i = 1; i <=staffList.getNumberOfEntries(); i++) {
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff ID", staffList.getEntry(i).staffID));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff Name", staffList.getEntry(i).staffName));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff Gender", staffList.getEntry(i).gender));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Staff Age", staffList.getEntry(i).staffAge));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
                System.out.println(String.format("| %-71s | %-25s |", "Position", staffList.getEntry(i).position));
                System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            }
        } else if (mode == 1) {
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff ID", staffList.getEntry(counter).staffID));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff Name", staffList.getEntry(counter).staffName));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff Gender", staffList.getEntry(counter).gender));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Staff Age", staffList.getEntry(counter).staffAge));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
            System.out.println(String.format("| %-71s | %-25s |", "Position", staffList.getEntry(counter).position));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
        }
    }
        
    public static void doctorConsultHistory(ListInterface<Staff> staffList, int counter, ListInterface<Patient> patientList) {
        Doctor doctor = (Doctor) staffList.getEntry(counter);
        Patient patient = new Patient();
        int record = 1;
        if (doctor.getConsultationHistory().isEmpty()) {
            System.out.println(String.format("| %-37s%-7s%-39s |", "", "NO CONSULTATION HISTORY", ""));
            System.out.println(String.format("+-%-67s---%-25s-+\n", "-----------------------------------------------------------------------", "----------------------------------"));
        } else {
            System.out.println(String.format("\n+-%-67s---%-25s-+", "--------------------------------------------------------------------------", "-----------------------------------------"));
            System.out.println(String.format("| %-50s%-7s%-48s |", "", "CONSULTATION HISTORY", ""));
            System.out.println(String.format("+-----+%-19s+%-17s+%-17s+%-17s+%-23s+%-10s+",
                    "-----------------------", "-----------------", "-----------------", "------------------", "------------------------", "----------"));
            System.out.println(String.format("| %-3s | %-21s | %-15s | %-15s | %-15s | %-15s  | %-8s |", "No.", 
                    "Consultation Date", "Patient ID", "Name", "Diagnosis Result", "Prescribed Medication", "Quantity"));
            System.out.println(String.format("+-----+%-19s+%-17s+%-17s+%-17s+%-23s+%-10s+", "-----------------------", 
                    "-----------------", "-----------------", "------------------", "------------------------", "----------"));

            for (int j = 1; j <=doctor.getConsultationHistory().getNumberOfEntries(); j++) {
                patient = (Patient) doctor.getConsultationHistory().getEntry(j);
                String[][] array = patient.getConsultationHistory();
                System.out.println(String.format("| %-3s | %-21s | %-15s | %-15s | %-16s | %-21s  | %-8s |", 
                        record + ".", array[0][0], patient.getPatientID(), patient.getPatientName(), array[0][2], array[0][3], array[0][4]));
                System.out.println(String.format("+-----+%-19s+%-17s+%-17s+%-17s+%-23s+%-10s+", "-----------------------",
                        "-----------------", "-----------------", "------------------", "------------------------", "----------"));
                record++;
            }
        }

    }

    //STAFF UPDATE 
    public static int staffUpdateMenu(int mode) {
        if (mode == 0) {
            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "      UPDATE STAFF   ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] UPDATE    ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [0] RETURN  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
        }
        if (mode == 1) {
            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "      UPDATE MENU     ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] NAME    ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [2] GENDER  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [3] AGE ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [4] POSITION ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [5] PASSWORD ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [0] RETURN ");
            System.out.printf("+-%-24s-+\n", "------------------------");
        }
        return Clinic.choiceGetter();
    }

    public static void updateStaff(ListInterface<Staff> staffList, ListInterface<Patient> patientList) {
        Scanner scanner = new Scanner(System.in);
        int tempChoice;
        int choice;
        int entry = 0;
        int tempSelect = 0;
        do {
            tempChoice = staffUpdateMenu(0);
            if (tempChoice == 1) {
                displayStaffDetails(0, staffList, 0);
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       STAFF ID       ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       EG. S001        ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the staff ID > ");
                String staffID = scanner.next();
                for (int i = 0; i <staffList.getNumberOfEntries(); i++) {
                    if (staffID.equals(staffList.getEntry(i).staffID)) {
                        displayStaffDetails(1, staffList, i);
                        entry = 1;
                        if (staffList.getEntry(i).position.equals("Doctor")) {
                            doctorConsultHistory(staffList, i, patientList);
                            entry = 1;
                        }
                        tempSelect = i;
                    }
                }
                if (entry == 0) {
                    System.out.println("NO RECORD FOUND !");
                }

                if (tempChoice != 0 && entry != 0) {
                    do {
                        choice = staffUpdateMenu(1);
                        switch (choice) {
                            case 1:
                                staffNewName(staffList, tempSelect);
                                break;
                            case 2:
                                staffNewGender(staffList, tempSelect);
                                break;
                            case 3:
                                staffNewAge(staffList, tempSelect);
                                break;
                            case 4:
                                staffNewPosition(staffList, tempSelect);
                                break;
                            case 5:
                                if (staffList.getEntry(tempSelect).position.equals("Admin")) {
                                    staffNewPassword(staffList, tempSelect);
                                } else {
                                    System.out.println("Doctor has no password!");
                                }
                                break;
                            case 0:
                                break;
                            default:
                                Clinic.errorMessage();
                                break;
                        }
                    } while (choice != 0);
                }
            }
        } while (tempChoice != 0);
    }

    public ListInterface<Staff> morningShiftGenerator(int counter, ListInterface<Staff> staffList) {
        ListInterface<Staff> staffShiftList = new LinkedList();
        for (int j = 0; j < staffList.getNumberOfEntries(); j++) {
            if (staffList.getEntry(j).timeTable[counter] == 1) {
                Staff staff = staffList.getEntry(j);
                staffShiftList.add(staff);
            }
        }

        return staffShiftList;
    }

    public ListInterface<Staff> nightShiftGenerator(int counter, ListInterface<Staff> staffList) {
        ListInterface<Staff> staffShiftList = new LinkedList();
        for (int j = 0; j < staffList.getNumberOfEntries(); j++) {
            if (staffList.getEntry(j).timeTable[counter] == 2) {
                Staff staff = staffList.getEntry(j);
                staffShiftList.add(staff);
            }
        }

        return staffShiftList;
    }

    public static int timeTableMenu(int mode) {
        switch (mode) {
            case 0:
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "     TIMETABLE  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [1] VIEW WHOLE WEEK   ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [2] SELECT DAY  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [0] RETURN  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                break;
            case 1:
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      DAY     ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [1] MONDAY   ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [2] TUESDAY  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [3] WEDNESDAY ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [4] THURSDAY ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [5] FRIDAY ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [6] SATURDAY ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [0] RETURN ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                break;
            case 2:
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      OPTION    ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [1] EDIT   ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [0] RETURN  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                break;

            case 3:
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "     SHIFT  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [1] MORNING  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [2] NIGHT  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [0] RETURN  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                break;

        }
        return Clinic.choiceGetter();
    }

    public void changeShift(int counter, int day, int shift, ListInterface<Staff> staffList) {
        Staff staff = staffList.getEntry(counter);
        int[] array = staff.timeTable;
        if (shift == 1) {
            shift = 2;
        } else {
            shift = 1;
        }
        array[day - 1] = shift;
        staff.timeTable = array;
        staffList.replace(counter, staff);
    }

    //STAFF TIMETABLE
    public void staffTimetable(ListInterface<Staff> staffList) {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;
        ListInterface<Staff> morningShiftList;
        ListInterface<Staff> nightShiftList;
        int choice;
        int choice1;
        int day = 0;
        int selection;
        int shift;
        int entry = 0;
        do {
            choice = timeTableMenu(0);
            switch (choice) {
                case 1:
                    for (int i = 0; i < 6; i++) {
                        switch (i) {
                            case 0:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     MONDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 1:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     TUESDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 2:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     WEDNESDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 3:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     THURSDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 4:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     FRIDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 5:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     SATURDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                        }
                        System.out.printf("\n| %-24s |\n", "     MORNING ");
                        System.out.printf("+-%-24s-+\n", "------------------------");
                        morningShiftList = morningShiftGenerator(i, staffList);
                        displayStaffShift(0, morningShiftList, 0);
                        System.out.printf("\n+-%-24s-+\n", "------------------------");
                        System.out.printf("| %-24s |\n", "     NIGHT ");
                        System.out.printf("+-%-24s-+\n", "------------------------");
                        nightShiftList = nightShiftGenerator(i, staffList);
                        displayStaffShift(0, nightShiftList, 0);
                    }

                    do {
                        selection = timeTableMenu(2);
                        if (selection == 1) {
                            do {
                                printer("Please select the day you want to edit");
                                choice1 = timeTableMenu(1);
                                if (choice1 > 0 && choice1 < 7) {
                                    do {
                                        shift = timeTableMenu(3);
                                        switch (choice1) {
                                            case 1:
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     MONDAY ");
                                                System.out.printf("+-%-24s-+", "------------------------");
                                                break;
                                            case 2:
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     TUESDAY ");
                                                System.out.printf("+-%-24s-+", "------------------------");
                                                break;
                                            case 3:
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     WEDNESDAY ");
                                                System.out.printf("+-%-24s-+", "------------------------");
                                                break;
                                            case 4:
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     THURSDAY ");
                                                System.out.printf("+-%-24s-+", "------------------------");
                                                break;
                                            case 5:
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     FRIDAY ");
                                                System.out.printf("+-%-24s-+", "------------------------");
                                                break;
                                            case 6:
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     SATURDAY ");
                                                System.out.printf("+-%-24s-+", "------------------------");
                                                break;
                                        }
                                        if (shift != 0) {
                                            if (shift == 1) {
                                                System.out.printf("\n| %-24s |\n", "     MORNING ");
                                                System.out.printf("+-%-24s-+\n", "------------------------");
                                                morningShiftList = morningShiftGenerator(choice1 - 1, staffList);
                                                displayStaffShift(0, morningShiftList, 0);

                                            } else if (shift == 2) {
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     NIGHT ");
                                                System.out.printf("+-%-24s-+\n", "------------------------");
                                                nightShiftList = nightShiftGenerator(choice1 - 1, staffList);
                                                displayStaffShift(0, nightShiftList, 0);
                                            }

                                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                                            System.out.printf("| %-24s |\n", "       STAFF ID       ");
                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                            System.out.printf("| %-24s |\n", "       EG. S001        ");
                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                            System.out.print("\nEnter the staff ID > ");
                                            String staffID = scanner.next();
                                            for (int i = 0; i < staffList.getNumberOfEntries(); i++) {
                                                if (staffID.equals(staffList.getEntry(i).staffID)) {
                                                    //ask confirmation
                                                    do {
                                                        scanner.nextLine();
                                                        Clinic.printer("Are you sure you want to edit the shift?(Y/N)");
                                                        String tempString = scanner.nextLine().toUpperCase();
                                                        if (!tempString.equals("Y") && !tempString.equals("N")) {
                                                            Clinic.errorMessage();
                                                            counter = 1;
                                                        } else if (tempString.equals("Y")) {

                                                            changeShift(i, choice1, shift, staffList);
                                                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                            System.out.printf("| %-24s |\n", "     MORNING ");
                                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                                            morningShiftList = morningShiftGenerator(choice1 - 1, staffList);
                                                            displayStaffShift(0, morningShiftList, 0);

                                                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                            System.out.printf("| %-24s |\n", "     NIGHT ");
                                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                                            nightShiftList = nightShiftGenerator(choice1 - 1, staffList);
                                                            displayStaffShift(0, nightShiftList, 0);
                                                            counter = 0;
                                                            shift = 0;
                                                            choice1 = 0;

                                                        } else if (tempString.equals("N")) {
                                                            counter = 0;
                                                        }
                                                    } while (counter == 1);
                                                    entry = 1;
                                                }
                                            }
                                            if (entry == 0) {
                                                System.out.println("NO RECORD FOUND !");
                                            }
                                        }
                                    } while (shift != 0);
                                }

                            } while (choice1 != 0);
                        }
                    } while (selection != 0);

                    break;

                case 2:
                    do {
                        day = timeTableMenu(1);
                        switch (day) {
                            case 1:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     MONDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 2:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     TUESDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 3:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     WEDNESDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 4:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     THURSDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 5:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     FRIDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                            case 6:
                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                System.out.printf("| %-24s |\n", "     SATURDAY ");
                                System.out.printf("+-%-24s-+", "------------------------");
                                break;
                        }
                        if (day > 0 && day < 7) {
                            System.out.printf("\n| %-24s |\n", "     MORNING ");
                            System.out.printf("+-%-24s-+\n", "------------------------");
                            morningShiftList = morningShiftGenerator(day - 1, staffList);
                            displayStaffShift(0, morningShiftList, 0);
                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                            System.out.printf("| %-24s |\n", "     NIGHT ");
                            System.out.printf("+-%-24s-+\n", "------------------------");
                            nightShiftList = nightShiftGenerator(day - 1, staffList);
                            displayStaffShift(0, nightShiftList, 0);

                            do {
                                selection = timeTableMenu(2);
                                if (selection == 1) {
                                    do {
                                        shift = timeTableMenu(3);
                                        if (shift != 0) {
                                            if (shift == 1) {
                                                System.out.printf("\n| %-24s |\n", "     MORNING ");
                                                System.out.printf("+-%-24s-+\n", "------------------------");
                                                morningShiftList = morningShiftGenerator(day - 1, staffList);
                                                displayStaffShift(0, morningShiftList, 0);

                                            } else if (shift == 2) {
                                                System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                System.out.printf("| %-24s |\n", "     NIGHT ");
                                                System.out.printf("+-%-24s-+\n", "------------------------");
                                                nightShiftList = nightShiftGenerator(day - 1, staffList);
                                                displayStaffShift(0, nightShiftList, 0);
                                            }

                                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                                            System.out.printf("| %-24s |\n", "       STAFF ID       ");
                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                            System.out.printf("| %-24s |\n", "       EG. S001        ");
                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                            System.out.print("\nEnter the staff ID > ");
                                            String staffID = scanner.next();
                                            for (int i = 0; i < staffList.getNumberOfEntries(); i++) {
                                                if (staffID.equals(staffList.getEntry(i).staffID)) {
                                                    //ask confirmation
                                                    do {
                                                        entry = 1;
                                                        scanner.nextLine();
                                                        Clinic.printer("Are you sure you want to edit the shift?(Y/N)");
                                                        String tempString = scanner.nextLine().toUpperCase();
                                                        if (!tempString.equals("Y") && !tempString.equals("N")) {
                                                            Clinic.errorMessage();
                                                            counter = 1;
                                                        } else if (tempString.equals("Y")) {

                                                            changeShift(i, day, shift, staffList);
                                                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                            System.out.printf("| %-24s |\n", "     MORNING ");
                                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                                            morningShiftList = morningShiftGenerator(day - 1, staffList);
                                                            displayStaffShift(0, morningShiftList, 0);

                                                            System.out.printf("\n+-%-24s-+\n", "------------------------");
                                                            System.out.printf("| %-24s |\n", "     NIGHT ");
                                                            System.out.printf("+-%-24s-+\n", "------------------------");
                                                            nightShiftList = nightShiftGenerator(day - 1, staffList);
                                                            displayStaffShift(0, nightShiftList, 0);
                                                            counter = 0;
                                                            shift = 0;
                                                            choice1 = 0;

                                                        } else if (tempString.equals("N")) {
                                                            counter = 0;
                                                        }
                                                    } while (counter == 1);

                                                }
                                            }
                                            if (entry == 0) {
                                                System.out.println("NO RECORD FOUND !");
                                            }
                                        }
                                    } while (shift != 0);
                                }
                            } while (selection != 0);
                        }
                    } while (day != 0);

                    break;
            }

        } while (choice != 0);
    }

    public static String genderSelector() {
        int choice;
        String male = "Male";
        String female = "Female";
        do {
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] MALE   ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [2] FEMALE  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            choice = Clinic.choiceGetter();
        } while (choice != 1 && choice != 2);
        if (choice == 1) {
            return male;

        } else {
            return female;
        }

    }

    public static String positionSelector() {
        int choice;
        String admin = "Admin";
        String doctor = "Doctor";
        do {
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] ADMIN   ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [2] DOCTOR  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            choice = Clinic.choiceGetter();
        } while (choice != 1 && choice != 2);
        if (choice == 1) {
            return admin;

        } else {
            return doctor;
        }

    }

    public static String shiftSelector() {
        int choice;
        String morning = "Morning";
        String night = "Night";
        do {
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [1] MORNING   ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  [2] NIGHT  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            choice = Clinic.choiceGetter();
        } while (choice != 1 && choice != 2);
        if (choice == 1) {
            return morning;

        } else {
            return night;
        }

    }

    public static void successfullyMsg(String string) {
        System.out.println("\n" + string + " Added successfully");
    }

    public static void anykey() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press enter to continue...");
        String temp = scanner.nextLine();
    }

    public static void staffNewName(ListInterface<Staff> staffList, int tempChoice) {
        int counter = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  CURRENT STAFF NAME ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("|  %-23s |\n", staffList.getEntry(tempChoice).staffName);
        System.out.printf("+-%-24s-+\n", "------------------------");
        printer("Please enter the staff's new name");
        String tempName = scanner.nextLine();
        do {
            printer("Are you sure you want to change the name?(Y/N)");
            String tempString = scanner.nextLine().toUpperCase();
            if (!tempString.equals("Y") && !tempString.equals("N")) {
                Clinic.errorMessage();
                counter = 1;
            } else if (tempString.equals("Y")) {
                counter = 0;
                staffList.getEntry(tempChoice).setStaffName(tempName);
                displayStaffDetails(1, staffList, tempChoice);
            } else if (tempString.equals("N")) {
                counter = 0;
            }
        } while (counter == 1);
    }

    public static void staffNewGender(ListInterface<Staff> staffList, int tempChoice) {
        int counter = 1;
        Scanner scanner = new Scanner(System.in);
        String gender = "MALE";
        if (staffList.getEntry(tempChoice).gender == 'M') {
            gender = "MALE";
        } else {
            gender = "FEMALE";
        }
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  CURRENT STAFF GENDER ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("|  %-23s |\n", gender);
        System.out.printf("+-%-24s-+\n", "------------------------");
        printer("Please select the staff's new gender");
        System.out.println("\n");
        String tempGender = genderSelector();
        do {
            Clinic.printer("Are you sure you want to change the gender?(Y/N)");
            String tempString = scanner.nextLine().toUpperCase();
            if (!tempString.equals("Y") && !tempString.equals("N")) {
                Clinic.errorMessage();
                counter = 1;
            } else if (tempString.equals("Y")) {
                counter = 0;
                staffList.getEntry(tempChoice).setGender(tempGender.charAt(0));
                displayStaffDetails(1, staffList, tempChoice);
            } else if (tempString.equals("N")) {
                counter = 0;
            }
        } while (counter == 1);

    }

    public static void staffNewAge(ListInterface<Staff> staffList, int tempChoice) {
        int counter = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "  CURRENT STAFF AGE ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("|  %-23s |\n", staffList.getEntry(tempChoice).staffAge);
        System.out.printf("+-%-24s-+\n", "------------------------");
        printer("Please enter the staff's new age");
        String tempAge = scanner.nextLine();
        do {
            Clinic.printer("Are you sure you want to change the age?(Y/N)");
            String tempString = scanner.nextLine().toUpperCase();
            if (!tempString.equals("Y") && !tempString.equals("N")) {
                Clinic.errorMessage();
                counter = 1;
            } else if (tempString.equals("Y")) {
                counter = 0;
                staffList.getEntry(tempChoice).setStaffAge(parseInt(tempAge));
                displayStaffDetails(1, staffList, tempChoice);
            } else if (tempString.equals("N")) {
                counter = 0;
            }
        } while (counter == 1);
    }

    public static void staffNewPosition(ListInterface<Staff> staffList, int tempChoice) {
        int counter = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("|%-24s |\n", "  CURRENT STAFF POSITION ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("|  %-23s |\n", staffList.getEntry(tempChoice).position);
        System.out.printf("+-%-24s-+\n", "------------------------");
        printer("Please enter the staff's new position");
        System.out.println("\n");
        String tempPosition = positionSelector();

        do {
            Clinic.printer("Are you sure you want to change the position?(Y/N)");
            String tempString = scanner.nextLine().toUpperCase();
            if (!tempString.equals("Y") && !tempString.equals("N")) {
                Clinic.errorMessage();
                counter = 1;
            } else if (tempString.equals("Y")) {
                counter = 0;
                staffList.getEntry(tempChoice).setPosition(tempPosition);
                displayStaffDetails(1, staffList, tempChoice);
            } else if (tempString.equals("N")) {
                counter = 0;
            }
        } while (counter == 1);

    }

    public static void staffNewPassword(ListInterface<Staff> staffList, int tempChoice) {
        ListInterface<Staff> adminList = adminList(staffList);
        int counter = 1;
        Admin admin = (Admin) adminList.getEntry(tempChoice);
        Scanner scanner = new Scanner(System.in);
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("|%-24s |\n", "  CURRENT STAFF PASSWORD ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("|  %-23s |\n", admin.getPassword());
        System.out.printf("+-%-24s-+\n", "------------------------");
        printer("Please enter the staff's new password");
        String tempPassword = scanner.nextLine();
        do {
            Clinic.printer("Are you sure you want to change the password?(Y/N)");
            String tempString = scanner.nextLine().toUpperCase();
            if (!tempString.equals("Y") && !tempString.equals("N")) {
                Clinic.errorMessage();
                counter = 1;
            } else if (tempString.equals("Y")) {
                counter = 0;
                admin.setPassword(tempPassword);
                displayStaffDetails(1, staffList, tempChoice);
            } else if (tempString.equals("N")) {
                counter = 0;
            }
        } while (counter == 1);
    }

}


 