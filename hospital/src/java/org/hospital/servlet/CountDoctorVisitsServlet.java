/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@WebServlet(name = "CountDoctorVisitsServlet", urlPatterns = {"/CountDoctorVisitsServlet"})
public class CountDoctorVisitsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(CountDoctorVisitsServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Patient> patientList = null;
        StringBuilder output = null;
        boolean success = false;
        String distinctPatients = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_DOCTOR_VISIT);
            output = new StringBuilder();

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
            long start_time = sdf.parse(request.getParameter("start_range")).getTime();
            long end_time = sdf.parse(request.getParameter("end_range")).getTime();
            Timestamp time1 = new Timestamp(start_time);
            Timestamp time2 = new Timestamp(end_time);

            cs.setString(1, request.getParameter("cpso"));
            cs.setTimestamp(2, time1);
            cs.setTimestamp(3, time2);
            rs = cs.executeQuery();
            if (rs != null)
            { 
                patientList = new ArrayList();
                while (rs.next())
                {
                    Patient p = new Patient();
                    p.setPatientId(rs.getInt("patient_id"));
                    patientList.add(p);
                    logger.info("Adding [" + p + "] to patient list");
                }
                success = true;
            }
            
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_COUNT_PATIENT_VISIT);
            cs.setString(1, request.getParameter("cpso"));
            rs = cs.executeQuery();
            if (rs != null)
            { 
                while (rs.next())
                {
                    distinctPatients = rs.getString("count(distinct v.patient_id)");
                }
            } 
        }
        catch (SQLException e)
        {
            logger.error(e.toString());
        }
        catch (ParseException e)    
        {
            
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
            
            output = new StringBuilder();
            if (patientList != null) {
                output.append("<table class='table table-hover'>");
                output.append("<thead>");
                output.append("<tr>");
                output.append("<th>CPSO Number</th>");
                output.append("<th>Start Time</th>");
                output.append("<th>End Time</th>");
                output.append("<th>Distinct Patients Seen</th>");
                output.append("</tr>");
                output.append("</thead>");              
           
                output.append("<tbody>");
                output.append("<tr>");
                output.append("<td>").append(request.getParameter("cpso")).append("</td>");
                output.append("<td>").append(request.getParameter("start_range")).append("</td>");
                output.append("<td>").append(request.getParameter("end_range")).append("</td>");
                output.append("<td>").append(distinctPatients).append("</td>");
                output.append("</tr>");
                output.append("</tbody>");
                output.append("</table>");
                
                output.append("<table class='table table-hover'>");
                output.append("<thead>");
                output.append("<tr>");
                output.append("<th>Patient ID</th>");
                output.append("</tr>");
                output.append("</thead>");
                if (patientList.size() > 0) {
                    output.append("<tbody>");
                    for (Patient p : patientList) {
                        output.append("<tr>");
                        output.append("<td><a href='openDetails(this);'>").append(p.getPatientId()).append("</a></td>");
                        output.append("</tr>");
                    }
                    output.append("</tbody>");
                }
                output.append("</table>");
                 out.println(" { \"success\": \"" + success + "\", \"output\": \"" + output.toString() + "\"} ");
            } else {
                 out.println(" { \"success\": \"" + success + "\", \"output\": \"" + "Invalid CPSO Number, please try again." + "\"} ");
            }
            out.close();
        }
    }
}