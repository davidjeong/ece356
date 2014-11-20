/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.entities;

/**
 *
 * @author okamayana
 */
public class Patient {

    private String userName;
    private String defaultDoctor;
    private String healthStatus;
    private String healthCardNumber;
    private String sinNumber;
    private String phoneNumber;
    private String address;

    public Patient() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDefaultDoctor() {
        return defaultDoctor;
    }

    public void setDefaultDoctor(String defaultDoctor) {
        this.defaultDoctor = defaultDoctor;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getHealthCardNumber() {
        return healthCardNumber;
    }

    public void setHealthCardNumber(String healthCardNumber) {
        this.healthCardNumber = healthCardNumber;
    }

    public String getSinNumber() {
        return sinNumber;
    }

    public void setSinNumber(String sinNumber) {
        this.sinNumber = sinNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
}
