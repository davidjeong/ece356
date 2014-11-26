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

@WebServlet(name = "CountDoctorVisitsServlet", urlPatterns = {"/CountDoctorVisitsServlet"})
public class CountDoctorVisitsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(CountDoctorVisitsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<VisitRecord> visitList = null;
        StringBuilder summaryOutput = null;
        StringBuilder allOutput = null;
        boolean success = false;
        int distinctPatients = 0;
        int doctorRevenue = 0;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_DOCTOR_VISIT);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");
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
                   visitList = new ArrayList();
                   VisitRecord vr = null;
                    while (rs.next())
                    {
                        String surgery_name = ((rs.getString("surgery_name") == null || rs.getString("surgery_name").isEmpty()) ? "N/A" : rs.getString("surgery_name"));
                        String prescription = ((rs.getString("prescription") == null || rs.getString("prescription").isEmpty()) ? "N/A" : rs.getString("prescription"));
                        String comments = ((rs.getString("comments") == null || rs.getString("comments").isEmpty()) ? "N/A" : rs.getString("comments"));
                        String diagnosis = ((rs.getString("diagnosis") == null || rs.getString("diagnosis").isEmpty()) ? "N/A" : rs.getString("diagnosis"));
                        
                        vr = new VisitRecord( rs.getInt("patient_id"),
                                                          rs.getString("cpso_number"),
                                                          rs.getTimestamp("start_time"),
                                                          rs.getTimestamp("end_time"),
                                                          surgery_name,
                                                          prescription,
                                                          comments,
                                                          diagnosis);
                        visitList.add(vr);    
                        logger.info("Adding [" + vr + "] to patient list");
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
                    
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_EARNING_BY_DOCTOR);
                    cs.setString(1, request.getParameter("cpso"));
                    cs.setTimestamp(2, time1);
                    cs.setTimestamp(3, time2);
                    rs = cs.executeQuery();
                    if (rs != null)
                    { 
                        if(rs.next()) 
                        {
                            doctorRevenue = rs.getInt("earning");
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
                
                if ( visitList != null) {
                    if (! visitList.isEmpty()) {
                        summaryOutput.append("<table class='table table-hover'>");
                        summaryOutput.append("<thead>");
                        summaryOutput.append("<tr>");
                        summaryOutput.append("<th>CPSO Number</th>");
                        summaryOutput.append("<th>Start Time</th>");
                        summaryOutput.append("<th>End Time</th>");
                        summaryOutput.append("<th>Distinct Patients Seen</th>");
                        summaryOutput.append("<th>Revenue Generated ($)</th>");
                        summaryOutput.append("</tr>");
                        summaryOutput.append("</thead>");              

                        summaryOutput.append("<tbody>");
                        summaryOutput.append("<tr>");
                        summaryOutput.append("<td>").append(request.getParameter("cpso")).append("</td>");
                        summaryOutput.append("<td>").append(request.getParameter("start_range")).append("</td>");
                        summaryOutput.append("<td>").append(request.getParameter("end_range")).append("</td>");
                        summaryOutput.append("<td>").append(distinctPatients).append("</td>");
                        summaryOutput.append("<td>").append(doctorRevenue).append("</td>");
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
                        allOutput.append("<th>Prescription</th>");
                        allOutput.append("<th>Surgery Name</th>");
                        allOutput.append("<th>Diagnosis</th>");
                        allOutput.append("<th>Comments</th>");
                        allOutput.append("</tr>");
                        allOutput.append("</thead>");
                        if ( visitList.size() > 0) {
                            allOutput.append("<tbody>");
                            for (VisitRecord vr :  visitList) {
                                allOutput.append("<tr>");
                                allOutput.append("<td>").append(vr.getPatientID()).append("</td>");
                                allOutput.append("<td>").append(vr.getCPSONumber()).append("</td>");
                                allOutput.append("<td>").append(vr.getStartTime().toString()).append("</td>");
                                allOutput.append("<td>").append(vr.getEndTime().toString()).append("</td>");
                                allOutput.append("<td>").append(vr.getPrescription()).append("</td>");
                                allOutput.append("<td>").append(vr.getSurgeryName()).append("</td>");
                                allOutput.append("<td>").append(vr.getDiagnosis()).append("</td>");
                                allOutput.append("<td>").append(vr.getComments()).append("</td>");
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