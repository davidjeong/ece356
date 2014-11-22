package org.hospital.other;

/**
 * Class to house the query names for all SQL queries.
 */
import java.sql.Connection;
import org.hospital.entities.User;

public class SQLConstants {
    
    public static User USER = null;
    public static Connection CONN = null;
    
    public static String ADMIN_USERNAME = "root";
    public static String ADMIN_PASSWORD = "root";
    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/ece356";
    
    public static final String Doctor = "doctor";
    public static final String Patient = "patient";
    public static final String Staff = "staff";
    public static final String Finance = "finance";
    public static final String Legal = "legal";
       
    // Our query names
    public static String SELECT_VERIFY_USER = "{ call VerifyUserCredentials(?,?) }";
    public static String INSERT_NEW_USER = "{ call InsertNewUserRecord(?,?,?,?)}";
    public static String INSERT_NEW_PATIENT = "{ call InsertNewPatientRecord(?,?,?,?,?,?,?) }";
    public static String INSERT_NEW_DOCTOR = "{ call InsertNewDoctorRecord(?,?,?) }";
    
    public static String USERNAME_TO_CPSONUMBER = "{ call UsernameToCpsonumber(?) }";
    public static String VIEW_ASSIGNED_DOCTOR = "{ call ViewAssignedDoctor(?) }";
    public static String VIEW_DOCTORS_FOR_STAFF = "{ call ViewDoctorsForStaff(?) }";
    public static String VIEW_PATIENT_VISIT = "{ call patientsCountGivenTimePeriod(?,?) }";
}