/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/DoctorPatientViewingUpdateServlet"})
public class DoctorPatientViewingUpdateServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientId = request.getParameter("patientId");
        String[] doctorIds = request.getParameterValues("doctors[]");

        boolean success = true;

        PrintWriter out = response.getWriter();

        if (patientId != null) {
            CallableStatement csDelete = null;
            CallableStatement csInsert = null;

            if (SQLConstants.CONN == null) {
                MySQLConnection.establish();
            }

            try {
                csDelete = SQLConstants.CONN.prepareCall(SQLConstants.DELETE_DOCTOR_PATIENT_RIGHTS_FOR_PATIENT);
                csDelete.setString(1, patientId);
                csDelete.executeUpdate();

                csInsert = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_USER_PATIENT_RIGHTS);
                for (String doctorId : doctorIds) {
                    csInsert.setString(1, doctorId);
                    csInsert.setString(2, patientId);
                    csInsert.addBatch();
                }

                csInsert.executeBatch();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                StringBuilder output = new StringBuilder();
                if (success) {
                    output.append("Changes applied successfully");
                } else {
                    output.append("Error applying changes");
                }

                out.println(" { \"success\": \"" + success + "\", \"output\": \"" + output.toString() + "\"} ");
                out.close();
            }
        } else {
            out.println(" { \"success\": \"" + "false" + "\", \"output\": \"" + "You must have a patient selected to apply changes!" + "\"} ");
        }
    }
}