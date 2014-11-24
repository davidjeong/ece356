package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@WebServlet(name = "ViewPatientVisitRecordsServlet", urlPatterns = {"/ViewPatientVisitRecordsServlet"})
public class ViewPatientVisitRecordsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientVisitRecordsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        List<VisitRecord> visitList = null;
        boolean success = false;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            String userType = request.getSession().getAttribute("usertype").toString();
            
            if(userType.equals(SQLConstants.Doctor) || userType.equals(SQLConstants.Staff) ) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_VISIT_RECORDS);
                String patientId = request.getParameter("patient_id");
                if (!patientId.isEmpty()) {
                    int i=0;
                    cs.setString(++i, patientId);
                    rs = cs.executeQuery();
                    
                    if (rs != null) { 
                        visitList = new ArrayList();
                        VisitRecord vr = null;
                        while (rs.next())
                        {   
                        String surgery_name = (rs.getString("surgery_name") == null ? "N/A" : rs.getString("surgery_name"));
                        String prescription = (rs.getString("prescription") == null ? "N/A" : rs.getString("prescription"));
                        String comments = (rs.getString("comments") == null ? "N/A" : rs.getString("comments"));
                        String diagnosis = (rs.getString("diagnosis") == null ? "N/A" : rs.getString("diagnosis"));
                        
                        vr = new VisitRecord( rs.getInt("patient_id"),
                                                          rs.getString("cpso_number"),
                                                          rs.getTimestamp("start_time"),
                                                          rs.getTimestamp("end_time"),
                                                          surgery_name,
                                                          prescription,
                                                          comments,
                                                          diagnosis);
                            
                            visitList.add(vr);
                            logger.info("Adding [" + vr + "] to visit list");
                        }
                        success = true;
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
            if (visitList != null) {
                output.append("<table class='table table-hover'>");
                
                if (visitList.size() > 0) {
                    output.append("<thead>");
                    output.append("<tr>");
                    output.append("<th>Patient ID</th>");
                    output.append("<th>CPSO Number</th>");
                    output.append("<th>Start Time</th>");
                    output.append("<th>End Time</th>");
                    output.append("<th>Prescription</th>");
                    output.append("<th>Surgery Name</th>");
                    output.append("<th>Diagnosis</th>");
                    output.append("<th>Comments</th>");
                    output.append("</tr>");
                    output.append("</thead>");
                    
                    output.append("<tbody>");
                    for (VisitRecord vr : visitList) {
                        output.append("<tr>");
                        output.append("<td>").append(vr.getPatientID()).append("</td>");
                        output.append("<td>").append(vr.getCPSONumber()).append("</td>");
                        output.append("<td>").append(vr.getStartTime().toString()).append("</td>");
                        output.append("<td>").append(vr.getEndTime().toString()).append("</td>");
                        output.append("<td>").append(vr.getPrescription()).append("</td>");
                        output.append("<td>").append(vr.getSurgeryName()).append("</td>");
                        output.append("<td>").append(vr.getDiagnosis()).append("</td>");
                        output.append("<td>").append(vr.getComments()).append("</td>");
                        output.append("</tr>");
                    }
                    output.append("</tbody>");
                } else {
                    output.append("<thead>");
                    output.append("<tr>");
                    output.append("<td> There are no visitation records for this patient. </td> ");
                    output.append("</tr>");
                    output.append("</thead>");
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
