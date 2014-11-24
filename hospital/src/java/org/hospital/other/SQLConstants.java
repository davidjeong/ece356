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
    public static String INSERT_NEW_VISIT_RECORD = " { call InsertNewVisitRecord(?,?,?,?) } ";
    public static String CHECK_IF_DOCTOR_IS_ASSIGNED_TO_PATIENT = " { call CheckIfDoctorIsAssignedToPatient(?,?) } ";
    
    public static String USERNAME_TO_CPSONUMBER = "{ call UsernameToCpsonumber(?) }";
    public static String USERNAME_TO_PATIENTID = " { call UsernameToPatientID(?) }";
    public static String VIEW_ASSIGNED_DOCTOR = "{ call ViewAssignedDoctor(?) }";
    public static String VIEW_DOCTORS_FOR_STAFF = "{ call ViewDoctorsForStaff(?) }";
    public static String VIEW_PAST_VISIT_RECORD = "{ call ViewPastVisitRecord(?) }";
    public static String VIEW_UPCOMING_VISIT_RECORD = "{ call ViewUpcomingVisitRecord(?) }";
    public static String VIEW_ALL_VISIT_RECORDS = "{ call ViewAllVisitRecords () }";
    public static String VIEW_DOCTOR_VISIT = "{ call CountDoctorVisits(?,?,?) }";
    public static String VIEW_PATIENT_VISIT =  "{ call CountPatientVisits(?,?,?) }";
    public static String VIEW_COUNT_PATIENT_VISIT = "{call UniquePatientsGivenDoctor(?,?,?) }";
    public static String All_DOCTOR_INFORMATION = "{ call AllDoctorInformation }";
    public static String VIEW_ALL_PATIENT = "{ call ViewAllPatients }";
    public static String VIEW_ALL_DOCTORS = "{ call ViewAllDoctors }";
    public static String VIEW_STAFFS_FOR_DOCTOR = "{ call ViewStaffsForDoctor(?) }";
    public static String VIEW_PATIENT_VISIT_RECORDS = "{ call ViewPatientRecords(?) }";
    public static String VIEW_ALL_DOCTOR_WEEKLY_RECORDS = " { call ViewAllDoctorWeeklyRecords(?) } ";
    public static String VIEW_UPCOMING_DOCTOR_WEEKLY_RECORDS = " { call ViewUpcomingDoctorWeeklyRecords(?) } ";
    public static String VIEW_DOCTORS_FOR_PATIENT = "{ call ViewDoctorsForPatient(?) }";
    public static String GET_ALL_SURGERIES = "{ call GetAllSurgeries }";
    public static String VIEW_EARNING_BY_DOCTOR = "{ call ViewEarningByDoctor(?,?,?) }";
    public static String COUNT_ALL_VISITS = "{ call CountAllVisits(?,?) }";

}