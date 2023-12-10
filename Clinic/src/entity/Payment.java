package entity;

import adt.SortedArrayList;
import adt.SortedLinkedList;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;
import adt.SortedListInterface;

/**
 *
 * @author Cheok Ding Wei
 */
enum PaymentMethod {
    CASH,
    CARD
}

class Cash extends Payment {

    private double cashGiven;
    private double change;

    public Cash() {
    }

    public Cash(Consultation consultation, LocalDate paymentDate, PaymentMethod paymentMethod, double cashGiven) {
        super(consultation, paymentDate, paymentMethod);
        this.cashGiven = cashGiven;
    }

    public double getCashGiven() {
        return cashGiven;
    }

    public void setCashGiven(double cashGiven) {
        this.cashGiven = cashGiven;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public boolean checkCashGiven(double paymentTotalAmount, double cashGiven) {

        if (cashGiven < paymentTotalAmount) {
            displayWarningMessage("You should enter a sufficient cash amount to cover payment amount!");
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("| %-67s | %-25s |\n", "Payment Method", "Cash")
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-25.2f |\n", "Cash Tendered        (RM)", cashGiven)
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-25.2f |\n", "Cash Change          (RM)", change)
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------");
    }
}

class Card extends Payment {

    private String cardNo;
    private String cardHolderName;
    private LocalDate cardExpDate;
    private String cvvNo;
    private String cardType;

    public Card() {
    }

    public Card(Consultation consultation, LocalDate paymentDate, PaymentMethod paymentMethod, String cardNo, String cardHolderName, LocalDate cardExpDate, String cvvNo, String cardType) {
        super(consultation, paymentDate, paymentMethod);
        this.cardNo = cardNo;
        this.cardHolderName = cardHolderName;
        this.cardExpDate = cardExpDate;
        this.cvvNo = cvvNo;
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public LocalDate getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(LocalDate cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public String getCvvNo() {
        return cvvNo;
    }

    public void setCvvNo(String cvvNo) {
        this.cvvNo = cvvNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean checkCardNo(String cardNo) {
        if (cardNo.length() != 16) {
            displayWarningMessage("You should enter a 16-digit card number!");
            return false;
        }

        for (int i = 0; i < cardNo.length(); i++) {
            if (!Character.isDigit(cardNo.charAt(i))) {
                displayWarningMessage("You should enter only DIGIT for card number!");
                return false;
            }
        }
        return true;
    }

    public boolean checkCardHolderName(String cardHolderName) {
        for (int i = 0; i < cardHolderName.length(); i++) {
            if (!(Character.isLetter(cardHolderName.charAt(i)))) {
                if (!(cardHolderName.charAt(i) == 32)) {
                    displayWarningMessage("You should enter only LETTER for card holder name!");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkCvvNo(String cvvNo) {

        if (cvvNo.length() != 3) {
            displayWarningMessage("You should enter a 3-digit cvv number!");
            return false;
        }

        for (int i = 0; i < cvvNo.length(); i++) {
            if (!Character.isDigit(cvvNo.charAt(i))) {
                displayWarningMessage("You should enter only DIGIT for cvv number!");
                return false;
            }
        }

        return true;
    }

    public boolean checkCardExpYear(int expYear, int tdyYear) {

        //LocalDate reserveDate = LocalDate.of(yearInput, monthInput, dayInput);
        if (expYear < tdyYear || (expYear - tdyYear) > 5) {
            displayWarningMessage("You've entered an invalid year!");
            return false;
        }

        return true;
    }

    public boolean checkCardExpMonth(int expYear, int tdyYear, int expMonth, int tdyMonth) {

        if (!(expMonth >= 1 && expMonth <= 12)) {
            displayWarningMessage("You've entered an invalid month!");
            return false;
        }

        if (expYear == tdyYear) {
            if (expMonth <= tdyMonth) {
                displayWarningMessage("You should enter a date after current date!");
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("| %-67s | %-25s |\n", "Payment Method", "Card")
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-25s |\n", "Card Type", cardType)
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-25s |\n", "Card Number", cardNo)
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-25s |\n", "Card Holder Name", (cardHolderName).toUpperCase())
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-2d/%-22d |\n", "Card Expiry Date", cardExpDate.getMonthValue(), cardExpDate.getYear())
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------")
                + String.format("| %-67s | %-25s |\n", "Cvv Number", cvvNo)
                + String.format("+-%-67s-+-%-25s-+\n", "-------------------------------------------------------------------", "-------------------------");

    }

}

public class Payment implements Comparable<Payment> {

    private static int nextPaymentId = 1;

    private String paymentId;
    private LocalDate paymentDate;
    private double paymentTotalAmount;
    private PaymentMethod paymentMethod;
    private Consultation consultation;

    public Payment() {
    }

    public Payment(Consultation consultation) {
        this.consultation = consultation;
    }

    public Payment(Consultation consultation, LocalDate paymentDate, PaymentMethod paymentMethod) {
        this.consultation = consultation;
        this.paymentId = "PYT" + String.valueOf(nextPaymentId + 100);
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        nextPaymentId++;
    }

    public Payment(String paymentId) {
        this.paymentId = paymentId;
    }

    public static int getNextPaymentId() {
        return nextPaymentId;
    }

    public static void setNextPaymentId(int nextPaymentId) {
        Payment.nextPaymentId = nextPaymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentTotalAmount() {
        return paymentTotalAmount;
    }

    public void setPaymentTotalAmount(double paymentTotalAmount) {
        this.paymentTotalAmount = paymentTotalAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public void displayPaymentMenu(Payment payment, SortedLinkedList<Payment> paymentList) {
        int paymentChoice = 4;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println("");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "      PAYMENT MENU      ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[1] View Payment");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[2] Delete Payment");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "[0] Exit");
                System.out.printf("+-%-24s-+\n", "------------------------");
                paymentChoice = readInteger("Enter Your Choice");

                switch (paymentChoice) {
                    case 1:
                        viewPayment(paymentList);
                        break;
                    case 2:
                        deletePayment(paymentList);
                        break;
                    case 0:
                        System.out.printf("\nExiting...");
                        scanner.nextLine();
                        System.out.println("");
                        break;
                    default:
                        displayWarningMessage("You've entered an invalid choice!");
                        break;
                }
            } catch (Exception InputMismatchException) {
                displayWarningMessage("You've entered a character/symbol!");
            }
        } while (paymentChoice != 0);
    }

    public void createPayment(Payment payment, SortedLinkedList<Payment> paymentList) {
        int paymentMethod = 3;
        Scanner scanner = new Scanner(System.in);

        do {
            try {
                displayInvoice(payment);
                displayPaymentMethod();
                paymentMethod = readInteger("Enter Your Choice");
                //continueInput = true;

                switch (paymentMethod) {
                    case 1:
                        payment.setPaymentMethod(PaymentMethod.CASH);
                        cashPayment(payment, paymentList);
                        break;
                    case 2:
                        payment.setPaymentMethod(PaymentMethod.CARD);
                        cardPayment(payment, paymentList);
                        break;
                    case 0:
                        System.out.printf("\nExiting...");
                        scanner.nextLine();
                        break;
                    default:
                        displayWarningMessage("You've entered an invalid choice!");
                        break;
                }
            } catch (Exception InputMismatchException) {
                displayWarningMessage("You've entered a character/symbol!");
            }
        } while (!(paymentMethod >= 0 && paymentMethod <= 2));

    }

    public static void cashPayment(Payment payment, SortedLinkedList<Payment> paymentList) {
        double cashGiven;
        Payment cash = new Cash();

        do {
            System.out.println("");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-24s |\n", "  PAYMENT TOTAL AMOUNT  ");
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.printf("| %-3s%-21.2f |\n", "RM", payment.paymentTotalAmount);
            System.out.printf("+-%-24s-+\n", "------------------------");
            System.out.println("");
            cashGiven = readDouble("Enter cash amount to be paid");
        } while (!((Cash) cash).checkCashGiven(payment.paymentTotalAmount, cashGiven));

        ((Cash) cash).setCashGiven(cashGiven);
        ((Cash) cash).setChange(cashGiven - payment.paymentTotalAmount);

        paymentConfirmation(payment, cash, paymentList);
    }

    public static void cardPayment(Payment payment, SortedLinkedList<Payment> paymentList) {
        int cardType = 3;
        String cardNo = null;
        String cardHolderName = null;
        LocalDate tdyDate = LocalDate.now();
        int cardExpYear = 2022;
        int cardExpMonth = 10;
        String cvvNo = null;
        Scanner scanner = new Scanner(System.in);

        Payment card = new Card();

        do {
            try {
                displayCardType();
                cardType = readInteger("Enter Your Choice");

                switch (cardType) {
                    case 1:
                    case 2:
                        break;
                    case 0:
                        System.out.printf("\nExiting...\n");
                        scanner.nextLine();
                        break;
                    default:
                        displayWarningMessage("You've entered an invalid choice!");
                        break;
                }
            } catch (Exception InputMismatchException) {
                displayWarningMessage("You've entered a character/symbol!");
            }
        } while (!(cardType >= 0 && cardType <= 2));

        do {
            try {
                System.out.println("");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       CARD NUMBER      ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "  EG. 1111222233334444  ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.println("");
                cardNo = readString("Enter your card number without space (16-digit)");
            } catch (Exception InputMismatchException) {
            }
        } while (!((Card) card).checkCardNo(cardNo));

        do {
            try {
                System.out.println("");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "    CARD HOLDER NAME    ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "    EG. ANDREW CHEOK    ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.println("");
                cardHolderName = readString("Enter your card holder name");
            } catch (Exception InputMismatchException) {
                displayWarningMessage("You've entered a symbol!");
            }
        } while (!((Card) card).checkCardHolderName(cardHolderName));

        do {
            try {
                System.out.println("");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", " CARD EXPIRY DATE (YEAR)");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "        EG. 2025        ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.println("");
                cardExpYear = readInteger("Enter your card expiry YEAR");
            } catch (Exception InputMismatchException) {
                displayWarningMessage("You've entered a character/symbol!");
            }
        } while (!((Card) card).checkCardExpYear(cardExpYear, tdyDate.getYear()));

        do {
            try {
                System.out.println("");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "CARD EXPIRY DATE (MONTH)");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "         EG. 12         ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.println("");
                cardExpMonth = readInteger("Enter your card expiry MONTH");
            } catch (Exception InputMismatchException) {
                displayWarningMessage("You've entered a character/symbol!");
            }
        } while (!((Card) card).checkCardExpMonth(cardExpYear, tdyDate.getYear(), cardExpMonth, tdyDate.getMonthValue()));

        do {
            try {
                System.out.println("");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "       CVV NUMBER       ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.printf("| %-24s |\n", "         EG. 490        ");
                System.out.printf("+-%-24s-+\n", "------------------------");
                System.out.println("");
                cvvNo = readString("Enter your cvv number (3-digit)");
            } catch (Exception InputMismatchException) {
            }
        } while (!((Card) card).checkCvvNo(cvvNo));

        ((Card) card).setCardNo(cardNo);
        ((Card) card).setCardHolderName(cardHolderName);
        ((Card) card).setCardExpDate(LocalDate.of(cardExpYear, cardExpMonth, 1));
        ((Card) card).setCvvNo(cvvNo);
        switch (cardType) {
            case 1:
                ((Card) card).setCardType("Debit Card");
                break;
            case 2:
                ((Card) card).setCardType("Credit Card");
                break;
        }

        paymentConfirmation(payment, card, paymentList);
    }

    public static void deletePayment(SortedLinkedList<Payment> paymentList) {
        String paymentId;
        char deleteConfirmation = 'Y';
        Scanner scanner = new Scanner(System.in);

        Iterator<Payment> paymentIterator;

        do {
            paymentIterator = paymentList.getIterator();
            displayPaymentList(paymentList, paymentIterator);
            System.out.println("");
            paymentId = readString("Please enter the payment ID you want to delete");

            paymentId = paymentId.toUpperCase();

            Payment pay = paymentList.search(new Payment(paymentId));

            if (!(paymentId.equals("0"))) {
                if (pay != null) {
                    displayBill(pay);

                    do {
                        System.out.println("");
                        deleteConfirmation = readChar("Are you sure want to delete this payment (Y = yes or N = no)?");
                    } while (validateChar(deleteConfirmation) == false);

                    switch (deleteConfirmation) {
                        case 'Y':
                        case 'y': {
                            System.out.println("");
                            System.out.println("Your payment is successful deleted!");
                            displayContinue();
                            paymentList.remove(pay);
                        }
                        break;
                        case 'N':
                        case 'n':
                            System.out.println("");
                            System.out.println("Your payment is unsuccessful deleted!");
                            displayContinue();
                            break;
                        default:
                            displayWarningMessage("You've entered an invalid character!");
                            break;
                    }
                } else {
                    displayWarningMessage("You've entered an invalid payment ID!");
                }

            } else {
                System.out.printf("\nExiting...");
                scanner.nextLine();
            }

        } while (!paymentId.equals("0"));
    }

    public static void viewPayment(SortedLinkedList<Payment> paymentList) {
        String paymentId;
        Scanner scanner = new Scanner(System.in);

        Iterator<Payment> paymentIterator;

        do {
            paymentIterator = paymentList.getIterator();
            displayPaymentList(paymentList, paymentIterator);
            System.out.println("");
            paymentId = readString("Please enter the payment ID you want to view");

            paymentId = paymentId.toUpperCase();

            Payment pay = paymentList.search(new Payment(paymentId));

            if (!(paymentId.equals("0"))) {
                if (pay != null) {
                    displayBill(pay);
                    displayContinue();
                } else {
                    displayWarningMessage("You've entered an invalid payment ID!");
                }
            } else {
                System.out.printf("\nExiting...");
                scanner.nextLine();
            }

        } while (!paymentId.equals("0"));
    }

    public static void displayPaymentList(SortedListInterface<Payment> paymentList, Iterator<Payment> paymentIterator) {
        int paymentCount = 0;
        System.out.println("");
        System.out.printf("+-------------------+\n");
        System.out.printf("|    PAYMENT LIST   |\n");
        System.out.printf("+------+------------+\n");
        while (paymentIterator.hasNext()) {
            paymentCount++;
            Payment pay = paymentIterator.next();
            if (paymentCount < 10) {
                System.out.printf("| [%d]  | %-10s |\n", paymentCount, pay.getPaymentId());
                System.out.printf("+------+------------+\n");
            } else {
                System.out.printf("| [%d] | %-10s |\n", paymentCount, pay.getPaymentId());
                System.out.printf("+------+------------+\n");
            }
        }
        System.out.printf("| [%d]  | %-10s |\n", 0, "Exit");
        System.out.printf("+------+------------+\n");
    }

    public static void paymentConfirmation(Payment payment, Payment paymentMethod, SortedLinkedList<Payment> paymentList) {
        char paymentConfirmation = 'Y';

        do {
            do {
                System.out.println("");
                paymentConfirmation = readChar("Please confirm your payment (Y = yes or N = no)");
            } while (validateChar(paymentConfirmation) == false);

            switch (paymentConfirmation) {
                case 'Y':
                case 'y': {
                    System.out.println("");
                    System.out.println("Your payment is successful!");
                    displayContinue();
                    if (payment.getPaymentMethod() == PaymentMethod.CASH) {
                        paymentList.add(new Cash(new Consultation(new Patient(payment.consultation.getPatient().getPatientID()), new Staff(payment.consultation.getStaff().getStaffID()), payment.consultation.getMedicationList()), LocalDate.now(), payment.getPaymentMethod(), ((Cash) paymentMethod).getCashGiven()));
                    } else {
                        paymentList.add(new Card(new Consultation(new Patient(payment.consultation.getPatient().getPatientID()), new Staff(payment.consultation.getStaff().getStaffID()), payment.consultation.getMedicationList()), LocalDate.now(), payment.getPaymentMethod(), ((Card) paymentMethod).getCardNo(), ((Card) paymentMethod).getCardHolderName(), ((Card) paymentMethod).getCardExpDate(), ((Card) paymentMethod).getCvvNo(), ((Card) paymentMethod).getCardType()));
                    }
                    Iterator<Payment> paymentIterator = paymentList.getIterator();
                    Payment pay = null;
                    while (paymentIterator.hasNext()) {
                        pay = paymentIterator.next();
                    }
                    displayBill(pay);
                }
                break;
                case 'N':
                case 'n':
                    System.out.println("");
                    System.out.println("Your payment is unsuccessful!");
                    displayContinue();
                    break;
                default:
                    displayWarningMessage("You've entered an invalid character!");
                    break;
            }
        } while (!(paymentConfirmation == 'Y' || paymentConfirmation == 'y' || paymentConfirmation == 'N' || paymentConfirmation == 'n'));
    }

    public static void displayWarningMessage(String msg) {
        System.out.printf("\n%s\n", msg);
        System.out.println("Please enter again!");
    }

    public static void displayContinue() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        System.out.print("Press enter to continue...");
        scanner.nextLine();
    }

    public static int readInteger(String action) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s > ", action);
        int input = scanner.nextInt();

        return input;
    }

    public static double readDouble(String action) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s > ", action);
        double input = scanner.nextDouble();

        return input;
    }

    public static String readString(String action) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s > ", action);
        String input = scanner.nextLine();

        return input;
    }

    public static char readChar(String action) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("%s > ", action);
        char input = scanner.next().charAt(0);

        return input;
    }

    public static boolean validateChar(char choice) {
        if (!Character.isLetter(choice)) {
            displayWarningMessage("You should enter a character!");
            return false;
        }

        return true;
    }

    public static void displayBill(Payment paymentList) {
        SortedArrayList<PrescribedMedications> medicineList = (SortedArrayList<PrescribedMedications>) paymentList.consultation.getMedicationList();

        System.out.println("");
        System.out.println(String.format("+-%-3s---%-15s---%-25s---%-15s---%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-46s%-4s%-45s |", "", "BILL", ""));

        System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+-%-25s-+", "---------------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-21s | %-25s | %-15s | %-25s |", "Patient ID", paymentList.consultation.getPatient().getPatientID(), "Doctor ID", paymentList.consultation.getStaff().getStaffID()));
        System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+-%-25s-+", "---------------------", "-------------------------", "---------------", "-------------------------"));

        if (paymentList.paymentDate.getMonthValue() < 10) {
            System.out.println(String.format("| %-21s | %-25s | %-15s | %-4d-%-1d-%-18d |", "Payment ID", paymentList.paymentId, "Payment Date", paymentList.paymentDate.getYear(), paymentList.paymentDate.getMonthValue(), paymentList.paymentDate.getDayOfMonth()));
        } else {
            System.out.println(String.format("| %-21s | %-25s | %-15s | %-4d-%-2d-%-17d |", "Payment ID", paymentList.paymentId, "Payment Date", paymentList.paymentDate.getYear(), paymentList.paymentDate.getMonthValue(), paymentList.paymentDate.getDayOfMonth()));
        }

        if (medicineList.getNumberOfEntries() != 0) {
            System.out.println(String.format("+-%-3s-+-%-15s-+-%-25s-+-%-15s-+-%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
            System.out.println(String.format("| %-3s | %-15s | %-25s | %-15s | %-25s |", "No.", "Medicine ID", "Medicine Name", "Quantity", "Subtotal (RM)"));
            System.out.println(String.format("+-%-3s-+-%-15s-+-%-25s-+-%-15s-+-%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));

            //for loop
            double medicineTotalPrice = 0;

            for (int i = 0; i < medicineList.getNumberOfEntries(); i++) {
                PrescribedMedications currentEntry = ((SortedArrayList<PrescribedMedications>) medicineList).getEntry(i);
                System.out.println(String.format("| [%-1d] | %-15s | %-25s | %-15d | %-25.2f |", (i + 1), currentEntry.getMedicine().getId(), currentEntry.getMedicine().getName(), currentEntry.getRequiredQuantity(), currentEntry.getMedicine().calculatePrice(currentEntry.getRequiredQuantity())));
                System.out.println(String.format("+-%-3s-+-%-15s-+-%-25s-+-%-15s-+-%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));

                medicineTotalPrice += currentEntry.getMedicine().calculatePrice(currentEntry.getRequiredQuantity());
            }

            paymentList.setPaymentTotalAmount(medicineTotalPrice + paymentList.consultation.getFees());

            System.out.println(String.format("| %-67s | %-25.2f |", "Medicine Total Price (RM)", medicineTotalPrice));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-------------------------------------------------------------------", "-------------------------"));
        } else {
            paymentList.setPaymentTotalAmount(paymentList.consultation.getFees());
            System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+-%-25s-+", "---------------------", "-------------------------", "---------------", "-------------------------"));
        }
        System.out.println(String.format("| %-67s | %-25.2f |", "Consultation Fee     (RM)", paymentList.consultation.getFees()));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-------------------------------------------------------------------", "-------------------------"));
        System.out.println(String.format("| %-67s | %-25.2f |", "Grand Total Price    (RM)", paymentList.getPaymentTotalAmount()));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-------------------------------------------------------------------", "-------------------------"));

        if (paymentList.paymentMethod == PaymentMethod.CASH) {
            ((Cash) paymentList).setChange(((Cash) paymentList).getCashGiven() - paymentList.getPaymentTotalAmount());
            System.out.print(((Cash) paymentList).toString());
        } else if (paymentList.paymentMethod == PaymentMethod.CARD) {
            System.out.print(((Card) paymentList).toString());
        }
    }

    public static void displayInvoice(Payment payment) {
        SortedListInterface<PrescribedMedications> medicineList = payment.consultation.getMedicationList();

        System.out.println("");
        System.out.println(String.format("+-%-3s---%-15s---%-25s---%-15s---%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-44s%-7s%-44s |", "", "INVOICE", ""));

        System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+-%-25s-+", "---------------------", "-------------------------", "---------------", "-------------------------"));
        System.out.println(String.format("| %-21s | %-25s | %-15s | %-25s |", "Patient ID", payment.consultation.getPatient().getPatientID(), "Doctor ID", payment.consultation.getStaff().getStaffID()));

        if (medicineList.getNumberOfEntries() != 0) {
            System.out.println(String.format("+-%-3s-+-%-15s-+-%-25s-+-%-15s-+-%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));
            System.out.println(String.format("| %-3s | %-15s | %-25s | %-15s | %-25s |", "No.", "Medicine ID", "Medicine Name", "Quantity", "Subtotal (RM)"));
            System.out.println(String.format("+-%-3s-+-%-15s-+-%-25s-+-%-15s-+-%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));

            //for loop
            double medicineTotalPrice = 0;

            for (int i = 0; i < medicineList.getNumberOfEntries(); i++) {
                PrescribedMedications currentEntry = ((SortedArrayList<PrescribedMedications>) medicineList).getEntry(i);
                System.out.println(String.format("| [%-1d] | %-15s | %-25s | %-15d | %-25.2f |", (i + 1), currentEntry.getMedicine().getId(), currentEntry.getMedicine().getName(), currentEntry.getRequiredQuantity(), currentEntry.getMedicine().calculatePrice(currentEntry.getRequiredQuantity())));
                System.out.println(String.format("+-%-3s-+-%-15s-+-%-25s-+-%-15s-+-%-25s-+", "---", "---------------", "-------------------------", "---------------", "-------------------------"));

                medicineTotalPrice += currentEntry.getMedicine().calculatePrice(currentEntry.getRequiredQuantity());
            }

            payment.setPaymentTotalAmount(medicineTotalPrice + payment.consultation.getFees());

            System.out.println(String.format("| %-67s | %-25.2f |", "Medicine Total Price (RM)", medicineTotalPrice));
            System.out.println(String.format("+-%-67s-+-%-25s-+", "-------------------------------------------------------------------", "-------------------------"));
        } else {
            payment.setPaymentTotalAmount(payment.consultation.getFees());
            System.out.println(String.format("+-%-21s-+-%-25s-+-%-15s-+-%-25s-+", "---------------------", "-------------------------", "---------------", "-------------------------"));
        }

        System.out.println(String.format("| %-67s | %-25.2f |", "Consultation Fee     (RM)", payment.consultation.getFees()));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-------------------------------------------------------------------", "-------------------------"));
        System.out.println(String.format("| %-67s | %-25.2f |", "Grand Total Price    (RM)", payment.getPaymentTotalAmount()));
        System.out.println(String.format("+-%-67s-+-%-25s-+", "-------------------------------------------------------------------", "-------------------------"));
    }

    public static void displayPaymentMethod() {
        System.out.println("");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "     PAYMENT METHOD     ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "[1] Cash");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "[2] Card");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "[0] Exit");
        System.out.printf("+-%-24s-+\n", "------------------------");
    }

    public static void displayCardType() {
        System.out.println("");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "        CARD TYPE       ");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "[1] Debit Card");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "[2] Credit Card");
        System.out.printf("+-%-24s-+\n", "------------------------");
        System.out.printf("| %-24s |\n", "[0] Exit");
        System.out.printf("+-%-24s-+\n", "------------------------");
    }

    public SortedListInterface<Payment> paymentDummyData(SortedArrayList<Medicine> medicineList) {
        SortedListInterface<Payment> paymentList = new SortedLinkedList<>();

        SortedArrayList<PrescribedMedications> medicationList1 = new SortedArrayList<>();
        SortedArrayList<PrescribedMedications> medicationList2 = new SortedArrayList<>();
        SortedArrayList<PrescribedMedications> medicationList3 = new SortedArrayList<>();
        SortedArrayList<PrescribedMedications> medicationList4 = new SortedArrayList<>();
        SortedArrayList<PrescribedMedications> medicationList5 = new SortedArrayList<>();

        medicationList1.add(new PrescribedMedications(medicineList.getEntry(0), 1));
        medicationList1.add(new PrescribedMedications(medicineList.getEntry(6), 3));

        medicationList2.add(new PrescribedMedications(medicineList.getEntry(1), 4));
        medicationList2.add(new PrescribedMedications(medicineList.getEntry(5), 2));

        medicationList3.add(new PrescribedMedications(medicineList.getEntry(2), 2));
        medicationList3.add(new PrescribedMedications(medicineList.getEntry(4), 2));

        medicationList4.add(new PrescribedMedications(medicineList.getEntry(3), 3));

        medicationList5.add(new PrescribedMedications(medicineList.getEntry(1), 4));
        medicationList5.add(new PrescribedMedications(medicineList.getEntry(3), 2));
        medicationList5.add(new PrescribedMedications(medicineList.getEntry(5), 1));

        paymentList.add(new Cash(new Consultation(new Patient("P0001"), new Staff("S004"), medicationList1), LocalDate.now(), PaymentMethod.CASH, 95.00));
        paymentList.add(new Card(new Consultation(new Patient("P0002"), new Staff("S003"), medicationList2), LocalDate.now(), PaymentMethod.CARD, "1111222233334444", "BAN MEI YAN", LocalDate.of(2024, 3, 1), "490", "Credit Card"));
        paymentList.add(new Cash(new Consultation(new Patient("P0003"), new Staff("S005"), medicationList3), LocalDate.now(), PaymentMethod.CASH, 70.00));
        paymentList.add(new Card(new Consultation(new Patient("P0004"), new Staff("S003"), medicationList4), LocalDate.now(), PaymentMethod.CARD, "4444333322221111", "STONE DING WEI", LocalDate.of(2022, 12, 1), "597", "Debit Card"));
        paymentList.add(new Cash(new Consultation(new Patient("P0005"), new Staff("S005"), medicationList5), LocalDate.now(), PaymentMethod.CASH, 150.00));

        return paymentList;
    }

    @Override
    public int compareTo(Payment p) {
        return this.paymentId.compareTo(p.paymentId);
    }
}
