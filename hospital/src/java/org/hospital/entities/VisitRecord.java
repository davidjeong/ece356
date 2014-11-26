/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.entities;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author l22fu
 */
public class VisitRecord {
 
    private int patient_id;
    private String cpso_number;
    private Timestamp start_time;
    private Timestamp end_time;
    private String surgery_name;
    private String prescription;
    private String comments;
    private String diagnosis;
    private String action;
    private Timestamp inserted_time;
    
    public VisitRecord() {
        
    }
    
    public VisitRecord (int patient_id, String cpso_number, Timestamp start_time, Timestamp end_time, String surgery_name, String prescription, String comments, String diagnosis) {
        this.patient_id = patient_id;
        this.cpso_number = cpso_number;
        this.start_time = start_time;
        this.end_time = end_time;
        this.surgery_name = surgery_name;
        this.prescription = prescription;
        this.comments = comments;
        this.diagnosis = diagnosis;
    }
    
    public VisitRecord (int patient_id, String cpso_number, Timestamp start_time, Timestamp end_time) {
        this.patient_id = patient_id;
        this.cpso_number = cpso_number;
        this.start_time = start_time;
        this.end_time = end_time;
    }
    
    public int getPatientID(){
        return patient_id;
    }
    
    public String getCPSONumber() {
        return cpso_number;
    }
    
    public Date getStartTime() {
        return start_time;
    }
    
    public Date getEndTime() {
        return end_time;
    }
    
    public String getSurgeryName() {
        return surgery_name;
    }
    
    public void setPrescription(String name) {
        this.prescription = name;
    }
    
    public String getPrescription() {
        return prescription;
    }
    
    public void setComments(String c) {
        this.comments = c;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setDiagnosis(String d) {
        this.diagnosis = d;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setAction(String a) {
        this.action = a;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setInsertedTime (Timestamp t) {
        this.inserted_time = t;
    }
    
    public Date getInsertedTime() {
        return inserted_time;
    }
}
