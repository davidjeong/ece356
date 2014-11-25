package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;

/**
 *
 * @author okamayana
 */
@WebServlet(urlPatterns = {"/DoctorUpdateStaffAssignmentServlet"})
public class DoctorUpdateStaffAssignmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();
        String[] staffs = request.getParameterValues("staffs[]");

        CallableStatement csDelete = null;
        CallableStatement csInsert = null;

        boolean success = true;
        
        PrintWriter out = response.getWriter();

        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }

        try {
            csDelete = SQLConstants.CONN.prepareCall(SQLConstants.DELETE_ALL_DOCTOR_STAFF_ASSIGNMENT_FOR_DOCTOR);
            csDelete.setString(1, cpsoNumber);
            csDelete.executeUpdate();

            if (staffs != null && staffs.length > 0) {
                csInsert = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_DOCTOR_STAFF_ASSIGNMENT);
                for (String staff : staffs) {
                    csInsert.setString(1, cpsoNumber);
                    csInsert.setString(2, staff);
                    csInsert.addBatch();
                }
                csInsert.executeBatch();
            } else {
                success = false;
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        } finally {
            StringBuilder output = new StringBuilder();
            if (success) {
                output.append("Successfully updated staff assignment list");
            } else {
                output.append("Error updating staff assignment list");
            }
            
            out.println(" { \"success\": \"" + success + "\", \"output\": \"" + output.toString() + "\"} ");
            out.close();
        }
    }
}
