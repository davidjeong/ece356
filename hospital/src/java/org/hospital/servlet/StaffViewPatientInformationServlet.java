package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
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
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        List<Patient> patientList = null;
        Hashtable<Integer, String> patientIdToNameMapping = new Hashtable<Integer, String>();
        Hashtable<String, String> cpsoToNameMapping = new Hashtable<String, String>();
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {

            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_INFORMATION_FOR_STAFF);
            rs = cs.executeQuery();

            if (rs != null)
            { 
                patientList = new ArrayList();
                Patient p = null;
                while (rs.next())
                {
                    
                    String healthStatus = (rs.getString("health_status") == null || rs.getString("health_status").isEmpty()) ? "N/A" : rs.getString("health_status");
                    String SINNumber = (rs.getString("sin_number") == null || rs.getString("sin_number").isEmpty()) ? "N/A" : rs.getString("sin_number");
                    String phoneNumber = (rs.getString("phone_number") == null || rs.getString("phone_number").isEmpty()) ? "N/A" : rs.getString("phone_number");
                    String address = (rs.getString("address") == null || rs.getString("address").isEmpty()) ? "N/A" : rs.getString("address");
                    
                    p = new Patient();
                    p.setPatientId(rs.getInt("patient_id"));
                    p.setDefaultDoctor(rs.getString("default_doctor"));
                    p.setHealthStatus(healthStatus);
                    p.setSinNumber(SINNumber);
                    p.setHealthCardNumber(rs.getString("health_card_number"));
                    p.setPhoneNumber(phoneNumber);
                    p.setAddress(address);
                    
                    patientIdToNameMapping.put(rs.getInt("patient_id"), rs.getString("patient_legal_name"));
                    cpsoToNameMapping.put(rs.getString("default_doctor"), rs.getString("doctor_legal_name"));
                    
                    patientList.add(p);
                    
                    logger.info("Adding [" + patientList + "] to patient list");
                }
            }  
        }
        catch (SQLException e)
        {
            logger.error(e.toString());
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
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
            boolean wrote = false;
            if ( patientList != null ) {
                if (!patientList.isEmpty()) {
                    output.append(" { \"output\":\"");
                    output.append("<table class='table table-hover'>");
                    output.append("<thead>");
                    output.append("<tr>");
                    output.append("<th>Patient ID</th>");
                    output.append("<th>Patient Name</th>");
                    output.append("<th>Default Doctor</th>");
                    output.append("<th>Health Status</th>");
                    output.append("<th>Health Card Number</th>");
                    output.append("<th>SIN Number</th>");
                    output.append("<th>Phone Number</th>");
                    output.append("<th>Address</th>");
                    output.append("<th>Edit</th>");
                    output.append("</tr>");
                    output.append("</thead>");              

                    output.append("<tbody>");
                    for(Patient p: patientList){
                        output.append("<tr>");
                        output.append("<td>").append(p.getPatientId()).append("</td>");
                        output.append("<td>").append(patientIdToNameMapping.get(p.getPatientId())).append("</td>");
                        output.append("<td>").append(cpsoToNameMapping.get(p.getDefaultDoctor())).append("</td>");
                        output.append("<td>").append(p.getHealthStatus()).append("</td>");
                        output.append("<td>").append(p.getHealthCardNumber()).append("</td>");
                        output.append("<td>").append(p.getSinNumber()).append("</td>");
                        output.append("<td>").append(p.getPhoneNumber()).append("</td>");
                        output.append("<td>").append(p.getAddress()).append("</td>"); 
                        output.append("<td>").append("<a href='javascript:openEditModal(").append(p.getPatientId()).append(", &#39;").append(patientIdToNameMapping.get(p.getPatientId())).append("&#39;, &#39;").append(cpsoToNameMapping.get(p.getDefaultDoctor())).append("&#39;, &#39;")
                               .append(p.getDefaultDoctor()).append("&#39;, &#39;").append(p.getHealthStatus()).append("&#39;, &#39;").append(p.getHealthCardNumber())
                               .append("&#39;, &#39;").append(p.getSinNumber()).append("&#39;, &#39;").append(p.getPhoneNumber()).append("&#39;, &#39;")
                               .append(p.getAddress()).append("&#39;);', class='btn btn-primary'>Edit</a>").append("</td>");
                        output.append("</tr>");
                    }
                output.append("</tbody>");
                output.append("</table>");
                output.append("\" } ");
                wrote = true;
                } 
            } 
            if (!wrote) {
                output.append("{ \"output\":\"<p>There are no patients.</p>\" } ");
            }
            
            out.println(output.toString());
            out.close();
        }
    }
}
