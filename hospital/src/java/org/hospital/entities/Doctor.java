package org.hospital.entities;

public class Doctor extends User {

    private String cpsoNumber;
    private String department;

    public Doctor() {
    }

    public String getCpsoNumber() {
        return cpsoNumber;
    }

    public void setCpsoNumber(String cpsoNumber) {
        this.cpsoNumber = cpsoNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
