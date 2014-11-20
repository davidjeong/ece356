package org.hospital.entities;

/**
 * Class to house the query names for all SQL queries.
 */
import java.sql.Connection;

public class SQLConstants {
    
    public static User USER = null;
    public static Connection CONN = null;
    
    public static String ADMIN_USERNAME = "root";
    public static String ADMIN_PASSWORD = "root";
    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/ece356";
    
    public static enum USER_TYPE {Doctor, Patient, Staff, Finance, Legal};
       
    // Our query names
    public static String SELECT_VERIFY_USER = "{ call VerifyUserCredentials(?,?) }";
    public static String INSERT_NEW_USER = "{ call InsertNewUserRecord(?,?,?,?)}";
    
}