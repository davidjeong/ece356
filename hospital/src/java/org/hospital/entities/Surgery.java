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
public class Surgery {
    private String name;
    private int price;
    
    public Surgery() {
        
    }
    
    public Surgery (String name, int price) {
        this.name = name;
        this.price = price;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getPrice() {
        return price;
    }
}
