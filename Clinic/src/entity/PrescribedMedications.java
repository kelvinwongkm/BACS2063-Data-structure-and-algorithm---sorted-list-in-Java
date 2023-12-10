package entity;

import adt.SortedArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Wong Kah Ming
 */
public class PrescribedMedications implements Comparable<PrescribedMedications> {

    private LocalDateTime processedTime;
    private Medicine medicine;
    private int requiredQuantity;

    public PrescribedMedications(Medicine medication) {
        this.processedTime = null;
        this.medicine = medication;
    }

    public PrescribedMedications() {
        this(null);
    }

    public PrescribedMedications(Medicine medication, int quantity) {
        this.medicine = medication;
        this.requiredQuantity = quantity;
    }

    public PrescribedMedications(LocalDateTime processedTime, int requiredQuantity) {
        this.processedTime = processedTime;
        this.requiredQuantity = requiredQuantity;
    }

    public LocalDateTime getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(LocalDateTime processedTime) {
        this.processedTime = processedTime;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    private void displayPrescribedMedicationsTable(SortedArrayList<PrescribedMedications> medicationList) {
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("| No. | Medicine ID | Type    | Name                  | Quantity    |");
        System.out.println("+-------------------------------------------------------------------+");

        if (!medicationList.isEmpty()) {
            for (int x = 0; x < medicationList.getNumberOfEntries(); x++) {

                PrescribedMedications entry = medicationList.getEntry(x);
                System.out.printf("| %-3d | %-11s | %-7s | %-21s | %-11d |\n", x + 1,
                        entry.getMedicine().getId(), entry.getMedicine().getType().toString(),
                        entry.getMedicine().getName(), entry.requiredQuantity);
                System.out.println("+-------------------------------------------------------------------+");
            }
        } else {
            System.out.println("|              No Prescribed Medications entered                    |");
            System.out.println("+-------------------------------------------------------------------+");
        }
    }

    private void addPrescribedMedications(SortedArrayList<PrescribedMedications> medicationList,
            SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);
        Character wantContinue;

        Medicine.displayMedicineArrayTable(medicineArray);

        do {
            System.out.print("Please enter the medicine ID: ");
            String medicineID = scanner.nextLine().toUpperCase();

            Medicine currentEntry = medicineArray.search(new Medicine(medicineID));

            if (currentEntry != null) {

                System.out.print("Please enter the quantity: ");
                int selectedQuantity = scanner.nextInt();

                System.out.print("Are you confirm? (Y/N): ");
                Character isConfirm = scanner.next().toLowerCase().charAt(0);
                if (isConfirm == 'y') {
                    var entryFound = medicationList.search(new PrescribedMedications(currentEntry));
                    if (entryFound != null) {
                        entryFound.setRequiredQuantity(entryFound.requiredQuantity + selectedQuantity);
                    } else {
                        medicationList.add(new PrescribedMedications(currentEntry, selectedQuantity));
                    }
                }
            }

            System.out.print("Do you want to continue? (Y/N): ");
            wantContinue = scanner.next().toLowerCase().charAt(0);
            scanner.nextLine();

        } while (wantContinue == 'y');
    }

    private void updatePrescribedMecdications(SortedArrayList<PrescribedMedications> medicationList, 
            SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);

        displayPrescribedMedicationsTable(medicationList);
        SortedArrayList<Medicine> medicineList = (SortedArrayList<Medicine>) medicineArray;

        System.out.print("Please enter the index number (Eg. 1, 2) to update: ");
        int selectedIndex = scanner.nextInt();

        System.out.print("Please enter the updated quantity: ");
        int selectedQuantity = scanner.nextInt();

        PrescribedMedications prescribedMedicationsEntry = medicationList.getEntry(selectedIndex - 1);
        Medicine medicineEntry = medicineList.search(prescribedMedicationsEntry.medicine);

        if (selectedQuantity < medicineEntry.getQuantity()) {
            prescribedMedicationsEntry.requiredQuantity = selectedQuantity;
        } else {
            System.out.println("Not enough stock");
        }
    }

    private void removePrescribedMedications(SortedArrayList<PrescribedMedications> medicationList) {
        Scanner scanner = new Scanner(System.in);
        displayPrescribedMedicationsTable(medicationList);

        System.out.print("Please enter the index number (Eg. 1, 2) to remove: ");
        int selectedIndex = scanner.nextInt();

        // remove if within range
        medicationList.remove(selectedIndex - 1);
    }

    public SortedArrayList<PrescribedMedications> prescribedMedicationsModule(SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        SortedArrayList<PrescribedMedications> medicationList = new SortedArrayList<>();

        System.out.println("Please enter the prescribed medications for the patient.");

        do {
            displayPrescribedMedicationsTable(medicationList);

            System.out.println("+------ Prescribed Medications ---------+");
            System.out.println("| 1. Add medications                    |");
            System.out.println("+---------------------------------------+");

            if (!medicationList.isEmpty()) {
                System.out.println("| 2. Update medications                 |");
                System.out.println("+---------------------------------------+");
                System.out.println("| 3. Remove medications                 |");
                System.out.println("+---------------------------------------+");
            }

            System.out.println("| 0. Return                             |");
            System.out.println("+---------------------------------------+");
            System.out.print("Please enter your selection: ");
            int selection = scanner.nextInt();

            switch (selection) {
                case 1:
                    addPrescribedMedications(medicationList, medicineArray);
                    break;
                case 2:
                case 3:
                    if (!medicationList.isEmpty()) {
                        switch (selection) {
                            case 2:
                                updatePrescribedMecdications(medicationList, medicineArray);
                                break;
                            case 3:
                                removePrescribedMedications(medicationList);
                                break;
                        }
                    }
                    break;
                case 0:
                    isContinue = false;
                    break;
                default:
                    System.out.println("Please enter again");
            }
        } while (isContinue);

        updateOriginalMedicineList(medicationList, medicineArray);
        return medicationList;
    }

    public void updateOriginalMedicineList(SortedArrayList<PrescribedMedications> medicationList, 
            SortedArrayList<Medicine> medicineArray) {
        for (int x = 0; x < medicationList.getNumberOfEntries(); x++) {

            PrescribedMedications medicationEntry = medicationList.getEntry(x);

            Medicine oriEntry = medicineArray.search(medicationEntry.medicine);

            medicationEntry.processedTime = LocalDateTime.now();
            oriEntry.getInventoryHistory().add(new PrescribedMedications(medicationEntry.processedTime, 
                    medicationEntry.requiredQuantity));
            oriEntry.setQuantity(oriEntry.getQuantity() - medicationEntry.getRequiredQuantity());
        }
    }

    @Override
    public String toString() {
        return "PrescribedMedications{" + "processedTime=" + processedTime + ", medication=" + medicine 
                + ", requiredQuantity=" + requiredQuantity + '}';
    }

    @Override
    public int compareTo(PrescribedMedications o) {
        return this.medicine.getId().compareTo(o.medicine.getId());
    }

}
