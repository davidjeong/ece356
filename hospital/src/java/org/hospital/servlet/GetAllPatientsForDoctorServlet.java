package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.hospital.entities.Doctor;
import org.hospital.entities.Patient;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(name = "GetAllPatientsForDoctorServlet", urlPatterns = {"/GetAllPatientsForDoctorServlet"})
public class GetAllPatientsForDoctorServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(GetAllPatientsForDoctorServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        String cpsoNumber = request.getParameter("cpso_number");
        
        List<Patient> patientList = null;
        
        try {
            if (!cpsoNumber.isEmpty()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_PATIENTS_FOR_DOCTOR);
                int i = 0;
                cs.setString(++i, cpsoNumber);
                rs = cs.executeQuery();

                if (rs != null) {
                    patientList = new ArrayList<Patient>();
                    while (rs.next()) {
                        Patient p = new Patient();
                        p.setLegalName(rs.getString("legal_name"));
                        p.setPatientId(rs.getInt("patient_id"));
                        patientList.add(p);
                    }
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.toString());
        }
        finally {
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
            
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");

            if (patientList != null) {
                if (!patientList.isEmpty()) {
                    output = new StringBuilder();
                    output.append(" { \"success\":\"true\", \"output\": [ ");
                    String delim = "";
                    for (Patient p : patientList) {
                        output.append(delim).append(" { \"patient_id\":\"").append(p.getPatientId()).append("\", \"legal_name\":\"").append(p.getLegalName()).append("\" } ");
                        delim = ",";
                    }    
                    output.append(" ] } ");
                }
            }
            if (output == null) {
                output = new StringBuilder();
                output.append(" { \"success\":\"false\", \"output\":\"There are no patients for this doctor.\" } ");
            }
            out.println(output.toString());
            out.close();
        }
    }
}
