/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.entities;

/**
 *
 * @author okamayana
 */
public class Doctor {

    private String userName;
    private String cpsoNumber;
    private String department;

    public Doctor() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
