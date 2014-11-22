/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.entities.Patient;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/DoctorPatientViewingServlet"})
public class DoctorPatientViewingServlet extends HttpServlet {
    
    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallableStatement cs = null;
        ResultSet rs = null;

        List<Patient> patientList = null;

        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }

        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ASSIGNED_DOCTOR);
            String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();
            if (!cpsoNumber.isEmpty()) {
                int i = 0;
                cs.setString(++i, cpsoNumber);
                logger.info("Adding [" + request.getSession().getAttribute("cpsonumber").toString() + "] to patient list");
                rs = cs.executeQuery();
                if (rs != null) {
                    patientList = new ArrayList();
                    while (rs.next()) {
                        Patient p = new Patient();
                        p.setPatientId(rs.getInt("patient_id"));
                        p.setLegalName(rs.getString("patient_legal_name"));
                        p.setDefaultDoctor(rs.getString("doctor_legal_name"));
                        p.setHealthStatus(rs.getString("health_status"));
                        patientList.add(p);
                        logger.info("Adding [" + p + "] to patient list");
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException ex) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                }
            }
            if (patientList != null) {
                request.getSession().setAttribute("PatientList", patientList);
            }
        }
    }
}