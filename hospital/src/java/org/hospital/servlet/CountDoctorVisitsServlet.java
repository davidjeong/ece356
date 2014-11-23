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
        StringBuilder summaryOutput = null;
        StringBuilder allOutput = null;
        boolean success = false;
        int distinctPatients = 0;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_DOCTOR_VISIT);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
            long start_time = sdf.parse(request.getParameter("start_range")).getTime();
            long end_time = sdf.parse(request.getParameter("end_range")).getTime();
            Timestamp time1 = new Timestamp(start_time);
            Timestamp time2 = new Timestamp(end_time);
            String cpso = request.getParameter("cpso");
            
            if (!cpso.isEmpty()) {
                cs.setString(1, cpso);
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

                    cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_COUNT_PATIENT_VISIT);
                    cs.setString(1, request.getParameter("cpso"));
                    cs.setTimestamp(2, time1);
                    cs.setTimestamp(3, time2);
                    rs = cs.executeQuery();
                    if (rs != null)
                    { 
                        if(rs.next()) 
                        {
                            distinctPatients = rs.getInt("patients");
                        }
                    }
                    success = true;
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
            
            if (success) {
                summaryOutput = new StringBuilder();
                allOutput = new StringBuilder();
                
                if (patientList != null) {
                    if (!patientList.isEmpty()) {
                        summaryOutput.append("<table class='table table-hover'>");
                        summaryOutput.append("<thead>");
                        summaryOutput.append("<tr>");
                        summaryOutput.append("<th>CPSO Number</th>");
                        summaryOutput.append("<th>Start Time</th>");
                        summaryOutput.append("<th>End Time</th>");
                        summaryOutput.append("<th>Distinct Patients Seen</th>");
                        summaryOutput.append("</tr>");
                        summaryOutput.append("</thead>");              

                        summaryOutput.append("<tbody>");
                        summaryOutput.append("<tr>");
                        summaryOutput.append("<td>").append(request.getParameter("cpso")).append("</td>");
                        summaryOutput.append("<td>").append(request.getParameter("start_range")).append("</td>");
                        summaryOutput.append("<td>").append(request.getParameter("end_range")).append("</td>");
                        summaryOutput.append("<td>").append(distinctPatients).append("</td>");
                        summaryOutput.append("</tr>");
                        summaryOutput.append("</tbody>");
                        summaryOutput.append("</table>");

                        allOutput.append("<table class='table table-hover'>");
                        allOutput.append("<thead>");
                        allOutput.append("<tr>");
                        allOutput.append("<th>Patient ID</th>");
                        allOutput.append("</tr>");
                        allOutput.append("</thead>");
                        if (patientList.size() > 0) {
                            allOutput.append("<tbody>");
                            for (Patient p : patientList) {
                                allOutput.append("<tr>");
                                allOutput.append("<td><a href='openDetails(this);'>").append(p.getPatientId()).append("</a></td>");
                                allOutput.append("</tr>");
                            }
                            allOutput.append("</tbody>");
                        }
                        allOutput.append("</table>");
                    } else {
                        summaryOutput.append("<p>There is no summary for this doctor.</p>");
                        allOutput.append("<p>There are no records for this doctor.</p>");
                    }
                    out.println(" { \"success\":\"" + success + "\", \"summaryOutput\": \"" + summaryOutput.toString() + "\", \"allOutput\":\"" + allOutput.toString() + "\"} ");
                } else {
                    out.println(" { \"success\":\"" + success + "\", \"summaryOutput\": \"<p>There are no records for this doctor.</p>\", \"allOutput\":\"<p>There are no records for this doctor.</p>\"} ");
                }
            }
            else {
                out.println("{ \"success\":\"" + success + "\", \"output\":\"Mandatory fields are empty.\" }");
            }
            out.close();
        }
    }
}