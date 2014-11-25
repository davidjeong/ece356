package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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

@WebServlet(name = "StaffViewPatientInformationServlet", urlPatterns = {"/StaffViewPatientInformationServlet"})
public class StaffViewPatientInformationServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(StaffViewPatientInformationServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ResultSet rs = null;
        StringBuilder summaryOutput = null;
        boolean success = false;
        String staff_ID = "";
        List<Patient> PatientInfo = null;
        PreparedStatement preparedStatement = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            String userType = request.getSession().getAttribute("usertype").toString();
            if (userType.equals("staff"))
            {
                staff_ID = request.getSession().getAttribute("username").toString();
            }
            else
            {
                //This part needs to be verified once staff side is completed
                staff_ID = request.getSession().getAttribute("username").toString();
            }
           
            preparedStatement = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_STAFF_PATIENT_INFORMATION);
            rs = preparedStatement.executeQuery();

            if (rs != null)
            { 
                PatientInfo = new ArrayList();
                while (rs.next())
                {
                    Patient p = new Patient (
                            rs.getString("default_doctor"),
                            rs.getString("health_status"),
                            rs.getString("health_card_number"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getInt("patient_id"));
                    PatientInfo.add(p);
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
                    summaryOutput.append("<th>Default Doctor</th>");
                    summaryOutput.append("<th>Patient ID</th>");
                    summaryOutput.append("<th>Health Card Number</th>");
                    summaryOutput.append("<th>Health Status</th>");
                    summaryOutput.append("<th>Phone Number</th>");
                    summaryOutput.append("<th>Address</th>");
                    summaryOutput.append("<th></th>");
                    summaryOutput.append("</tr>");
                    summaryOutput.append("</thead>");              

                    if (PatientInfo.size() > 0) {
                        summaryOutput.append("<tbody>");
                        for(Patient p: PatientInfo){
                            summaryOutput.append("<tr>");
                            summaryOutput.append("<td>").append(p.getDefaultDoctor()).append("</td>");
                            summaryOutput.append("<td id ='patient_id'>").append(p.getPatientId()).append("</td>");
                            summaryOutput.append("<td>").append(p.getHealthCardNumber()).append("</td>");
                            summaryOutput.append("<td>").append(p.getHealthStatus()).append("</td>");
                            summaryOutput.append("<td>").append(p.getPhoneNumber()).append("</td>");
                            summaryOutput.append("<td>").append(p.getAddress()).append("</td>"); 
                            summaryOutput.append("<td>").append("<a href='javascript:openPatientModal(").append(p.getPatientId()).append(");', class='btn btn-primary'>Edit</a>").append("</td>");
                            summaryOutput.append("</tr>");
                        }
                    summaryOutput.append("</tbody>");
                    summaryOutput.append("</table>");
                    } 
                }
                else {
                    summaryOutput.append("<p>This staff does not have any patient assigned to him/her.</p>");
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
