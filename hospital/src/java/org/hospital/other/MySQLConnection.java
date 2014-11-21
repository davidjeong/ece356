package org.hospital.other;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    
    public static void establish() {
        boolean success = false;
        try {
            Class.forName(SQLConstants.DRIVER);
            SQLConstants.CONN = DriverManager.getConnection(SQLConstants.URL, SQLConstants.ADMIN_USERNAME, SQLConstants.ADMIN_PASSWORD);
            success = true;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class is not found");
        }
        catch (SQLException e) {
            System.out.println("SQLException thrown while trying to establish connection");
        }
        finally {
            if (success) {
                System.out.println("Connection established to database.");
            }
        }
    }
}
