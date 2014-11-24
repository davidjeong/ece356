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

@WebServlet(name = "ViewDoctorSchedulesServlet", urlPatterns = {"/ViewDoctorSchedulesServlet"})
public class ViewDoctorSchedulesServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewDoctorSchedulesServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        List<VisitRecord> visitList = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
           
            String username = request.getParameter("username");
            if (username != null && !username.isEmpty()) {
                int i = 0;
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_DOCTOR_WEEKLY_RECORDS);
                rs = cs.executeQuery();
                
                if (rs != null) {
                    visitList = new ArrayList<VisitRecord>();
                    while (rs.next()) {
                        VisitRecord vr = new VisitRecord(rs.getInt("patient_id"),
                        rs.getString("cpso_number"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"));
                        visitList.add(vr);
                    }
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.toString());
        }
        catch (Exception e) {
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
            if (visitList != null) {
                if (!visitList.isEmpty()) {
                    output.append("{ \"events_source\": [ ");
                    String delim = "";
                    for (int i=0; i<visitList.size(); i++) {
                        VisitRecord vr = visitList.get(i);
                        output.append(delim).append(" { ");
                        output.append("\"id\":\"").append(String.valueOf(i)).append("\",");
                        output.append("\"title\":\"Patient ID ").append(String.valueOf(vr.getPatientID())).append(" - ").append(vr.getCPSONumber()).append("\",");
                        output.append("\"url\":\"#\",");
                        output.append("\"class\":\"event-info\",");
                        long startMillis = vr.getStartTime().getTime();
                        long endMillis = vr.getEndTime().getTime();
                        output.append("\"start\":\"").append(String.valueOf(startMillis)).append("\",");
                        output.append("\"end\":\"").append(String.valueOf(endMillis)).append("\"");
                        output.append(" } ");
                        delim = ",";
                    }
                    output.append(" ] }");
                } else {
                    output.append(" { \"events_source\": [] } ");
                }
            }
            logger.info(output.toString());
            out.write(output.toString());
            out.close();
        }
    }
}
