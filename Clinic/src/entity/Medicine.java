package entity;

import adt.LinkedList;
import adt.SortedArrayList;
import static entity.Patient.formattedDateTime;
import java.util.Scanner;

/**
 *
 * @author Wong Kah Ming
 */
enum MedicineType {
    TABLET,
    OTHERS
}

public class Medicine implements Comparable<Medicine> {

    private String idPrefix;
    public static int othCounter = 0;
    public static int tabCounter = 0;

    private String id;
    private MedicineType type;
    private String name;
    private int quantity;
    private double price;

    // history
    private LinkedList<PrescribedMedications> inventoryHistory = new LinkedList<>();

    public Medicine() {
        id = null;
        type = null;
        name = null;
        quantity = 0;
        price = 0;
    }

    public Medicine(Medicine medicine) {
        this.type = medicine.type;
        this.name = medicine.name;
        this.quantity = medicine.quantity;
        this.price = medicine.price;

        if (type == MedicineType.TABLET) {
            tabCounter++;
            idPrefix = "TAB";
            id = String.format("%s%03d", idPrefix, tabCounter);
        } else if (type == MedicineType.OTHERS) {
            othCounter++;
            idPrefix = "OTH";
            id = String.format("%s%03d", idPrefix, othCounter);
        }
    }

    public Medicine(String id) {
        this.id = id;
    }

    public Medicine(MedicineType type, String name, int quantity, double price) {

        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.price = price;

        assignID(type);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<PrescribedMedications> getInventoryHistory() {
        return inventoryHistory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public MedicineType getType() {
        return type;
    }

    private void assignID(MedicineType type) {
        if (type == MedicineType.TABLET) {
            tabCounter++;
            idPrefix = "TAB";
            id = String.format("%s%03d", idPrefix, tabCounter);
        } else if (type == MedicineType.OTHERS) {
            othCounter++;
            idPrefix = "OTH";
            id = String.format("%s%03d", idPrefix, othCounter);
        }
    }

    public double calculatePrice(int quantity) {
        return price * quantity;
    }

    public Medicine addMedicine() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Medicine Type - 1. Tablet 2. Others");
        System.out.print("Please enter 1 or 2 only. : ");
        MedicineType inputType;
        switch (scanner.nextInt()) {
            case 1:
                inputType = MedicineType.TABLET; // type = "Tablet";
                break;
            case 2:
                inputType = MedicineType.OTHERS;
                break;
            default:
                inputType = MedicineType.TABLET;
        }
        scanner.nextLine();

        System.out.print("Medicine Name: ");
        String inputName = scanner.nextLine();

        System.out.print("Quantity: ");
        int inputQuantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Price per quantity: RM ");
        double inputPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Are you confirm (Y/N): ");
        Character confirmation = scanner.next().toLowerCase().charAt(0);

        if (confirmation == 'y') {
            return new Medicine(inputType, inputName, inputQuantity, inputPrice);
        }

        return null;
    }

    public void deleteMedicineMain(SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);
        String input;
        displayMedicineArrayTable(medicineArray);

        do {
            System.out.print("Please enter the id to remove, 0 to return: ");
            input = scanner.nextLine().toUpperCase();

            var temp = medicineArray.binarySearch(new Medicine(input));

            if (temp.getEntry() != null) {

                temp.getEntry().viewMedicine();
                System.out.print("Are you confirm to remove this medicine? (Y/N) : ");
                var confirm = scanner.nextLine().toLowerCase().charAt(0);
                if (confirm == 'y') {
                    medicineArray.remove(temp.getIndex());
                    if (temp.getEntry().type == MedicineType.TABLET) {
                        tabCounter--;
                    } else {
                        othCounter--;
                    }
                    System.out.println("\nThe entry has been removed");
                    displayMedicineArrayTable(medicineArray);
                } else {
                    break;
                }
            } else if (input.charAt(0) == '0') {
                break;
            } else {
                System.out.println("\nInvalid input, please enter again");
            }
        } while (input.charAt(0) != '0');

    }

