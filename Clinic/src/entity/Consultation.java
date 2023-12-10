/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import adt.LinkedList;
import adt.LinkedQueue;
import adt.ListInterface;
import adt.QueueInterface;
import java.util.Scanner;
import java.time.LocalDateTime;
import adt.SortedArrayList;
import adt.SortedLinkedList;

enum qStatus {
    WAITING, CONSULTING
}

/**
 *
 * @author Yau Hui Yi
 */
public class Consultation {

    static QueueInterface<Consultation> queueR01 = new LinkedQueue<>();
    static QueueInterface<Consultation> queueR02 = new LinkedQueue<>();
    static ListInterface<Patient> patientList = new LinkedList<>();

    public static int counter = 1;

    private String queueNo;
    private LocalDateTime startTime;
    private qStatus queueStatus;
    private Patient patient;
    private Staff staff = new Staff();
    private SortedArrayList<PrescribedMedications> medicationList = new SortedArrayList<>();
    private String diagnosis;
    private String roomNo;
    private double fees = 20.0;
    private static int currentQueueNo = 0;
    private Payment payment = new Payment();

    public Consultation() {
        this.queueNo = makeQueueNo();
        Consultation.currentQueueNo++;
    }

    public Consultation(String roomNo, Patient patient, Staff staff) {
        this.startTime = LocalDateTime.now();
        this.roomNo = roomNo;
        this.setQueueStatus(qStatus.WAITING);
        this.patient = patient;
        this.staff = staff;
        this.queueNo = makeQueueNo();
        this.diagnosis = "null";

        Consultation.currentQueueNo++;

    }

    public Consultation(Patient patient, Staff staff, SortedArrayList<PrescribedMedications> medicationList) {
        this.patient = patient;
        this.staff = staff;
        this.medicationList = medicationList;
    }

    private static String makeQueueNo() {
        if (currentQueueNo < 10) {
            return "Q00" + currentQueueNo;
        } else if (currentQueueNo < 100) {
            return "Q0" + currentQueueNo;
        }

        return "Q" + currentQueueNo;
    }

    //getter and setter
    public static QueueInterface<Consultation> getQueueR01() {
        return queueR01;
    }

    public static void setQueueR01(QueueInterface<Consultation> queueR01) {
        Consultation.queueR01 = queueR01;
    }

    public static QueueInterface<Consultation> getQueueR02() {
        return queueR02;
    }

    public static void setQueueR02(QueueInterface<Consultation> queueR02) {
        Consultation.queueR02 = queueR02;
    }

    public static ListInterface<Patient> getPatientList() {
        return patientList;
    }

    public static void setPatientList(ListInterface<Patient> patientList) {
        Consultation.patientList = patientList;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Consultation.counter = counter;
    }

    public String getQueueNo() {
        return queueNo;
    }

