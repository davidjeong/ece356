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
        
        ResultSet rs = null;
        StringBuilder summaryOutput = null;
        boolean success = false;
        String patient_ID = "";
        Patient PatientInfo = new Patient ();
        String sql = "select * from patient_schema where patient_id=?";
        PreparedStatement preparedStatement = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            String userType = request.getSession().getAttribute("usertype").toString();
            if (userType.equals("patient"))
            {
                patient_ID = request.getSession().getAttribute("patientid").toString();
            }
            else
            {
                //This part needs to be verified once staff side is completed
                patient_ID = request.getSession().getAttribute("requested_patient_id").toString();
            }
           
            preparedStatement = SQLConstants.CONN.prepareStatement(sql);
            preparedStatement.setString(1, patient_ID);
            rs = preparedStatement.executeQuery();

            if (rs != null)
            { 
                if (rs.next())
                {
                    PatientInfo.setPatientId(rs.getInt("patient_id"));
                    PatientInfo.setHealthCardNumber(rs.getString("health_card_number"));
                    PatientInfo.setSinNumber(rs.getString("sin_number"));
                    PatientInfo.setAddress(rs.getString("address"));
                    PatientInfo.setPhoneNumber(rs.getString("phone_number"));
                    logger.info("Adding [" + PatientInfo + "] to patient list");
                }
                success = true;
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
            
            if (success) {
                summaryOutput = new StringBuilder();
                if ( PatientInfo != null) {
                    summaryOutput.append("<table class='table table-hover'>");
                    summaryOutput.append("<thead>");
                    summaryOutput.append("<tr>");
                    summaryOutput.append("<th>PatientID</th>");
                    summaryOutput.append("<th>Health Card Number</th>");
                    summaryOutput.append("<th>Sin Number</th>");
                    summaryOutput.append("<th>Phone Number</th>");
                    summaryOutput.append("<th>Address</th>");
                    summaryOutput.append("</tr>");
                    summaryOutput.append("</thead>");              

                    summaryOutput.append("<tbody>");
                    summaryOutput.append("<tr>");
                    summaryOutput.append("<td>").append(PatientInfo.getPatientId()).append("</td>");
                    summaryOutput.append("<td>").append(PatientInfo.getHealthCardNumber()).append("</td>");
                    summaryOutput.append("<td>").append(PatientInfo.getSinNumber()).append("</td>");
                    summaryOutput.append("<td>").append(PatientInfo.getPhoneNumber()).append("</td>");
                    summaryOutput.append("<td>").append(PatientInfo.getAddress()).append("</td>"); 
                    summaryOutput.append("</tr>");
                    summaryOutput.append("</tbody>");
                    summaryOutput.append("</table>");
                } else {
                    summaryOutput.append("<p>There is no personal patient information available.</p>");
                }
                out.println(" { \"success\":\"" + success + "\", \"summaryOutput\": \"" + summaryOutput.toString() + "\"} ");
            }
            else {
                out.println("{ \"success\":\"" + success + "\", \"output\":\"Mandatory fields are empty.\" }");
            }
            out.close();
        }
    }
}
