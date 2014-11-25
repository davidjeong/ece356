package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(name = "UpdatePatientInformationServlet", urlPatterns = {"/UpdatePatientInformationServlet"})
public class UpdatePatientInformationServlet extends HttpServlet {
    
    Logger logger = LoggerFactory.getLogger(UpdatePatientInformationServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        StringBuilder output = null;
        int res = 0;
        
        String patientId = request.getParameter("patient_id");
        String cpso = request.getParameter("default_doctor");
        String healthStatus = request.getParameter("health_status");
        String healthCardNumber = request.getParameter("health_card_number");
        String sinNumber = request.getParameter("sin_number");
        String phoneNumber = request.getParameter("phone_number");
        String address = request.getParameter("address");
        
        String old_cpso = request.getParameter("cpso");
        
        boolean success = false;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            if (!patientId.isEmpty()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.UPDATE_PATIENT_RECORD);
                int i = 0;
                cs.setInt(++i, Integer.parseInt(patientId));
                cs.setString(++i, cpso);
                cs.setString(++i, healthStatus);
                cs.setString(++i, healthCardNumber);
                cs.setString(++i, sinNumber);
                cs.setString(++i, phoneNumber);
                cs.setString(++i, address);
                
                res = cs.executeUpdate();
                
                if (res > 0) {
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.DELETE_PATIENT_DOCTOR_MAPPING);
                    i = 0;
                    cs.setInt(++i, Integer.parseInt(patientId));
                    cs.setString(++i, old_cpso);
                    
                    res = cs.executeUpdate();
                    if (res > 0) {
                        cs = SQLConstants.CONN.prepareCall(SQLConstants.CHECK_PATIENT_DOCTOR_MAPPING);
                        i = 0;
                        cs.setInt(++i, Integer.parseInt(patientId));
                        cs.setString(++i, cpso);
                        ResultSet rs = null;
                        rs = cs.executeQuery();
                        boolean failed = false;
                        if (rs != null) {
                            if (rs.next()) {
                                int count = rs.getInt("occurrence");
                                if (count == 0) {
                                    cs = SQLConstants.CONN.prepareCall(SQLConstants.GET_DOCTOR_USERNAME);
                                    i = 0;
                                    cs.setString(++i, cpso);
                                    rs = null;
                                    rs = cs.executeQuery();

                                    if (rs != null) {
                                        if (rs.next()) {
                                            String userName = rs.getString("user_name");
                                            cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_PATIENT_DOCTOR_MAPPING);
                                            i = 0;
                                            cs.setInt(++i, Integer.parseInt(patientId));
                                            cs.setString(++i, userName);
                                            res = cs.executeUpdate();
                                            if (res == 0) {
                                                failed = true;
                                            }
                                        }
                                    }
                                }
                            } else {
                                failed = true;
                            }
                        } else {
                            failed = true;
                        }
                        if (!failed) {
                            success = true;
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            if (cs != null) {
                try {
                    cs.close();
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
            
            output = new StringBuilder();
            output.append(" { \"success\":\"").append(success).append("\" } ");
            out.println(output.toString());
            out.close();
            
        }
    }
}
