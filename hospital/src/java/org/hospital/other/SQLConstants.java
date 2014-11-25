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
    public static String All_DOCTOR_INFORMATION = "{ call AllDoctorInformation }";
    
    public static String CHECK_PATIENT_DOCTOR_MAPPING = " { call CheckPatientDoctorMapping(?,?) } ";
    
    public static String COUNT_ALL_VISITS = "{ call CountAllVisits(?,?) }";
    public static String COUNT_REVENUE_FROM_SURGERY = "{ call CountRevenueFromSurgery(?,?,?) }";
    public static String COUNT_REVENUE_FROM_ALL_SURGERIES = "{ call CountRevenueFromAllSurgeries(?,?) }";
    
    public static String DELETE_ALL_DOCTOR_STAFF_ASSIGNMENT_FOR_DOCTOR = "{ call DeleteAllDoctorStaffAssignmentsForDoctor(?) }";
    public static String DELETE_DOCTOR_PATIENT_RIGHTS_FOR_PATIENT = "{ call DeleteDoctorPatientRightsForPatient(?,?) }";
    public static String DELETE_PATIENT_DOCTOR_MAPPING = " { call DeletePatientDoctorMapping(?,?) } ";
    public static String DELETE_VISIT_RECORD = " { call DeleteVisitRecord(?,?) } ";
    public static String GET_ALL_SURGERIES = "{ call GetAllSurgeries }";
    public static String GET_DOCTOR_USERNAME= " { call GetDoctorUsername(?) } ";
     
    public static String INSERT_NEW_DOCTOR = "{ call InsertNewDoctorRecord(?,?,?) }";
    public static String INSERT_NEW_DOCTOR_STAFF_ASSIGNMENT= "{ call InsertNewDoctorStaffAssignment(?,?) }";
    public static String INSERT_NEW_PATIENT = "{ call InsertNewPatientRecord(?,?,?,?,?,?,?) }";
    public static String INSERT_NEW_VISIT_RECORD = " { call InsertNewVisitRecord(?,?,?,?,?) } ";
    public static String INSERT_NEW_USER = "{ call InsertNewUserRecord(?,?,?,?)}";
    public static String INSERT_NEW_USER_PATIENT_RIGHTS = "{ call InsertNewUserPatientViewingRights(?,?) }";
    public static String INSERT_PATIENT_DOCTOR_MAPPING = " { call InsertPatientDoctorMapping(?,?) } ";
    
    public static String SELECT_VERIFY_USER = "{ call VerifyUserCredentials(?,?) }";
    
    public static String VIEW_ALL_DOCTORS = "{ call ViewAllDoctors }";
    public static String VIEW_ALL_DOCTOR_WEEKLY_RECORDS = " { call ViewAllDoctorWeeklyRecords } ";
    public static String VIEW_ALL_PATIENT = "{ call ViewAllPatients }";
    public static String VIEW_ALL_PATIENTS_FOR_DOCTOR = " { call ViewAllPatientsForDoctor(?) } ";
    public static String VIEW_ALL_STAFF = "{ call ViewAllStaff }";
    public static String VIEW_ALL_VISIT_RECORDS = "{ call ViewAllVisitRecords () }";
    public static String VIEW_COUNT_PATIENT_VISIT = "{call UniquePatientsGivenDoctor(?,?,?) }";
    
    public static String VIEW_DOCTORS_FOR_PATIENT = "{ call ViewDoctorsForPatient(?) }";
    public static String VIEW_DOCTORS_FOR_STAFF = "{ call ViewDoctorsForStaff(?) }";
    public static String VIEW_DOCTOR_VISIT = "{ call CountDoctorVisits(?,?,?) }";
    
    public static String VIEW_EARNING_BY_DOCTOR = "{ call ViewEarningByDoctor(?,?,?) }";
    public static String VIEW_MY_PATIENTS = "{ call ViewMyPatients(?) }";
    public static String VIEW_OTHER_PATIENTS = " { call ViewOtherPatients(?) } ";
    
    public static String VIEW_PAST_VISIT_RECORD_FOR_DOCTOR = "{ call ViewPastVisitRecordForDoctor(?) }";
    public static String VIEW_PAST_VISIT_RECORD_FOR_STAFF = " { call ViewPastVisitRecordForStaff(?) } ";
    public static String VIEW_PAST_VISIT_RECORD_FOR_PATIENT = "{ call ViewPastVisitRecordForPatient(?) }";
    public static String VIEW_PATIENT_INFORMATION = "{ call ViewPatientInformation(?)}";
    public static String VIEW_PATIENT_INFORMATION_FOR_STAFF = " { call ViewPatientInformationForStaff } ";
    public static String VIEW_PATIENT_VISIT =  "{ call CountPatientVisits(?,?,?) }";
    public static String VIEW_PATIENT_VISIT_RECORDS = "{ call ViewPatientRecords(?) }";
    public static String VIEW_PATIENT_VISIT_RECORDS_FOR_START_TIME = "{ call ViewPatientRecordsForStartTime(?,?) } ";
    public static String VIEW_STAFFS_FOR_DOCTOR = "{ call ViewStaffsForDoctor(?) }";
    public static String VIEW_UPCOMING_DOCTOR_WEEKLY_RECORDS = " { call ViewUpcomingDoctorWeeklyRecords(?) } ";

    public static String VIEW_UPCOMING_VISIT_RECORD = "{ call ViewUpcomingVisitRecord(?) }";
    public static String VIEW_UPCOMING_VISIT_RECORD_FOR_DOCTOR = "{ call ViewUpcomingVisitRecordForDoctor(?) }";
    public static String VIEW_UPCOMING_VISIT_RECORD_FOR_STAFF = "{ call ViewUpcomingVisitRecordForStaff(?) }";
    public static String VIEW_UPCOMING_VISIT_RECORD_FOR_PATIENT = "{ call ViewUpcomingVisitRecordForPatient(?) }";

    public static String UPDATE_APPOINTMENT = " { call UpdateAppointment(?,?,?,?) } ";
    public static String UPDATE_PATIENT_RECORD =" { call UpdatePatientRecord(?,?,?,?,?,?,?) }";
    public static String UPDATE_PERSONAL_RECORD = " { call UpdatePersonalRecord(?,?,?) } ";
    public static String UPDATE_VISIT_RECORD = " { call UpdateVisitRecord(?,?,?,?,?) }";
    public static String UPDATE_STAFF_PATIENT_RECORD = " {call StaffUpdatePatientRecord(?,?,?,?,?,?) }";
    public static String USERNAME_TO_CPSONUMBER = "{ call UsernameToCpsonumber(?) }";
    public static String USERNAME_TO_PATIENTID = " { call UsernameToPatientID(?) }";
    
}