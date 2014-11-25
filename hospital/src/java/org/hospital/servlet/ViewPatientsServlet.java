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
        List<Patient> myList = null;
        List<Patient> otherList = null;
        StringBuilder mySb = null;
        StringBuilder otherSb = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();
            if (cpsoNumber != null && !cpsoNumber.isEmpty()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_MY_PATIENTS);
                //int i=0;
                cs.setString(1, cpsoNumber);
                rs = cs.executeQuery();
                if (rs != null)
                { 
                    myList = new ArrayList();
                    while (rs.next())
                    {
                        Patient p = new Patient();
                        
                        String healthStatus = (request.getParameter("health_status") == null || request.getParameter("health_status").isEmpty()) ? "N/A" : request.getParameter("health_status");
                        
                        p.setPatientId(rs.getInt("patient_id"));
                        p.setLegalName(rs.getString("patient_legal_name"));
                        p.setDefaultDoctor(rs.getString("doctor_legal_name"));
                        p.setHealthStatus(healthStatus);
                        myList.add(p);
                        logger.info("Adding [" + p + "] to patient list");
                    }
                }
                
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_OTHER_PATIENTS);
                //int i=0;
                cs.setString(1, cpsoNumber);
                rs = cs.executeQuery();
                if (rs != null)
                { 
                    otherList = new ArrayList();
                    while (rs.next())
                    {
                        Patient p = new Patient();
                        
                        String healthStatus = (request.getParameter("health_status") == null || request.getParameter("health_status").isEmpty()) ? "N/A" : request.getParameter("health_status");
                        
                        p.setPatientId(rs.getInt("patient_id"));
                        p.setLegalName(rs.getString("patient_legal_name"));
                        p.setDefaultDoctor(rs.getString("doctor_legal_name"));
                        p.setHealthStatus(healthStatus);
                        otherList.add(p);
                        logger.info("Adding [" + p + "] to patient list");
                    }
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
            
            boolean wrote = false;
            mySb = new StringBuilder();
            if (myList != null) {
                if (!myList.isEmpty()) {
                    mySb.append("<table class='table table-hover'>");
                    mySb.append("<thead>");
                    mySb.append("<tr>");
                    mySb.append("<th>Patient ID</th>");
                    mySb.append("<th>Legal Name</th>");
                    mySb.append("<th>Default Doctor</th>");
                    mySb.append("<th>Health Status</th>");
                    mySb.append("</tr>");
                    mySb.append("</thead>");
                    mySb.append("<tbody>");
                    for (Patient p : myList) {
                        mySb.append("<tr>");
                        mySb.append("<td><a href='javascript:openModal(").append(p.getPatientId()).append(");'>").append(p.getPatientId()).append("</a></td>");
                        mySb.append("<td>").append(p.getLegalName()).append("</td>");
                        mySb.append("<td>").append(p.getDefaultDoctor()).append("</td>");
                        mySb.append("<td>").append(p.getHealthStatus()).append("</td>");
                        mySb.append("</tr>");
                    }
                    mySb.append("</table>");
                    wrote = true;
                }
            }
            if (!wrote) {
                mySb.append("<p>You have no default patients.</p>");
            }
            wrote = false;
            otherSb = new StringBuilder();
            if (otherList != null) {
                if (!otherList.isEmpty()) {
                    otherSb.append("<table class='table table-hover'>");
                    otherSb.append("<thead>");
                    otherSb.append("<tr>");
                    otherSb.append("<th>Patient ID</th>");
                    otherSb.append("<th>Legal Name</th>");
                    otherSb.append("<th>Default Doctor</th>");
                    otherSb.append("<th>Health Status</th>");
                    otherSb.append("</tr>");
                    otherSb.append("</thead>");
                    otherSb.append("<tbody>");
                    for (Patient p : otherList) {
                        otherSb.append("<tr>");
                        otherSb.append("<td><a href='javascript:openModal(").append(p.getPatientId()).append(");'>").append(p.getPatientId()).append("</a></td>");
                        otherSb.append("<td>").append(p.getLegalName()).append("</td>");
                        otherSb.append("<td>").append(p.getDefaultDoctor()).append("</td>");
                        otherSb.append("<td>").append(p.getHealthStatus()).append("</td>");
                        otherSb.append("</tr>");
                    }
                    otherSb.append("</tbody>");
                    otherSb.append("</table>");
                    wrote = true;
                }
            }
            if (!wrote) {
                otherSb.append("<p>You have no other patients.</p>");
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
            
            out.println(" { \"myPatients\": \"" + mySb.toString() + "\", \"otherPatients\":\"" + otherSb.toString() + "\" } ");
            out.close();
        }
    }
}