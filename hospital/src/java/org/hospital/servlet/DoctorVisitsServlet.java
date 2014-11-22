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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/DoctorVisitsServlet"})
public class DoctorVisitsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(DoctorVisitsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallableStatement cs = null;
        ResultSet rs = null;
        boolean success = false;
        int PatientVisits = 0;
        String cpso = null;
        String datetime = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cpso = request.getParameter("cpso");
            datetime = request.getParameter("searchtime");
            if (!cpso.isEmpty() && !datetime.isEmpty()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_VISIT);
                cs.setString(1, cpso);
                cs.setString(2, datetime);
                rs = cs.executeQuery();
                while (rs.next())
                {
                    PatientVisits = rs.getInt("count(distinct v.patient_id)");
                }
                success = true;
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
        }
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
        
        StringBuilder output = new StringBuilder();
        if (success && PatientVisits > 0) {
            output.append("Number of Patients seen by Doctor: ").append(PatientVisits);
        } else {
            output.append("Invalid CPSO Number or Before Date Entered.");
        }
        out.println(" { \"success\": \"" + success + "\", \"output\": \"" + output.toString() + "\"} ");
        out.close();
    }
}