    public void updateMedicine(SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n+----- Please select the field to update: ---------+");
        System.out.println("| 1. Type                                          |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("| 2. Name                                          |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("| 3. Quantity                                      |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("| 4. Cost per quantity                             |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("| 0. Return                                        |");
        System.out.println("+--------------------------------------------------+");
        System.out.print("Please enter your selection (0-4): ");
        int selection = scanner.nextInt();
        scanner.nextLine();

        switch (selection) {
            case 1:
                System.out.println("Medicine Type - 1. Tablet 2. Others");
                System.out.print("Please enter 1 or 2 only. : ");
                switch (scanner.nextInt()) {
                    case 1:
                        type = MedicineType.TABLET;
                        break;
                    case 2:
                        type = MedicineType.OTHERS;
                        break;
                    default:
                        type = MedicineType.TABLET;
                }
                scanner.nextLine();
                break;
            case 2:
                System.out.print("Medicine Name: ");
                name = scanner.nextLine();
                break;
            case 3:
                System.out.print("Quantity: ");
                quantity = scanner.nextInt();
                scanner.nextLine();
                break;
            case 4:
                System.out.print("Price per quantity: RM ");
                price = scanner.nextDouble();
                scanner.nextLine();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid input");
        }
    }

    public void updateMedicineMain(SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);
        String input;
        displayMedicineArrayTable(medicineArray);

        do {
            System.out.print("Please enter the id to update, 0 to return: ");
            input = scanner.nextLine().toUpperCase();

            var foundedEntry = medicineArray.binarySearch(new Medicine(input));
            if (foundedEntry.getEntry() != null) {
                Medicine currentEntry = foundedEntry.getEntry();
                MedicineType temp = currentEntry.type;

                System.out.println("Preupdate result: ");
                currentEntry.viewMedicine();
                currentEntry.updateMedicine(medicineArray);

                if (temp != currentEntry.type) {
                    medicineArray.remove(currentEntry);
                    if (temp == MedicineType.OTHERS) {
                        othCounter--;
                    } else if (temp == MedicineType.TABLET) {
                        tabCounter--;
                    }
                    medicineArray.add(new Medicine(currentEntry));
                }
                break;
            } else if (input.charAt(0) == '0') {
                break;
            } else {
                System.out.println("\nInvalid input, please enter again");
            }
        } while (input.charAt(0) != '0');
    }

    public void viewMedicine() {
        System.out.println("+-----------------------------------------------------------------------------------+");
        System.out.println("|                                Medicine Details                                   |");
        System.out.println("+--------------------+-----------------+-----------------+--------------------------+");
        System.out.printf("| Medicine ID        | %-15s | Medicine Type   | %-24s |\n", id, type);
        System.out.println("+--------------------+-----------------+-----------------+--------------------------+");
        System.out.printf("| Medicine Name                                          | %-24s |\n", name);
        System.out.println("+--------------------------------------------------------+--------------------------+");
        System.out.printf("| Medicine Quantity                                      | %-24d |\n", quantity);
        System.out.println("+--------------------------------------------------------+--------------------------+");
        System.out.printf("| Price per quantity (RM)                                | %-24.2f |\n", price);
        System.out.println("+--------------------------------------------------------+--------------------------+");
        System.out.println("|                                 Inventory History                                 |");
        System.out.println("+-----------------------------------------------------------------------------------+");
        System.out.println("| No. | Input date                | Output date               | Quantity            |");
        System.out.println("+-----------------------------------------------------------------------------------+");

        if (!inventoryHistory.isEmpty()) {

            for (int x = 1; x <= inventoryHistory.getNumberOfEntries(); x++) {
                PrescribedMedications history = inventoryHistory.getEntry(x);
                System.out.printf("| %-3d | %-25s | %-25s | -%-18d |\n", x, "",
                        formattedDateTime(history.getProcessedTime()), history.getRequiredQuantity());
                System.out.println("+-----------------------------------------------------------------------------------+");
            }

        } else {
            System.out.println("|                            No History At The Moment                               |");
            System.out.println("+-----------------------------------------------------------------------------------+");
        }

    }

    public void viewMedicineMain(SortedArrayList<Medicine> medicineArray) {
        Scanner scanner = new Scanner(System.in);

        String input;
        displayMedicineArrayTable(medicineArray);

        do {
            System.out.print("Please enter the id to view, 0 to return: ");
            input = scanner.nextLine().toUpperCase();

            var temp = medicineArray.binarySearch(new Medicine(input));

            if (temp.getEntry() != null) {
                temp.getEntry().viewMedicine();
                break;
            } else if (input.charAt(0) == '0') {
                break;
            } else {
                System.out.println("\nInvalid input, please enter again");
            }
        } while (input.charAt(0) != '0');
    }

    public boolean takeMedicine(int quantity) {
        if (this.quantity > quantity) {
            this.quantity -= quantity;
            return true;
        }
        return false;
    }

    public int viewMedicineMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n+--------- Medicine --------+");
        System.out.println("| 1. Add medicine           |");
        System.out.println("+---------------------------+");
        System.out.println("| 2. Update medicine        |");
        System.out.println("+---------------------------+");
        System.out.println("| 3. Remove medicine        |");
        System.out.println("+---------------------------+");
        System.out.println("| 4. View medicine          |");
        System.out.println("+---------------------------+");
        System.out.println("| 0. Exit                   |");
        System.out.println("+---------------------------+");
        System.out.print("Please enter your selection: ");
        return scanner.nextInt();
    }

