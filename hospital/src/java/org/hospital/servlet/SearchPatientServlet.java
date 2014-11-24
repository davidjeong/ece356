/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.entities.Patient;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author l22fu
 */
@WebServlet(name = "SearchPatientServlet", urlPatterns = {"/SearchPatientServlet"})
public class SearchPatientServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(SearchPatientServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> patientNames = null;
        StringBuilder output = new StringBuilder();
        boolean success = false;
        String date = null;
        String pID = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            String sql = "select distinct(u.legal_name) as legal_name from user_schema u ";
            sql += " INNER JOIN patient_schema p ON u.user_name = p.user_name ";
            
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            date = request.getParameter("last_visit_date");
            String name = request.getParameter("legal_name");
            pID = request.getParameter("patient_id");
            
            if ( !date.equals("")) {
                long start_time = sdf.parse(date).getTime();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(start_time);
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59);

                Timestamp time1 = new Timestamp(start_time);
                Timestamp time2 = new Timestamp(c.getTime().getTime());
                
                sql += " INNER JOIN (SELECT ";
                sql += "patient_id FROM (SELECT patient_id,  MAX(start_time) AS start_time, MAX(end_time)";
                sql += " AS end_time FROM visit_schema WHERE start_time <= CURTIME() GROUP BY ";
                sql += "patient_id) v2 WHERE '"+time1.toString() + "' <= start_time AND end_time <='";
                sql +=  time2.toString() + "')  v WHERE p.patient_id = v.patient_id";
                if (!name.equals("")) 
                    sql += " AND u.legal_name LIKE '%" + name + "%'";
                if (!pID.equals(""))
                    sql += " AND p.patient_id='"+pID+"'";
                sql += ";";
                success = true;
                
            } else if (!pID.equals("")) {
                sql += "WHERE p.patient_id='"+ pID +"'";
                
                if (!name.equals(""))
                    sql += " AND u.legal_name LIKE '%"+name+"%'";
                sql += ";";
                success = true;
            } else if (!name.equals("")) {
                sql += " WHERE u.legal_name LIKE '%"+name+"%'";
                success = true;
            }
            
            if (success) {
                ps = SQLConstants.CONN.prepareStatement(sql);
                rs = ps.executeQuery();
                patientNames = new ArrayList();
                if (rs != null) {
                    while (rs.next()) {
                        String ln = rs.getString("legal_name");
                        patientNames.add(ln);
                    }
                }
            }
            
        } catch (SQLException e)
        {
            logger.error(e.toString());
        } catch (ParseException e)    
        {
            
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
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
                
                if (patientNames != null) {
                    if (!pID.equals("")) {
                        output.append("<table class='table table-hover'>");
                        output.append("<thead>");
                        output.append("<tr>");
                        output.append("<th>Patient ID</th>");
                        output.append("</tr>");
                        output.append("</thead>");
                        
                        output.append("<tbody>");
                        output.append("<tr>");
                        output.append("<td>").append(pID).append("</td>");
                        output.append("</tr>");
                        
                    } 
                    if (!date.equals("")) {
                        output.append("<table class='table table-hover'>");
                        output.append("<thead>");
                        output.append("<tr>");
                        output.append("<th>Last Visit Date</th>");
                        output.append("</tr>");
                        output.append("</thead>");
                        
                        output.append("<tr>");
                        output.append("<td>").append(date).append("</td>");
                        output.append("</tr>");
                    }
                    
                    output.append("<table class='table table-hover'>");
                    output.append("<thead>");
                    output.append("<tr>");
                    output.append("<th>Patient Name</th>");
                    output.append("</tr>");
                    output.append("</thead>");
                    if (patientNames.size() > 0) {
                        output.append("<tbody>");
                        
                        for(String s:patientNames) {
                            output.append("<tr>");
                            output.append("<td>").append(s).append("</td>");
                            output.append("</tr>");
                        }
                        output.append("</tbody>");
                    }
                    output.append("</table>");
                } else {
                    output.append("<p>There are no patients that matched your search parameters.</p>");
                }
                out.println(" { \"success\":\"" + success + "\", \"output\":\"" + output.toString() + "\"} ");
            } else {
                out.println("{ \"success\":\"" + success + "\", \"output\":\"Input Parameters are missing or incorrect.\" }");
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
