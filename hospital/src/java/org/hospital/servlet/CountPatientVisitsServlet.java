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
import org.hospital.entities.VisitRecord;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "CountPatientVisitsServlet", urlPatterns = {"/CountPatientVisitsServlet"})
public class CountPatientVisitsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<VisitRecord> visitRecordList = null;
        StringBuilder summaryOutput = null;
        StringBuilder allOutput = null;
        boolean success = false;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_VISIT);
            summaryOutput = new StringBuilder();

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
            long start_time = sdf.parse(request.getParameter("start_range")).getTime();
            long end_time = sdf.parse(request.getParameter("end_range")).getTime();
            Timestamp time1 = new Timestamp(start_time);
            Timestamp time2 = new Timestamp(end_time);

            cs.setString(1, request.getParameter("requested_patient_id"));
            cs.setTimestamp(2, time1);
            cs.setTimestamp(3, time2);
            rs = cs.executeQuery();
            if (rs != null)
            { 
                visitRecordList = new ArrayList();
                while (rs.next())
                {
                    VisitRecord v = new VisitRecord(rs.getInt("patient_id"),
                            rs.getString("cpso_number"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"),
                    rs.getString("surgery_name"), rs.getString("prescription"),
                            rs.getString("comments"), rs.getString("diagnosis"));
                    visitRecordList.add(v);
                    logger.info("Adding [" + v + "] to visit list");
                }
                success = true;
            }
        }   
        catch (SQLException e)
        {
            logger.error(e.toString());
        }
        catch (ParseException e)    
        {
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
            
            if (success) {
                summaryOutput = new StringBuilder();
                allOutput = new StringBuilder();
                if (visitRecordList != null) {
                    if (!visitRecordList.isEmpty()) {
                        summaryOutput.append("<table class='table table-hover'>");
                        summaryOutput.append("<thead>");
                        summaryOutput.append("<tr>");
                        summaryOutput.append("<th>Patient ID</th>");
                        summaryOutput.append("<th>Start Time</th>");
                        summaryOutput.append("<th>End Time</th>");
                        summaryOutput.append("<th>Total Number of Visits</th>");
                        summaryOutput.append("</tr>");
                        summaryOutput.append("</thead>");              
                        summaryOutput.append("<tbody>");
                        summaryOutput.append("<tr>");
                        summaryOutput.append("<td>").append(request.getParameter("requested_patient_id")).append("</td>");
                        summaryOutput.append("<td>").append(request.getParameter("start_range")).append("</td>");
                        summaryOutput.append("<td>").append(request.getParameter("end_range")).append("</td>");
                        summaryOutput.append("<td>").append(visitRecordList.size()).append("</td>");
                        summaryOutput.append("</tr>");
                        summaryOutput.append("</tbody>");
                        summaryOutput.append("</table>");
                        
                        allOutput.append("<table class='table table-hover'>");
                        allOutput.append("<thead>");
                        allOutput.append("<tr>");
                        allOutput.append("<th>Patient ID</th>");
                        allOutput.append("<th>CPSO Number</th>");
                        allOutput.append("<th>Start Time</th>");
                        allOutput.append("<th>End Time</th>");
                        allOutput.append("<th>Surgery Name</th>");
                        allOutput.append("<th>Prescription</th>");
                        allOutput.append("<th>Comments</th>");
                        allOutput.append("</tr>");
                        allOutput.append("</thead>");
                        if (visitRecordList.size() > 0) {
                            allOutput.append("<tbody>");
                            for (VisitRecord v : visitRecordList) {
                                allOutput.append("<tr>");
                                allOutput.append("<td>").append(v.getPatientID()).append("</td>");
                                allOutput.append("<td>").append(v.getCPSONumber()).append("</td>");
                                allOutput.append("<td>").append(v.getStartTime()).append("</td>");
                                allOutput.append("<td>").append(v.getEndTime()).append("</td>");
                                allOutput.append("<td>").append(v.getSurgeryName()).append("</td>");
                                allOutput.append("<td>").append(v.getPrescription()).append("</td>");
                                allOutput.append("<td>").append(v.getComments()).append("</td>");
                                allOutput.append("</tr>");
                            }
                            allOutput.append("</tbody>");
                        }
                        allOutput.append("</table>");
                    }
                    else {
                        summaryOutput.append("<p>There is no summary for this patient.</p>");
                        allOutput.append("<p>There are no records for this patient.</p>");
                    }
                    out.println(" { \"success\":\"" + success + "\", \"summaryOutput\": \"" + summaryOutput.toString() + "\", \"allOutput\":\"" + allOutput.toString() + "\"} ");
                } else {
                    out.println(" { \"success\":\"" + success + "\", \"summaryOutput\": \"<p>There are no records for this patient.</p>\", \"allOutput\":\"<p>There are no records for this patient.</p>\"} ");
                }
            } else {
                out.println("{ \"success\":\"" + success + "\", \"output\":\"Mandatory fields are empty.\" }");
            }
            out.close();
        }
    }
}