    public void setQueueNo(String queueNo) {
        this.queueNo = queueNo;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public qStatus getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(qStatus queueStatus) {
        this.queueStatus = queueStatus;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public SortedArrayList<PrescribedMedications> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(SortedArrayList<PrescribedMedications> medicationList) {
        this.medicationList = medicationList;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public static int getCurrentQueueNo() {
        return currentQueueNo;
    }

    public static void setCurrentQueueNo(int currentQueueNo) {
        Consultation.currentQueueNo = currentQueueNo;
    }

    @Override
    public String toString() {
        return String.format(
                "| [%s] | Patient: %-20s    | Doctor: %-25s    | Room No: %s | Arrival Time: %-2d:%-2d | Diagnosis: %-15s   | Status: %-11s |\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+",
                queueNo, patient.getPatientName(), staff.getStaffName(), roomNo, startTime.getHour(), startTime.getMinute(), diagnosis, queueStatus);
    }

    //find consultation number
    public static String findConsultation(String queueNo) {
        for (Consultation consultation : queueR01) {
            if (consultation.getQueueNo().equalsIgnoreCase(queueNo)) {
                return "R01";
            }
        }

        for (Consultation consultation : queueR02) {
            if (consultation.getQueueNo().equalsIgnoreCase(queueNo)) {
                return "R02";
            }
        }

        return "not found";
    }

    //search consultation
    public static void searchConsultation() {

        Scanner searchScanner = new Scanner(System.in);
        boolean found = false;

        if (queueR01.isEmpty() && queueR02.isEmpty()) {
            System.out.println("\nThere are no upcoming consultation in the system currently.");
        } else {
            System.out.print("Please enter the Consultation Queue Number: ");
            String consultationNo = searchScanner.next();

            if (findConsultation(consultationNo) == "R01") {
                for (int i = 0; i < queueR01.getNumberOfEntries(); i++) {
                    var currentEntry = queueR01.getEntry(i + 1);

                    if (currentEntry.getQueueNo().equalsIgnoreCase(consultationNo)) {
                        found = true;
                        System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                        System.out.printf("|  %-170s|\n", "CONSULTATION FOUND");
                        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                        System.out.println(currentEntry);
                    }
                }
            } else if (findConsultation(consultationNo) == "R02") {
                for (int i = 0; i < queueR02.getNumberOfEntries(); i++) {
                    var currentEntry = queueR02.getEntry(i + 1);

                    if (currentEntry.getQueueNo().equalsIgnoreCase(consultationNo)) {
                        found = true;
                        System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                        System.out.printf("|  %-170s|\n", "CONSULTATION FOUND");
                        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                        System.out.println(currentEntry);
                    }
                }
            }

            if (!found) {
                System.out.println("Consultation does not exist.");
            }
        }
    }

    //display consultation
    public static void displayConsultation() {
        boolean foundConsulting1 = false, foundWaiting1 = false;
        boolean foundConsulting2 = false, foundWaiting2 = false;

        System.out.println("");

        if (queueR01.isEmpty() && queueR02.isEmpty()) {
            System.out.println("There are no upcoming consultation in the system currently.");
        } else {
            //display current consultation 

            System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("|  %-170s|\n", "ROOM R01 CURRENTLY CONSULTATING");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

            for (int i = 0; i < queueR01.getNumberOfEntries(); i++) {
                var currentEntry = queueR01.getEntry(i + 1);

                if (currentEntry.getQueueStatus() == qStatus.CONSULTING) {
                    foundConsulting1 = true;
                    System.out.println(currentEntry);
                }
            }

            if (!foundConsulting1) {
                System.out.printf("|  %-170s|\n", "NONE.");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            }

            System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("|  %-170s|\n", "ROOM R02 CURRENTLY CONSULTATING");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

            for (int i = 0; i < queueR02.getNumberOfEntries(); i++) {
                var currentEntry = queueR02.getEntry(i + 1);

                if (currentEntry.getQueueStatus() == qStatus.CONSULTING) {
                    foundConsulting2 = true;
                    System.out.println(currentEntry);
                }
            }

            if (!foundConsulting2) {
                System.out.printf("|  %-170s|\n", "NONE.");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            }

            //display waiting consultation queue
            System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("|  %-170s|\n", "ROOM R01 CURRENTLY WAITING CONSULTATION");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

            for (int i = 0; i < queueR01.getNumberOfEntries(); i++) {
                var currentEntry = queueR01.getEntry(i + 1);

                if (currentEntry.getQueueStatus() == qStatus.WAITING) {
                    foundWaiting1 = true;
                    System.out.println(currentEntry);
                }
            }

            if (!foundWaiting1) {
                System.out.printf("|  %-170s|\n", "NONE.");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            }

            System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("|  %-170s|\n", "ROOM R02 CURRENTLY WAITING CONSULTATION");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

            for (int i = 0; i < queueR02.getNumberOfEntries(); i++) {
                var currentEntry = queueR02.getEntry(i + 1);

                if (currentEntry.getQueueStatus() == qStatus.WAITING) {
                    foundWaiting2 = true;
                    System.out.println(currentEntry);
                }
            }

            if (!foundWaiting2) {
                System.out.printf("|  %-170s|\n", "NONE.");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            }

        }
    }

    //add consultation
    public static void addConsultation(ListInterface<Patient> patientList, ListInterface<Staff> staffList) {

        Scanner adddScanner = new Scanner(System.in);
        boolean patientFound = false;

        ListInterface<Staff> doctorList = Staff.doctorList(staffList);
        System.out.println("\nEnter Details To Create New Consultation: ");
        System.out.print("Enter Patient ID: ");
        String patientid = adddScanner.nextLine();

        //check patient existence
        Patient patient = null;
        for (int i = 1; i <= patientList.getNumberOfEntries(); i++) {
            if (patientList.getEntry(i).getPatientID().equals(patientid)) {
                patient = patientList.getEntry(i);
                patientFound = true;
            }
        }

        if (patientFound) {
            Consultation consultation = new Consultation();
            if (counter % 2 != 0) {
                consultation.setStaff(doctorList.getEntry(1));
                consultation.setPatient(patient);
                consultation.setRoomNo("R01");
                consultation.setQueueStatus(qStatus.WAITING);
                consultation.setStartTime(LocalDateTime.now());

                queueR01.enqueue(consultation);
                if (queueR01.getFront().getQueueStatus() == qStatus.WAITING) {
                    queueR01.getFront().setQueueStatus(qStatus.CONSULTING);
                }

            } else {
                consultation.setStaff(doctorList.getEntry(2));
                consultation.setPatient(patient);
                consultation.setRoomNo("R02");
                consultation.setQueueStatus(qStatus.WAITING);
                consultation.setStartTime(LocalDateTime.now());

                queueR02.enqueue(consultation);
                if (queueR02.getFront().getQueueStatus() == qStatus.WAITING) {
                    queueR02.getFront().setQueueStatus(qStatus.CONSULTING);
                }

            }

            System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("|  %-170s|\n", "CONSULTATION ADDED");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

            System.out.println(consultation);
            counter++;
        } else {
            System.out.println("Patient not found.");
        }

    }

    //update consultation
    public static void updateConsultation(ListInterface<Patient> patientList, ListInterface<Staff> staffList) {
        Scanner updateScanner = new Scanner(System.in);
        int choice = 2;

        if (queueR01.isEmpty() && queueR02.isEmpty()) {
            System.out.println("\nThere are no upcoming consultation in the system currently.");
        } else {
            //display existing consultations in the system
            System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.printf("|  %-170s|\n", "EXISTING CONSULTATIONS");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

            for (Consultation consultationR01 : queueR01) {
                System.out.println(consultationR01);
            }

            for (Consultation consultationR02 : queueR02) {
                System.out.println(consultationR02);
            }

            System.out.print("\nPlease enter the consultation number to update: ");
            String consultationNo = updateScanner.next();

            if (findConsultation(consultationNo) != "not found") {
                //Consultation consultation
                System.out.println("Which Consultatoin info would you like to update?");
                System.out.println("1. Patient");
                System.out.println("2. Nothing\n");
                System.out.print("Your option: ");
                choice = updateScanner.nextInt();
            }
            if (findConsultation(consultationNo) == "R01") {
                for (int i = 0; i < queueR01.getNumberOfEntries(); i++) {
                    var currentEntry = queueR01.getEntry(i + 1);

                    if (currentEntry.getQueueNo().equalsIgnoreCase(consultationNo)) {
                        switch (choice) {
                            case 1:
                                System.out.printf("\nEnter New Patient ID: ");
                                updateScanner.nextLine();
                                String patientid = updateScanner.nextLine();

                                //check patient existence
                                for (int n = 1; n <= patientList.getNumberOfEntries(); n++) {
                                    if (patientList.getEntry(n).getPatientID().equals(patientid)) {
                                        Patient patient = patientList.getEntry(n);
                                        currentEntry.setPatient(patient);
                                        System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                                        System.out.printf("|  %-170s|\n", "CONSULTATION UPDATED");
                                        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

                                        System.out.println(currentEntry);
                                        break;
                                    }
                                }
                                break;
                            case 2:
                                break;

                            default:
                                System.out.println("Invalid selection entered. Please try again.");
                        }
                    }
                }
            } else if (findConsultation(consultationNo) == "R02") {
                for (int i = 0; i < queueR02.getNumberOfEntries(); i++) {
                    var currentEntry = queueR02.getEntry(i + 1);

                    if (currentEntry.getQueueNo().equalsIgnoreCase(consultationNo)) {
                        switch (choice) {
                            case 1:
                                System.out.printf("\nEnter New Patient ID: ");
                                updateScanner.nextLine();
                                String patientid = updateScanner.nextLine();

                                //check patient existence
                                for (int n = 1; n <= patientList.getNumberOfEntries(); n++) {
                                    if (patientList.getEntry(n).getPatientID().equals(patientid)) {
                                        Patient patient = patientList.getEntry(n);
                                        currentEntry.setPatient(patient);
                                        System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                                        System.out.printf("|  %-170s|\n", "CONSULTATION UPDATED");
                                        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

                                        System.out.println(currentEntry);
                                        break;
                                    }
                                }
                                break;
                            case 2:
                                break;

                            default:
                                System.out.println("Invalid selection entered. Please try again.");
                        }
                    }
                }
            } else {
                System.out.println("Consultation does not exist.");
            }
        }
    }

    //current consultation stuff
    public void ongoingConsultation(SortedLinkedList<Payment> paymentList, SortedArrayList<Medicine> medicineList) {
        Scanner ongoingScanner = new Scanner(System.in);
        int diagnosisSelection;
        String queue = "not found";
        String diagnosisResult = "Allergies";
        boolean hasOngoing = false;

        for (int i = 0; i < queueR01.getNumberOfEntries(); i++) {
            var currentEntry = queueR01.getEntry(i + 1);

            if (currentEntry.getQueueStatus() == qStatus.CONSULTING) {
                System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.printf("|  %-170s|\n", "CURRENT CONSULTATION IN ROOM R01 ");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                hasOngoing = true;
                System.out.println(currentEntry);
            }
        }

        for (int i = 0; i < queueR02.getNumberOfEntries(); i++) {
            var currentEntry = queueR02.getEntry(i + 1);

            if (currentEntry.getQueueStatus() == qStatus.CONSULTING) {
                System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.printf("|  %-170s|\n", "CURRENT CONSULTATION IN ROOM R02");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                hasOngoing = true;
                System.out.println(currentEntry);
            }
        }

        if (hasOngoing) {
            System.out.print("Enter Ongoing Consultation Number: ");
            String consultationNo = ongoingScanner.nextLine();

            //check consultation existence
            Consultation consultation = null;
            if (findConsultation(consultationNo) == "R01") {
                for (int n = 1; n <= queueR01.getNumberOfEntries(); n++) {
                    if (queueR01.getEntry(n).getQueueNo().equals(consultationNo)) {
                        consultation = queueR01.getEntry(n);
                        queue = "R01";
                    }
                }
            } else if (findConsultation(consultationNo) == "R02") {
                for (int n = 1; n <= queueR02.getNumberOfEntries(); n++) {
                    if (queueR02.getEntry(n).getQueueNo().equals(consultationNo)) {
                        consultation = queueR02.getEntry(n);
                        queue = "R02";
                    }
                }

            }

            if (consultation != null && consultation.getQueueStatus() == qStatus.CONSULTING) {
                do {

                    System.out.println("\nPlease enter diagnosis result: ");
                    System.out.println("1. Allergies");
                    System.out.println("2. Colds and Flu");
                    System.out.println("3. Diarrhea");
                    System.out.println("4. Others\n");
                    System.out.print("Your option: ");

                    diagnosisSelection = ongoingScanner.nextInt();

                    switch (diagnosisSelection) {
                        case 1:
                            diagnosisResult = "Allergies";
                            break;
                        case 2:
                            diagnosisResult = "Colds and Flu";
                            break;
                        case 3:
                            diagnosisResult = "Diarrhea";
                            break;
                        case 4:
                            System.out.print("Please enter the diagnosis result: ");
                            diagnosisResult = ongoingScanner.next();
                            break;
                        default:
                            System.out.println("Invalid option selected. Please try again.\n");
                    }
                } while (diagnosisSelection < 1 || diagnosisSelection > 4);
                //Load diagnosis into consultation
                consultation.setDiagnosis(diagnosisResult);

                //Enter medicine**
                //SortedArrayList<Medicine> test = Medicine.dummyData();
                medicationList = new PrescribedMedications().prescribedMedicationsModule(medicineList);

                consultation.setMedicationList(medicationList);
                if (!medicationList.isEmpty()) {
                    consultation.getPatient().setConsultationHistory(consultation, medicationList);
                }
                char paymentConfirmation = 'Y';

                int paymentListNo = paymentList.getNumberOfEntries();

                do {
                    do {
                        System.out.println("");
                        paymentConfirmation = Payment.readChar("Proceed to payment (Y = yes or N = no)?");
                    } while (Payment.validateChar(paymentConfirmation) == false);

                    switch (paymentConfirmation) {
                        case 'Y':
                        case 'y':
                            payment.createPayment(new Payment(consultation), paymentList);
                            break;
                        case 'N':
                        case 'n':
                            Payment.displayContinue();
                            break;
                        default:
                            Payment.displayWarningMessage("You've entered an invalid character!");
                            break;
                    }
                } while (!(paymentConfirmation == 'Y' || paymentConfirmation == 'y' || paymentConfirmation == 'N' || paymentConfirmation == 'n'));

                if (paymentListNo != paymentList.getNumberOfEntries()) {
                    // remove ONGOING
                    if (queue == "R01") {
                        queueR01.dequeue();
                        if (!queueR01.isEmpty()) {
                            queueR01.getFront().setQueueStatus(qStatus.CONSULTING);
                        }
                    } else if (queue == "R02") {
                        queueR02.dequeue();
                        if (!queueR02.isEmpty()) {
                            queueR02.getFront().setQueueStatus(qStatus.CONSULTING);
                        }
                    }

                } else {
                    System.out.println("Please process to payment later.");
                }

            } else {
                System.out.println("Consultation does not exist.");
            }

        } else {
            System.out.println("\nThere is no Ongoing Consultation.");
        }

    }

    public static void deleteConsultation() {
        Scanner deleteScanner = new Scanner(System.in);
        boolean r01hasOngoing = false, r02hasOngoing = false;

        for (int i = 0; i < queueR01.getNumberOfEntries(); i++) {
            var currentEntry = queueR01.getEntry(i + 1);

            if (currentEntry.getQueueStatus() == qStatus.CONSULTING) {
                System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.printf("|  %-170s|\n", "CURRENT CONSULTATION IN ROOM R01 ");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                r01hasOngoing = true;
                System.out.println(currentEntry);
            }
        }

        for (int i = 0; i < queueR02.getNumberOfEntries(); i++) {
            var currentEntry = queueR02.getEntry(i + 1);

            if (currentEntry.getQueueStatus() == qStatus.CONSULTING) {
                System.out.println("\n+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.printf("|  %-170s|\n", "CURRENT CONSULTATION IN ROOM R02");
                System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
                r02hasOngoing = true;
                System.out.println(currentEntry);
            }
        }

        if (r01hasOngoing || r02hasOngoing) {
            System.out.print("\nWhich Room Consultation would you like to delete? \n 1. R01 \n 2. R02 \n Choice > ");
            int room = deleteScanner.nextInt();

            switch (room) {
                case 1:
                    if (queueR01.isEmpty()) {
                        System.out.println("Room R01 is already empty, no consultation to remove.");
                    } else {
                        queueR01.dequeue();
                        if (!queueR01.isEmpty()) {
                            queueR01.getFront().setQueueStatus(qStatus.CONSULTING);
                        }
                        System.out.println("Consultation Deleted.");
                    }
                    break;
                case 2:
                    if (queueR02.isEmpty()) {
                        System.out.println("Room R02 is already empty, no consultation to remove.");
                    } else {
                        queueR02.dequeue();
                        if (!queueR02.isEmpty()) {
                            queueR02.getFront().setQueueStatus(qStatus.CONSULTING);
                        }
                        System.out.println("Consultation Deleted.");
                    }
                    break;
                default:
                    System.out.println("Invalid option selected.\n");
            }
        } else {
            System.out.println("\nThere is no Ongoing Consultation.");
        }

    }

    public void queueMenu(ListInterface<Patient> patientList, ListInterface<Staff> staffList, SortedLinkedList<Payment> paymentList, SortedArrayList<Medicine> medicineList) {

        Scanner scanner1 = new Scanner(System.in);
        int choice;

        do {
            System.out.println("");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "      CONSULTATION MENU      ");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[1] Add New Consultation");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[2] Ongoing Consultation");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[3] Update Consultation");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[4] Delete Current Consultation ");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[5] View Consultation Queue");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[6] Search Consultation");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.printf("| %-32s |\n", "[0] Exit Consultation");
            System.out.printf("+-%-32s-+\n", "--------------------------------");
            System.out.print("Enter Choice > ");
            choice = scanner1.nextInt();

            switch (choice) {
                case 1:
                    addConsultation(patientList, staffList);
                    break;
                case 2:
                    ongoingConsultation(paymentList, medicineList);
                    break;
                case 3:
                    updateConsultation(patientList, staffList);
                    break;
                case 4:
                    deleteConsultation();
                    break;
                case 5:
                    displayConsultation();
                    break;
                case 6:
                    searchConsultation();
                    break;
                case 0:
                    System.out.println("");
                    break;
                default:
                    System.out.println("Invalid option selected. Please try again.\n");
            }

        } while (choice != 0);

    }

}
