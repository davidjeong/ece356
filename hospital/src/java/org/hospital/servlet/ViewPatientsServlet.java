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
import org.hospital.entities.Patient;
import org.hospital.other.SQLConstants;
import org.hospital.other.MySQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ViewPatientsServlet", urlPatterns = {"/ViewPatientsServlet"})
public class ViewPatientsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Patient> patientList = null;
        StringBuilder output = null;
        boolean success = false;
        String userType = request.getSession().getAttribute("usertype").toString();
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {           
            output = new StringBuilder();
            if (userType.equals(SQLConstants.Doctor))
            {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ASSIGNED_DOCTOR);
                String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();

                if (!cpsoNumber.isEmpty() && cpsoNumber != null) {
                    int i=0;
                    cs.setString(++i, cpsoNumber);
                    logger.info("Adding [" + request.getSession().getAttribute("cpsonumber").toString() + "] to patient list");
                    rs = cs.executeQuery();
                    if (rs != null)
                    { 
                        patientList = new ArrayList();
                        while (rs.next())
                        {
                            Patient p = new Patient();
                            p.setPatientId(rs.getInt("patient_id"));
                            p.setLegalName(rs.getString("patient_legal_name"));
                            p.setDefaultDoctor(rs.getString("doctor_legal_name"));
                            p.setHealthStatus(rs.getString("health_status"));
                            patientList.add(p);
                            logger.info("Adding [" + p + "] to patient list");
                        }
                        success = true;
                    }
                }            
            }
            else if (userType.equals(SQLConstants.Finance))
            {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_PATIENT);
                rs = cs.executeQuery();
                if (rs != null)
                { 
                    patientList = new ArrayList();
                    while (rs.next())
                    {
                        Patient p = new Patient();
                        p.setPatientId(rs.getInt("patient_id"));
                        p.setLegalName(rs.getString("patient_legal_name"));
                        p.setDefaultDoctor(rs.getString("doctor_legal_name"));
                        p.setHealthStatus(rs.getString("health_status"));
                        patientList.add(p);
                        logger.info("Adding [" + p + "] to patient list");
                    }
                    success = true;
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
                output.append("<table class=table table-hover>");
                output.append("<thead>");
                output.append("<tr>");
                output.append("<th>Patient ID</th>");
                output.append("<th>Legal Name</th>");
                if (userType.equals(SQLConstants.Doctor)) {
                    output.append("<th>Default Doctor</th>");
                    output.append("<th>Health Status</th>");
                }
                output.append("</tr>");
                output.append("</thead>");
                if (patientList.size() > 0) {
                    output.append("<tbody>");
                    for (Patient p : patientList) {
                        output.append("<tr>");
                        output.append("<td>").append(p.getPatientId()).append("</td>");
                        output.append("<td>").append(p.getLegalName()).append("</td>");
                        if (userType.equals(SQLConstants.Doctor)) {
                            output.append("<td>").append(p.getDefaultDoctor()).append("</td>");
                            output.append("<td>").append(p.getHealthStatus()).append("</td>");
                        }
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