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

/**
 *
 * @author Leo Zhao
 */
@WebServlet(name = "EditPatientVisitRecordsServlet", urlPatterns = {"/EditPatientVisitRecordsServlet"})
public class EditPatientVisitRecordsServlet extends HttpServlet {

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
                        while (rs.next())
                        {   
                            VisitRecord vr = new VisitRecord( rs.getInt("patient_id"),
                                                              rs.getString("cpso_number"),
                                                              rs.getTimestamp("start_time"),
                                                              rs.getTimestamp("end_time"),
                                                              rs.getString("surgery_name"),
                                                              rs.getString("prescription"),
                                                              rs.getString("comments"),
                                                              rs.getString("diagnosis"));
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
                    output.append("<th>Surgery Name</th>");
                    output.append("<th>Prescription</th>");
                    output.append("<th>Diagnosis</th>");
                    output.append("<th>Comments</th>");
                    output.append("</tr>");
                    output.append("</thead>");
                    
                    output.append("<tbody>");
                    for (VisitRecord vr : visitList) {
                        output.append("<tr>");
                        output.append("<td>").append(vr.getSurgeryName()).append("</td>");
                        output.append("<form name='input' id='ajaxEditRecords' class='form-control' role='form' method='POST'>");
                        output.append("<div class='form-group'><td>").append("<input type = 'text' class='form-control' name = 'Prescription' value ='").append(vr.getPrescription()).append("'>").append("</td></div>");
                        output.append("<div class='form-group'><td>").append("<input type = 'text' class='form-control' name = 'Diagnosis' value ='").append(vr.getDiagnosis()).append("'>").append("</td></div>");
                        output.append("<div class='form-group'><td>").append("<input type = 'text' class='form-control' name = 'Comments' value ='").append(vr.getComments()).append("'>").append("</td></div>");
                        output.append("</form>");
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
