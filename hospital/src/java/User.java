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
    
    String userName;
    String password;
    
    public User (String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    private void setUserName(String userName) {
        this.userName = userName;
    }
    
    private String getUserName() {
        return userName;
    }
    
    private void setPassword(String password) {
        this.password = password;
    }
    
    private String getPassword() {
        return password;
    }
    
}
