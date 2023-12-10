package entity;

import java.util.Objects;
/**
 *
 * @author Vickham Foo
 */
public class Admin extends Staff {

    private String password;

    public Admin(String password) {
        this.password = password;
    }

    public Admin(String staffID, String staffName, Character gender, int staffAge, String position, int[] timeTable, String password) {
        super(staffID, staffName, gender, staffAge, position, timeTable);
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Admin other = (Admin) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + ',' + "password=" + password;

    }

}
