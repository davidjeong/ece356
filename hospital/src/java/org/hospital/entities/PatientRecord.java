/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.entities;

/**
 *
 * @author l22fu
 */
public class PatientRecord {
 
    private int patient_id;
    private String health_card_number;
    private String sin_number;
    private String phone_number;
    private String address;
    
    public PatientRecord() {
        
    }
    
    public PatientRecord (int patient_id, String health_card_number, String sin_number, String phone_number, String address) {
        this.patient_id = patient_id;
        this.health_card_number = health_card_number;
        this.sin_number = sin_number;
        this.phone_number = phone_number;
        this.address = address;
    }
    
    public int getPatientID(){
        return patient_id;
    }
    
    public String getHealthCardNumber(){
        return health_card_number;
    }
    
    public String getSinNumber(){
        return sin_number;
    }
    
    public String getPhoneNumber(){
        return phone_number;
    }
    
    public String getAddress(){
        return address;
    }
    
}
