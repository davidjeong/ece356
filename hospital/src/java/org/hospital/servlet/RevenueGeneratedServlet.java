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
 * @author l22fu
 */
@WebServlet(name = "RevenueGeneratedServlet", urlPatterns = {"/RevenueGeneratedServlet"})
public class RevenueGeneratedServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(RevenueGeneratedServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        
        StringBuilder output = null;
        boolean success = false;
        int numberOfVisits = 0;
        StringBuilder totalOutput = null;
        StringBuilder visitsOutput = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
            long start_time = sdf.parse(request.getParameter("start_range")).getTime();
            long end_time = sdf.parse(request.getParameter("end_range")).getTime();
            Timestamp time1 = new Timestamp(start_time);
            Timestamp time2 = new Timestamp(end_time);
            String surgery_name = request.getParameter("surgeryName");
       
            cs = SQLConstants.CONN.prepareCall(SQLConstants.COUNT_ALL_VISITS);
            cs.setTimestamp(1, time1);
            cs.setTimestamp(2, time2);
            rs = cs.executeQuery();
            if(rs!=null) {
                if (rs.next())
                    numberOfVisits = rs.getInt("visits");
            }
            /*
            if (surgery_name == "All") {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.GET_ALL_SURGERIES);
                cs.setTimestamp(1, time1);
                cs.setTimestamp(2, time2);
            } else {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.GET_ALL_SURGERIES);
                cs.setString(1, surgery_name);
                cs.setTimestamp(2, time1);
                cs.setTimestamp(3, time2);
            }
            rs = cs.executeQuery();
            if( rs!=null ) {
                
                if (rs.next()) {
                    
                }
                    
            }
            */
            success = true;
            
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
//                totalOutput = new StringBuilder();
                visitsOutput = new StringBuilder();
                visitsOutput.append("<table class='table table-hover'>");
                visitsOutput.append("<thead>");
                visitsOutput.append("<tr>");
                visitsOutput.append("<th>Start Time</th>");
                visitsOutput.append("<th>End Time</th>");
                visitsOutput.append("<th>Number of Visits</th>");
                visitsOutput.append("<th>Revenue</th>");
                visitsOutput.append("</tr>");
                visitsOutput.append("</thead>");  
                
                visitsOutput.append("<tbody>");
                visitsOutput.append("<tr>");
                visitsOutput.append("<td>").append(request.getParameter("start_range")).append("</td>");
                visitsOutput.append("<td>").append(request.getParameter("end_range")).append("</td>");
                visitsOutput.append("<td>").append(numberOfVisits).append("</td>");
                visitsOutput.append("<td>").append(numberOfVisits*100).append("</td>");
                visitsOutput.append("</tr>");
                visitsOutput.append("</tbody>");
                visitsOutput.append("</table>");
                
                out.println(" { \"success\":\"" + success + "\", \"visitsOutput\":\"" + visitsOutput.toString() + "\"} ");
            } else {
                out.println("{ \"success\":\"" + success + "\", \"output\":\"Mandatory fields are empty.\" }");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
