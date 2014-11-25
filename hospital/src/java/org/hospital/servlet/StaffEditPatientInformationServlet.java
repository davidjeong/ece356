/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Leo Zhao
 */
@WebServlet(name = "StaffEditPatientInformationServlet", urlPatterns = {"/StaffEditPatientInformationServlet"})
public class StaffEditPatientInformationServlet extends HttpServlet {
   Logger logger = LoggerFactory.getLogger(EditPatientVisitRecordsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        StringBuilder output = null;
        
        String Default_Doctor = request.getParameter ("Default_Doctor");
        String Health_Card_Number = request.getParameter("Health_Card_Number");
        String Health_Status = request.getParameter("Health_Status");
        String Phone_Number = request.getParameter("Phone_Number");
        String Address = request.getParameter("Address");
        
        int patient_id = Integer.parseInt(request.getParameter("Patient_Id").toString());
        int res = 0;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.UPDATE_STAFF_PATIENT_RECORD);
            int i = 0;
            cs.setInt(++i, patient_id);
            cs.setString(++i, Default_Doctor);
            cs.setString(++i, Health_Card_Number);
            cs.setString(++i, Health_Status);
            cs.setString(++i, Phone_Number);
            cs.setString(++i, Address);
            
            res = cs.executeUpdate();
            
        } catch (SQLException e) {
            logger.error(e.toString());
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
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
            
            if (res > 0) {
                output.append(" {\"success\":\"true\"} ");
            } else {
                output.append(" {\"success\":\"false\"} ");
            }
            out.println(output.toString());
            out.close();
        }
    }
}
