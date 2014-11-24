package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.entities.Doctor;
import org.hospital.entities.Patient;
import org.hospital.other.SQLConstants;
import org.hospital.entities.User;
import org.hospital.other.MySQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/UserCreationServlet"})
public class UserCreationServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(UserCreationServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        boolean success = false;
        User newUser = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            SQLConstants.CONN.setAutoCommit(false);
            
            String pw1 = request.getParameter("password");
            String pw2 = request.getParameter("confirm_password");
            
            if (pw1.equals(pw2)) {
                newUser = new User();
                
                newUser.setLegalName(request.getParameter("legal_name"));
                newUser.setUserName(request.getParameter("username"));
                newUser.setPassword(pw1);
                newUser.setUserType(request.getParameter("usertype"));

                if (newUser.isValidUser()) {
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_USER);
                    int i = 0;
                    cs.setString(++i, newUser.getLegalName());
                    cs.setString(++i, newUser.getUserName());
                    cs.setString(++i, newUser.getPassword());
                    cs.setString(++i, newUser.getUserType());
                    cs.executeUpdate();

                    if (newUser.getUserType().equals(SQLConstants.Patient)) {
                        Patient patient = new Patient();
                        patient.setUserName(request.getParameter("username"));
                        patient.setDefaultDoctor(request.getParameter("default_doctor"));
                        patient.setHealthStatus(request.getParameter("health_status"));
                        patient.setHealthCardNumber(request.getParameter("health_card_number"));
                        patient.setSinNumber(request.getParameter("sin_number"));
                        patient.setPhoneNumber(request.getParameter("phone_number"));
                        patient.setAddress(request.getParameter("address"));

                        cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_PATIENT);
                        i = 0;
                        cs.setString(++i, patient.getUserName());
                        cs.setString(++i, patient.getDefaultDoctor());
                        cs.setString(++i, patient.getHealthStatus());
                        cs.setString(++i, patient.getHealthCardNumber());
                        cs.setString(++i, patient.getSinNumber());
                        cs.setString(++i, patient.getPhoneNumber());
                        cs.setString(++i, patient.getAddress());
                        cs.executeUpdate();
                    }
                    else if (newUser.getUserType().equals(SQLConstants.Doctor)) {
                        Doctor doctor = new Doctor();
                        doctor.setUserName(request.getParameter("username"));
                        doctor.setCpsoNumber(request.getParameter("cpso"));
                        doctor.setDepartment(request.getParameter("department"));

                        cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_DOCTOR);
                        i = 0;
                        cs.setString(++i, doctor.getUserName());
                        cs.setString(++i, doctor.getCpsoNumber());
                        cs.setString(++i, doctor.getDepartment());
                        cs.executeUpdate();
                    } 
                    success = true;
                    SQLConstants.CONN.commit();
                }
            }
        } catch (SQLException e) {
            try {
                logger.error(e.toString());
                SQLConstants.CONN.rollback();
            } catch (SQLException ex) {
            }
        } catch (Exception e) {
            try {
                logger.error(e.toString());
                SQLConstants.CONN.rollback();
            } catch (SQLException ex) {
            }
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                }
            }
            try {
                SQLConstants.CONN.setAutoCommit(true);
            } catch (SQLException e) {
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
            
            StringBuilder output = new StringBuilder();
            if (success && newUser != null) {
                output.append("User creation for ").append(newUser.getLegalName()).append(" successful.");
            } else {
                output.append("User creation unsucessful. Mandatory fields may be empty.");
            }
            out.println(" { \"success\": \"" + success + "\", \"output\": \"" + output.toString() + "\"} ");
            out.close();
        }
    }
}
