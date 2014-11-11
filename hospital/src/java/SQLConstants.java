/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Class to house the query names for all SQL queries.
 * @author j7jeong
 */
public class SQLConstants {
    
    public static String admin_username = "root";
    public static String admin_password = "root";
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/ece356";
    
    
    public static String SELECT_VERIFY_USER = "{ call VerifyUserCredentials(?,?) }";
    public static String INSERT_USER = "{ call InsertUserCredentials(?,?,?,?)}";
    
    
}
