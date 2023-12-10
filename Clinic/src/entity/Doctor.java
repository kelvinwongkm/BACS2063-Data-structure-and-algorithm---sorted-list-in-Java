package entity;

import adt.LinkedList;
import adt.ListInterface;
import java.util.Objects;

/**
 *
 * @author Vickham Foo
 */
public class Doctor extends Staff {

    private ListInterface<Patient> consultationHistory = new LinkedList();

    public Doctor(String staffID, String staffName, Character gender, int staffAge, String position, int[] timeTable, ListInterface consultationHistory) {
        super(staffID, staffName, gender, staffAge, position, timeTable);
        this.consultationHistory = consultationHistory;
    }

    public ListInterface getConsultationHistory() {
        return consultationHistory;
    }

    public void setConsultationHistory(ListInterface consultationHistory) {
        this.consultationHistory = consultationHistory;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
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
        final Doctor other = (Doctor) obj;
        if (!Objects.equals(this.consultationHistory, other.consultationHistory)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + ',' + "Consultation History=" + consultationHistory;
    }

}
