package entity;

import adt.LinkedList;
import adt.ListInterface;
import adt.SortedArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Melvin Wong Wai Hung
 */
public class Patient {

    static Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    private static int patientCounter = 0;
    private static int consultationCounter = 0;
    private String patientID;
    private String patientIC;
    private String patientName;
    private char patientGender;
    private int patientAge;
    private String patientPhoneNo;
    private Consultation consultationInfo;
    private String[][] consultationHistory = new String[10][5];

    public Patient() {
        patientCounter++;
        this.patientID = String.format("P%04d", getPatientCounter());
    }

    public Patient(String patientID, String patientIC, String patientName, char patientGender, int patientAge, String patientPhoneNo, String[][] consultationHistory) {
        this.patientID = patientID;
        this.patientIC = patientIC;
        this.patientName = patientName;
        this.patientGender = patientGender;
        this.patientAge = patientAge;
        this.patientPhoneNo = patientPhoneNo;
        this.consultationHistory = consultationHistory;
    }

    public Patient(String patientID) {
        this.patientID = patientID;
    }

    public static void setPatientCounter(int patientCounter) {
        Patient.patientCounter = patientCounter;
    }

    public static int getPatientCounter() {
        return patientCounter;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientIC() {
        return patientIC;
    }

    public void setPatientIC(String patientIC) {
        this.patientIC = patientIC;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public char getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(char patientGender) {
        this.patientGender = patientGender;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPhoneNo() {
        return patientPhoneNo;
    }

    public void setPatientPhoneNo(String patientPhoneNo) {
        this.patientPhoneNo = patientPhoneNo;
    }

    public String[][] getConsultationHistory() {
        return consultationHistory;
    }

    public void setConsultationHistory(Consultation consultationHistory, SortedArrayList<PrescribedMedications> medicationList) {
        this.consultationHistory = fillConsultationHistoryArray(consultationHistory, medicationList);
        consultationCounter++;
    }

    public static boolean findPatientByID(String patientID, ListInterface<Patient> patientList) {
        for (int i = 0; i < patientList.getNumberOfEntries(); i++) {
            var currentEntry = patientList.getEntry(i + 1);

            if (currentEntry.getPatientID().equalsIgnoreCase(patientID)) {
                return true;
            }
        }

        return false;
    }

    public static void addPatient(ListInterface<Patient> patientList) {
        Patient patient = new Patient();
        String tempPatientIC, tempPatientName, tempPatientGender, tempPatientAge, tempPatientPhoneNo;
        char addConfirmation = 'Y';

        //Patient ID will be auto assigned in constructor
        do {
            do {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       PATIENT IC       ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "   EG. 020523-14-1151   ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the patient IC > ");

                tempPatientIC = scanner.next();
            } while (!validateIC(tempPatientIC));
            patient.setPatientIC(tempPatientIC);

            do {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      PATIENT NAME      ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      EG. DYLAN         ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the patient name > ");

                tempPatientName = scanner.next();
            } while (!validateStringInput(tempPatientName));
            patient.setPatientName(tempPatientName);

            do {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "     PATIENT GENDER     ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "     EG. M              ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the patient gender > ");

                tempPatientGender = scanner.next();
            } while (!validateGender(tempPatientGender));
            patient.setPatientGender(tempPatientGender.toUpperCase().charAt(0));

            do {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      PATIENT AGE       ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      EG. 18            ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the patient age > ");

                tempPatientAge = scanner.next();
            } while (!validateIntegerInput(tempPatientAge));
            patient.setPatientAge(Integer.valueOf(tempPatientAge));

            do {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "   PATIENT PHONE NO.    ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "   EG. 011-33244656     ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("\nEnter the patient phone no. > ");

                tempPatientPhoneNo = scanner.next();
            } while (!validatePhoneNo(tempPatientPhoneNo));
            patient.setPatientPhoneNo(tempPatientPhoneNo);

            do {
                displayPatientDetails(patient.getPatientID(), tempPatientIC, tempPatientName, tempPatientGender.toUpperCase().charAt(0), Integer.valueOf(tempPatientAge), tempPatientPhoneNo);

                System.out.print("Are you sure you want to add this patient into the system? (Y = yes or N = no) > ");
                addConfirmation = scanner.next().charAt(0);

                switch (addConfirmation) {
                    case 'Y':
                    case 'y':
                        patientList.add(patient);
                        System.out.println("The patient has been added successfully.\n");
                        addConfirmation = 'N';
                        break;
                    case 'N':
                    case 'n':
                        System.out.print("Do you want to key in the details again? (Y = yes or N = no) > ");
                        addConfirmation = scanner.next().charAt(0);
                        break;
                    default:
                        addConfirmation = 'A';
                        System.out.println("Invalid input entered. Please try again.\n");
                }
            } while (addConfirmation == 'A');

        } while (addConfirmation == 'Y' || addConfirmation == 'y');
    }

    public static void updatePatient(ListInterface<Patient> patientList) {
        int updateSelection = 0;
        String tempPatientIC, tempPatientName, tempPatientGender, tempPatientAge, tempPatientPhoneNo;
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "       PATIENT ID       ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "       EG. P0001        ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.print("\nEnter the patient ID > ");
        String patientID = scanner.next();

        do {
            if (findPatientByID(patientID, patientList)) {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       PATIENT INFO     ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[1] Patient IC");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[2] Patient Name");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[3] Patient Gender");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[4] Patient Age");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[5] Patient Phone No.");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[0] Back");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("Select a patient info to be updated (1 - 5, 0 to back to previous page) > ");

                updateSelection = scanner.nextInt();

                for (int i = 0; i < patientList.getNumberOfEntries(); i++) {
                    var currentEntry = patientList.getEntry(i + 1);

                    if (currentEntry.getPatientID().equalsIgnoreCase(patientID)) {
                        switch (updateSelection) {
                            case 1:
                                do {
                                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                                    System.out.printf("| %-24s |\n", "   CURRENT PATIENT IC   ");
                                    System.out.printf("+-%-24s-+\n", "------------------------");
                                    System.out.printf("|    %-21s |\n", currentEntry.getPatientIC());
                                    System.out.printf("+-%-24s-+\n", "------------------------");

                                    System.out.print("\nPlease enter the new patient IC (Eg.: 020523-14-1151): ");
                                    tempPatientIC = scanner.next();
                                } while (!validateIC(tempPatientIC));
                                if (updatePatientConfirmation()) {
                                    currentEntry.setPatientIC(tempPatientIC);
                                }
                                break;
                            case 2:
                                do {
                                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                                    System.out.printf("| %-24s |\n", "  CURRENT PATIENT NAME ");
                                    System.out.printf("+-%-24s-+\n", "------------------------");
                                    System.out.printf("|  %-23s |\n", currentEntry.getPatientName());
                                    System.out.printf("+-%-24s-+\n", "------------------------");

                                    System.out.print("\nPlease enter the new patient name: ");
                                    tempPatientName = scanner.next();
                                } while (!validateStringInput(tempPatientName));
                                if (updatePatientConfirmation()) {
                                    currentEntry.setPatientName(tempPatientName);
                                }
                                break;
                            case 3:
                                do {
                                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                                    System.out.printf("| %-24s |\n", " CURRENT PATIENT GENDER");
                                    System.out.printf("+-%-24s-+\n", "------------------------");
                                    System.out.printf("|  %-23s |\n", currentEntry.getPatientGender());
                                    System.out.printf("+-%-24s-+\n", "------------------------");

                                    System.out.print("\nPlease enter the new patient gender: ");
                                    tempPatientGender = scanner.next();
                                } while (!validateGender(tempPatientGender));
                                if (updatePatientConfirmation()) {
                                    currentEntry.setPatientGender(tempPatientGender.toUpperCase().charAt(0));
                                }
                                break;
                            case 4:
                                do {
                                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                                    System.out.printf("| %-24s |\n", " CURRENT PATIENT AGE  ");
                                    System.out.printf("+-%-24s-+\n", "------------------------");
                                    System.out.printf("|  %-23s |\n", currentEntry.getPatientAge());
                                    System.out.printf("+-%-24s-+\n", "------------------------");

                                    System.out.print("\nPlease enter the new patient age: ");
                                    tempPatientAge = scanner.next();
                                } while (!validateIntegerInput(tempPatientAge));
                                if (updatePatientConfirmation()) {
                                    currentEntry.setPatientAge(Integer.valueOf(tempPatientAge));
                                }
                                break;
                            case 5:
                                do {
                                    System.out.printf("\n+-%-24s-+\n", "------------------------");
                                    System.out.printf("| %-24s|\n", "CURRENT PATIENT PHONE NO.");
                                    System.out.printf("+-%-24s-+\n", "------------------------");
                                    System.out.printf("| %-23s  |\n", currentEntry.getPatientPhoneNo());
                                    System.out.printf("+-%-24s-+\n", "------------------------");

                                    System.out.print("\nPlease enter the new patient phone no. (Eg.: 011-33244656): ");
                                    tempPatientPhoneNo = scanner.next();
                                } while (!validatePhoneNo(tempPatientPhoneNo));
                                if (updatePatientConfirmation()) {
                                    currentEntry.setPatientPhoneNo(tempPatientPhoneNo);
                                }
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid selection entered. Please try again.");
                        }
                    }
                }
            } else {
                System.out.println("Patient does not exist.");
            }
        } while (updateSelection != 0);
    }

    public static void removePatient(ListInterface<Patient> patientList) {
        char removeConfirmation;
        System.out.printf("\n+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "       PATIENT ID       ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "       EG. P0001        ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.print("\nEnter the patient ID > ");
        String patientID = scanner.next();

        if (findPatientByID(patientID, patientList)) {
            for (int i = 0; i < patientList.getNumberOfEntries(); i++) {
                var currentEntry = patientList.getEntry(i + 1);

                if (currentEntry.getPatientID().equalsIgnoreCase(patientID)) {
                    displayPatientDetails(currentEntry.getPatientID(), currentEntry.getPatientIC(), currentEntry.getPatientName(), currentEntry.getPatientGender(), currentEntry.getPatientAge(), currentEntry.getPatientPhoneNo());

                    do {
                        System.out.print("Are you sure you want to remove this patient from the system? (Y = yes or N = no) > ");
                        removeConfirmation = scanner.next().charAt(0);

                        switch (removeConfirmation) {
                            case 'Y':
                            case 'y':
                                patientList.remove(i + 1);
                                System.out.println("The patient has been removed from the system successfully.\n");
                                break;
                            case 'N':
                            case 'n':
                                System.out.println("Failed to remove the patient from the system.\n");
                                break;
                            default:
                                removeConfirmation = 'A';
                                System.out.println("Invalid input entered. Please try again.\n");
                        }
                    } while (removeConfirmation == 'A');
                }
            }
        } else {
            System.out.println("Patient does not exist.");
        }
    }

    public static void displayPatient(ListInterface<Patient> patientList) {
        int menuSelection;

        if (patientList.isEmpty()) {
            System.out.println("There are no patients registered in the system currently.");
        } else {
            do {
                System.out.printf("\n+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       VIEW OPTION      ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [1] ALL PATIENTS     ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  [2] SINGLE PATIENT   ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.print("Your option: ");

                menuSelection = scanner.nextInt();

                switch (menuSelection) {
                    case 1:
                        for (int i = 0; i < patientList.getNumberOfEntries(); i++) {
                            if (patientList.isEmpty()) {
                                System.out.println("There are no patients registered in the system yet.");
                            }

                            var currentEntry = patientList.getEntry(i + 1);
                            displayPatientDetails(currentEntry.getPatientID(), currentEntry.getPatientIC(), currentEntry.getPatientName(), currentEntry.getPatientGender(), currentEntry.getPatientAge(), currentEntry.getPatientPhoneNo());
                            displayConsultationHistory(patientList, currentEntry.getPatientID());
                        }
                        break;
                    case 2:
                        System.out.printf("\n+-%-24s-+\n", "------------------------");
                        System.out.printf("| %-24s |\n", "       PATIENT ID       ");
                        System.out.printf("+-%-24s-+\n", "------------------------");
                        System.out.printf("| %-24s |\n", "       EG. P0001        ");
                        System.out.printf("+-%-24s-+\n", "------------------------");
                        System.out.print("\nEnter the patient ID > ");
                        String patientID = scanner.next();

                        for (int i = 0; i < patientList.getNumberOfEntries(); i++) {
                            var currentEntry = patientList.getEntry(i + 1);

                            if (currentEntry.getPatientID().equalsIgnoreCase(patientID)) {
                                displayPatientDetails(currentEntry.getPatientID(), currentEntry.getPatientIC(), currentEntry.getPatientName(), currentEntry.getPatientGender(), currentEntry.getPatientAge(), currentEntry.getPatientPhoneNo());
                                displayConsultationHistory(patientList, patientID);
                                break;
                            } else if ((i == patientList.getNumberOfEntries() - 1) && !currentEntry.getPatientID().equalsIgnoreCase(patientID)) { //Indicates the entire patient list doesn't contain the patientID
                                System.out.println("Patient does not exist.");
                                break;
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid option selected. Please try again.\n");
                }
            } while (menuSelection != 1 && menuSelection != 2);
        }
    }

    public static boolean validateIntegerInput(String input) {
        if (input.length() == 0) {
            System.out.println("Invalid input entered. Please enter again.\n");
            return false;
        } else {
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    System.out.println("Invalid input entered. Please enter again.\n");
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean validateStringInput(String input) {
        if (input.length() == 0) {
            System.out.println("Invalid input entered. Please enter again.\n");
            return false;
        }

        return true;
    }

    public static boolean validateIC(String IC) {
        String regex = "([0-9][0-9])((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\\-([0-9][0-9])\\-([0-9][0-9][0-9][0-9])";

        if (!IC.matches(regex) || IC.length() == 0) {
            System.out.println("Invalid input entered. Please enter again.\n");
            return false;
        }

        return true;
    }

    public static boolean validateGender(String gender) {
        if (gender.length() > 1 || gender.length() == 0) {
            System.out.println("Invalid input entered. Please enter again.\n");
            return false;
        }

        if (gender.toUpperCase().charAt(0) != 'M' && gender.toUpperCase().charAt(0) != 'F') {
            System.out.println("Invalid input entered. Please enter again.\n");
            return false;
        }

        return true;
    }

    public static boolean validatePhoneNo(String phoneNo) {
        String regex = "^(\\+?6?01)[02-46-9]-*[0-9]{7}$|^(\\+?6?01)[1]-*[0-9]{8}$"; //If 011, has 11 digit, else 10 digit, 015 invalid 

        if (!phoneNo.matches(regex) || phoneNo.length() == 0) {
            System.out.println("Invalid input entered. Please enter again.\n");
            return false;
        }

        return true;
    }

    public static ListInterface<Patient> autoAddPatient() {
        ListInterface<Patient> patientList = new LinkedList<>();
        String patient1Consultation[][] = new String[10][5];
        patient1Consultation[0][0] = "2022-09-01 08:17:40";
        patient1Consultation[0][1] = "S003";
        patient1Consultation[0][2] = "Allergies";
        patient1Consultation[0][3] = "Paracetamol";
        patient1Consultation[0][4] = "3";

        patientList.add(new Patient("P0001", "020523-14-3311", "Mel", 'M', 20, "011-33244656", patient1Consultation));
        patientList.add(new Patient("P0002", "020416-10-9999", "Wingkren", 'M', 12, "011-31819117", new String[10][5]));
        patientList.add(new Patient("P0003", "020719-14-1818", "Kel", 'M', 29, "016-9976640", new String[10][5]));
        patientList.add(new Patient("P0004", "020912-14-8787", "Stone", 'M', 23, "012-6373705", new String[10][5]));
        patientList.add(new Patient("P0005", "020926-14-2222", "Fy", 'F', 18, "012-6168695", new String[10][5]));

        consultationCounter = 1;

        return patientList;
    }

    public static boolean updatePatientConfirmation() {
        char editConfirmation = 'Y';
        boolean confirm = false;
        do {
            System.out.print("Are you sure you want to update the patient details? (Y = yes or N = no) > ");
            editConfirmation = scanner.next().charAt(0);

            switch (editConfirmation) {
                case 'Y':
                case 'y':
                    System.out.println("The patient details has been updated successfully.\n");
                    confirm = true;
                    break;
                case 'N':
                case 'n':
                    System.out.println("Failed to update the patient details.\n");
                    break;
                default:
                    editConfirmation = 'A';
                    System.out.println("Invalid input entered. Please try again.\n");
            }
        } while (editConfirmation == 'A');

        return confirm;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientID=" + patientID + ", patientIC=" + patientIC + ", patientName=" + patientName + ", patientGender=" + patientGender + ", patientAge=" + patientAge + ", patientPhoneNo=" + patientPhoneNo + ", consultationInfo=" + consultationInfo + '}';
    }

    public static void displayPatientDetails(String patientID, String patientIC, String patientName, char patientGender, int patientAge, String patientPhoneNo) {
        System.out.println("");
        System.out.println(String.format("+-%-3s---%-15s---%-25s-------%-15s---%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-40s%-7s%-40s     |", "", "PATIENT DETAILS", ""));

        System.out.println(String.format("+-%-25s-+-%-25s-+-%-15s-+-%-25s-+", "-------------------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-25s | %-25s | %-15s | %-25s |", "Patient ID", patientID, "Patient IC", patientIC));
        System.out.println(String.format("+-%-25s-+-%-25s-+-%-15s-+-%-25s-+", "-------------------------", "-------------------------", "---------------", "-------------------------"));

        System.out.println(String.format("| %-71s | %-25s |", "Patient Name", patientName));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
        System.out.println(String.format("| %-71s | %-25s |", "Patient Gender", patientGender));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
        System.out.println(String.format("| %-71s | %-25s |", "Patient Age", patientAge));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
        System.out.println(String.format("| %-71s | %-25s |", "Patient Phone No.", patientPhoneNo));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-----------------------------------------------------------------------", "-------------------------"));
    }

    public String[][] fillConsultationHistoryArray(Consultation consultationInfo, SortedArrayList<PrescribedMedications> medicationList) {
        consultationHistory[consultationCounter][0] = formattedDateTime(consultationInfo.getStartTime());
        consultationHistory[consultationCounter][1] = consultationInfo.getStaff().getStaffID();
        consultationHistory[consultationCounter][2] = consultationInfo.getDiagnosis();
        consultationHistory[consultationCounter][3] = medicationList.getEntry(0).getMedicine().getName();
        consultationHistory[consultationCounter][4] = String.valueOf(medicationList.getEntry(0).getRequiredQuantity());

        return consultationHistory;
    }

    public static void displayConsultationHistory(ListInterface<Patient> patientList, String patientID) {
        for (int i = 0; i < patientList.getNumberOfEntries(); i++) {
            var currentEntry = patientList.getEntry(i + 1);

            if (currentEntry.getPatientID().equalsIgnoreCase(patientID)) {
                if (currentEntry.consultationHistory[0][0] == null) {
                    System.out.println(String.format("| %-37s%-7s%-39s |", "", "NO CONSULTATION HISTORY", ""));
                    System.out.println(String.format("+-%-67s---%-25s-+\n", "-----------------------------------------------------------------------", "-------------------------"));
                } else {
                    System.out.println(String.format("| %-40s%-7s%-39s |", "", "CONSULTATION HISTORY", ""));
                    System.out.println(String.format("+-----+%-19s+%-17s+%-18s+%-23s+%-10s+", "-----------------------", "-----------------", "------------------", "-----------------------", "----------"));
                    System.out.println(String.format("| %-3s | %-21s | %-15s | %-15s | %-14s | %-8s |", "No.", "Consultation Date", "Doctor Assigned", "Diagnosis Result", "Prescribed Medication", "Quantity"));
                    System.out.println(String.format("+-----+%-19s+%-17s+%-18s+%-23s+%-10s+", "-----------------------", "-----------------", "------------------", "-----------------------", "----------"));

                    for (int j = 0; j < currentEntry.consultationHistory.length; j++) {
                        if (currentEntry.consultationHistory[j][0] != null) {
                            System.out.println(String.format("| %-3s | %-21s | %-15s | %-16s | %-21s | %-8s |", String.valueOf(j + 1) + ".", currentEntry.consultationHistory[j][0], currentEntry.consultationHistory[j][1], currentEntry.consultationHistory[j][2], currentEntry.consultationHistory[j][3], currentEntry.consultationHistory[j][4]));
                            System.out.println(String.format("+-----+%-19s+%-17s+%-18s+%-23s+%-10s+", "-----------------------", "-----------------", "------------------", "-----------------------", "----------"));
                        }
                    }
                }
            }
        }
    }

    public static String formattedDateTime(LocalDateTime startTime) {
        DateTimeFormatter sdfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(startTime);
        return strDate;
    }

    public void patientModule(ListInterface<Patient> patientList) {
        int menuSelection;

        do {
            System.out.printf("\n+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "     IN-PATIENT MENU    ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "[1] Add New Patient");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "[2] Update Patient");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "[3] Remove Patient");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "[4] Display Patient Info");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "[0] Exit");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.print("Enter Your Choice > ");

            menuSelection = scanner.nextInt();

            switch (menuSelection) {
                case 1:
                    addPatient(patientList);
                    break;
                case 2:
                    updatePatient(patientList);
                    break;
                case 3:
                    removePatient(patientList);
                    break;
                case 4:
                    displayPatient(patientList);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option selected. Please try again.\n");
            }

        } while (menuSelection != 0);
    }

}
