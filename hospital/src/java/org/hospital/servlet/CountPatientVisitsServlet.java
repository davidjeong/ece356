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
import org.hospital.entities.VisitRecord;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "CountPatientVisitsServlet", urlPatterns = {"/CountPatientVisitsServlet"})
public class CountPatientVisitsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<VisitRecord> visitRecordList = null;
        StringBuilder output = null;
        boolean success = false;
        String NumberVisits = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_VISIT);
            output = new StringBuilder();

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
            if (visitRecordList != null) {
                output.append("<table class='table table-hover'>");
                output.append("<thead>");
                output.append("<tr>");
                output.append("<th>Patient ID</th>");
                output.append("<th>Start Time</th>");
                output.append("<th>End Time</th>");
                output.append("<th>Total Number of Visits</th>");
                output.append("</tr>");
                output.append("</thead>");              
           
                output.append("<tbody>");
                output.append("<tr>");
                output.append("<td>").append(request.getParameter("requested_patient_id")).append("</td>");
                output.append("<td>").append(request.getParameter("start_range")).append("</td>");
                output.append("<td>").append(request.getParameter("end_range")).append("</td>");
                output.append("<td>").append(visitRecordList.size()).append("</td>");
                output.append("</tr>");
                output.append("</tbody>");
                output.append("</table>");
                
                output.append("<table class='table table-hover'>");
                output.append("<thead>");
                output.append("<tr>");
                output.append("<th>Patient ID</th>");
                output.append("<th>CPSO Number</th>");
                output.append("<th>Start Time</th>");
                output.append("<th>End Time</th>");
                output.append("<th>Surgery Name</th>");
                output.append("<th>Prescription</th>");
                output.append("<th>Comments</th>");
                output.append("</tr>");
                output.append("</thead>");
                if (visitRecordList.size() > 0) {
                    output.append("<tbody>");
                    for (VisitRecord v : visitRecordList) {
                        output.append("<tr>");
                        output.append("<td>").append(v.getPatientID()).append("</td>");
                        output.append("<td>").append(v.getCPSONumber()).append("</td>");
                        output.append("<td>").append(v.getStartTime()).append("</td>");
                        output.append("<td>").append(v.getEndTime()).append("</td>");
                        output.append("<td>").append(v.getSurgeryName()).append("</td>");
                        output.append("<td>").append(v.getPrescription()).append("</td>");
                        output.append("<td>").append(v.getComments()).append("</td>");
                        output.append("</tr>");
                    }
                    output.append("</tbody>");
                }
                output.append("</table>");
                 out.println(" { \"success\": \"" + success + "\", \"output\": \"" + output.toString() + "\"} ");
            } else {
                 out.println(" { \"success\": \"" + success + "\", \"output\": \"" + "Failed to retrieve patients." + "\"} ");
            }
            out.close();
        }
    }
}
