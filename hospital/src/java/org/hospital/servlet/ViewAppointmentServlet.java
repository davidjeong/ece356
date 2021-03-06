package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@WebServlet(name = "ViewAppointmentServlet", urlPatterns = {"/ViewAppointmentServlet"})
public class ViewAppointmentServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallableStatement cs = null;
        ResultSet rs = null;
        List<VisitRecord> pastList = null;
        List<VisitRecord> upcomingList = null;
        StringBuilder pastSb = null;
        StringBuilder upcomingSb = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        String userType = request.getSession().getAttribute("usertype").toString();
        String userName = request.getSession().getAttribute("username").toString();
        
        try {
            
            if(userType.equals(SQLConstants.Doctor)) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_UPCOMING_VISIT_RECORD_FOR_DOCTOR);
            }
            else if (userType.equals(SQLConstants.Staff)) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_UPCOMING_VISIT_RECORD_FOR_STAFF);
            }
            else if (userType.equals(SQLConstants.Patient)) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_UPCOMING_VISIT_RECORD_FOR_PATIENT);
            }
            if (userType.equals(SQLConstants.Doctor) || userType.equals(SQLConstants.Staff) || userType.equals(SQLConstants.Patient)) {
                upcomingList = new ArrayList<VisitRecord>();
                
                int i=0;
                cs.setString(++i, userName);
                rs = cs.executeQuery();
                VisitRecord vr = null;
                if (rs != null)
                { 
                    upcomingList = new ArrayList();
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
                        upcomingList.add(vr);
                        logger.info("Adding [" + vr + "] to upcoming list");
                    }
                }
                if(userType.equals(SQLConstants.Doctor)) {
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PAST_VISIT_RECORD_FOR_DOCTOR);
                }
                else if (userType.equals(SQLConstants.Staff)) {
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PAST_VISIT_RECORD_FOR_STAFF);
                }
                else if (userType.equals(SQLConstants.Patient)) {
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PAST_VISIT_RECORD_FOR_PATIENT);
                } 
                i=0;
                cs.setString(++i, userName);
                rs = cs.executeQuery();
                if (rs != null) {
                    pastList = new ArrayList();
                    while (rs.next()) {
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
                        pastList.add(vr);
                        logger.info("Adding [" + vr + "] to past list");
                    }
                }
            } 
        } catch (SQLException e) {
            logger.error(e.toString());
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
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
            boolean wrote = false;
            upcomingSb = new StringBuilder();
            if (upcomingList != null) {
                if (!upcomingList.isEmpty()) {
                    upcomingSb.append("<table class='table table-hover'>");
                    upcomingSb.append("<thead>");
                    upcomingSb.append("<tr>");
                    upcomingSb.append("<th>Patient ID</th>");
                    upcomingSb.append("<th>CPSO Number</th>");
                    upcomingSb.append("<th>Start Time</th>");
                    upcomingSb.append("<th>End Time</th>");
                    upcomingSb.append("<th>Surgery</th>");
                    upcomingSb.append("<th>Prescription</th>");
                    upcomingSb.append("<th>Diagnosis</th>");
                    if (userType.equals(SQLConstants.Doctor) || userType.equals(SQLConstants.Staff)) {
                        upcomingSb.append("<th>Comments</th>");
                    }
                    upcomingSb.append("</tr>");
                    upcomingSb.append("</thead>");
                    upcomingSb.append("<tbody>");
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm");
                    for (VisitRecord vr : upcomingList) {
                        String startTime = sdf.format(vr.getStartTime());
                        String endTime = sdf.format(vr.getEndTime());
                        upcomingSb.append("<tr>");
                        upcomingSb.append("<td>").append(vr.getPatientID()).append("</td>");
                        upcomingSb.append("<td>").append(vr.getCPSONumber()).append("</td>");
                        upcomingSb.append("<td>").append(startTime).append("</td>");
                        upcomingSb.append("<td>").append(endTime).append("</td>");
                        upcomingSb.append("<td>").append(vr.getSurgeryName()).append("</td>");
                        upcomingSb.append("<td>").append(vr.getPrescription()).append("</td>");
                        upcomingSb.append("<td>").append(vr.getDiagnosis()).append("</td>");
                        if (userType.equals(SQLConstants.Doctor) || userType.equals(SQLConstants.Staff)) {
                            upcomingSb.append("<td>").append(vr.getComments()).append("</td>");
                        }
                        upcomingSb.append("</tr>");
                    }
                    upcomingSb.append("</tbody>");
                    upcomingSb.append("</table>");
                    wrote = true;
                } 
            }
            if (!wrote) {
                upcomingSb.append("<p>There are no upcoming appointments.</p>");
            }
            wrote = false;
            pastSb = new StringBuilder();
            if (pastList != null) 
            {
                if (!pastList.isEmpty()) {
                    pastSb.append("<table class='table table-hover'>");
                    pastSb.append("<thead>");
                    pastSb.append("<tr>");
                    pastSb.append("<th>Patient ID</th>");
                    pastSb.append("<th>CPSO Number</th>");
                    pastSb.append("<th>Start Time</th>");
                    pastSb.append("<th>End Time</th>");
                    pastSb.append("<th>Surgery</th>");
                    pastSb.append("<th>Prescription</th>");
                    pastSb.append("<th>Diagnosis</th>");
                    if (userType.equals(SQLConstants.Doctor) || userType.equals(SQLConstants.Staff)) {
                        pastSb.append("<th>Comments</th>");
                    }
                    if (userType.equals(SQLConstants.Doctor)) {
                        pastSb.append("<th>Edit</th>");
                    }
                    pastSb.append("</tr>");
                    pastSb.append("</thead>");
                    pastSb.append("<tbody>");
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm");
                    for (VisitRecord vr : pastList) {
                        String startTime = sdf.format(vr.getStartTime());
                        String endTime = sdf.format(vr.getEndTime());
                        pastSb.append("<tr>");
                        pastSb.append("<td>").append(vr.getPatientID()).append("</td>");
                        pastSb.append("<td>").append(vr.getCPSONumber()).append("</td>");
                        pastSb.append("<td>").append(startTime).append("</td>");
                        pastSb.append("<td>").append(endTime).append("</td>");
                        pastSb.append("<td>").append(vr.getSurgeryName()).append("</td>");
                        pastSb.append("<td>").append(vr.getPrescription()).append("</td>");
                        pastSb.append("<td>").append(vr.getDiagnosis()).append("</td>");
                        if (userType.equals(SQLConstants.Doctor) || userType.equals(SQLConstants.Staff)) {
                            pastSb.append("<td>").append(vr.getComments()).append("</td>");
                        }
                        if (userType.equals(SQLConstants.Doctor)) {
                            pastSb.append("<td>").append("<a href='javascript:openVisitModal(&#39;").append(vr.getCPSONumber()).append("&#39;, &#39;").append(vr.getStartTime()).append("&#39;);' class='btn btn-primary'>Edit</a>").append("</td>");
                        }
                        pastSb.append("</tr>");
                    }
                    pastSb.append("</tbody>");
                    pastSb.append("</table>");
                    wrote = true;
                }
            } 
            if (!wrote) {
                pastSb.append("<p>There are no past appointments.</p>");
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
            
            out.println(" { \"upcoming\":\"" + upcomingSb.toString() + "\", \"past\":\"" + pastSb.toString() + "\"} ");
            out.close();
        }
    }
}
