package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@WebServlet(name = "PatientInformationServlet", urlPatterns = {"/PatientInformationServlet"})
public class PatientInformationServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(PatientInformationServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        Patient p = null;
        
        PreparedStatement preparedStatement = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            
            String patient_id = request.getSession().getAttribute("patientid").toString();
            
            if (!patient_id.isEmpty()) {
                //This part needs to be verified once staff side is completed
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_INFORMATION);
                cs.setInt(1, Integer.parseInt(patient_id));
                rs = cs.executeQuery();

                if (rs != null)
                { 
                    if (rs.next())
                    {
                        p = new Patient();
                        String address = rs.getString("address") == null ? "" : rs.getString("address");
                        String phoneNumber = rs.getString("phone_number") == null ? "" : rs.getString("phone_number");
                        
                        p.setAddress(address);
                        p.setPhoneNumber(phoneNumber);
                    }
                }  
            }
        }
        catch (SQLException e)
        {
            logger.error(e.toString());
        }
        finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                    } 
                catch (SQLException ex) {
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
            
            output = new StringBuilder();
            if (p != null) {
                output.append(" { \"success\":\"true\", \"phone_number\":\"").append(p.getPhoneNumber()).append("\", \"address\":\"").append(p.getAddress()).append("\" } ");
            } else {
                output.append(" { \"success\":\"false\" } ");
            }
            
            out.println(output.toString());
            out.close();
        }
    }
}
