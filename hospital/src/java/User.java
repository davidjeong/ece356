/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jeong_000
 */
public class User {
    
    private String userName;
    private String password;
    private String legalName;
    private String userType;
    
    public User() {
        
    }
    
    public User (String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
    
    public String getLegalName() {
        return legalName;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String getUserType() {
        return userType;
    }
    
}