    public void medicineModule(SortedArrayList<Medicine> medicineArray) {

        int selection;
        do {
            selection = viewMedicineMenu();
            switch (selection) {
                case 1:
                    medicineArray.add(addMedicine());
                    break;
                case 2:
                    updateMedicineMain(medicineArray);
                    break;
                case 3:
                    deleteMedicineMain(medicineArray);
                    break;
                case 4:
                    viewMedicineMain(medicineArray);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Please enter again.");
            }
        } while (selection != 0);
    }

    public static void displayMedicineArrayTable(SortedArrayList<Medicine> medicineArray) {
        System.out.println("+--------------------------------------------------------------------------+");
        System.out.println("| ID     | TYPE       | NAME                    | QUANTITY  | PRICE (RM)   |");
        System.out.println("+--------------------------------------------------------------------------+ ");

        if (medicineArray.isEmpty()) {
            System.out.println("|                       NO RECORD FOUNDED                                   |");
            System.out.println("+--------------------------------------------------------------------------+ ");
        }

        for (int x = 0; x < medicineArray.getNumberOfEntries(); x++) {
            Medicine medicineItem = medicineArray.getEntry(x);
            System.out.println(String.format("| %-5s | %-10s | %-23s | %-9d | %-12.2f |",
                    medicineItem.id, medicineItem.type.toString(),
                    medicineItem.name.toUpperCase(), medicineItem.quantity, medicineItem.price));
            System.out.println("+--------------------------------------------------------------------------+ ");
        }
    }

    public static SortedArrayList<Medicine> dummyData() {
        SortedArrayList<Medicine> medicineArray = new SortedArrayList<>();

        medicineArray.add(new Medicine(MedicineType.OTHERS, "Other1", 999, 12));
        medicineArray.add(new Medicine(MedicineType.OTHERS, "Other2", 999, 22));
        medicineArray.add(new Medicine(MedicineType.TABLET, "Paracetamol", 999, 11));
        medicineArray.add(new Medicine(MedicineType.TABLET, "Tablet1", 999, 10.5));
        medicineArray.add(new Medicine(MedicineType.TABLET, "Tablet2", 999, 20.5));
        medicineArray.add(new Medicine(MedicineType.OTHERS, "Other3", 999, 13));
        medicineArray.add(new Medicine(MedicineType.OTHERS, "Other4", 999, 15));

        return medicineArray;
    }

    @Override
    public int compareTo(Medicine o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "Medicine{" + "idPrefix=" + idPrefix + ", id=" + id + ", type=" + type
                + ", name=" + name + ", quantity=" + quantity + ", price=" + price
                + ", inventoryHistory=" + inventoryHistory + '}';
    }

}
