package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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

@WebServlet(name = "CreateNewAppointmentServlet", urlPatterns = {"/CreateNewAppointmentServlet"})
public class CreateNewAppointmentServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(CreateNewAppointmentServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<VisitRecord> visitList = null;
        StringBuilder output = null;
        boolean empty = false;
        boolean conflicted = false;
        boolean success = false;
        int res = 0;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            String cpsoNumber = request.getParameter("cpso");
            String patientId = request.getParameter("patient_id");
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
            long start_time = sdf.parse(request.getParameter("start_range")).getTime();
            long end_time = sdf.parse(request.getParameter("end_range")).getTime();
            Timestamp time1 = new Timestamp(start_time);
            Timestamp time2 = new Timestamp(end_time);
            
            if (!cpsoNumber.isEmpty() && !patientId.isEmpty()) {
                boolean valid = false;
                cs = SQLConstants.CONN.prepareCall(SQLConstants.CHECK_IF_DOCTOR_IS_ASSIGNED_TO_PATIENT);
                int i = 0;
                cs.setString(++i, patientId);
                cs.setString(++i, cpsoNumber);
                rs = cs.executeQuery();
                if (rs != null) {
                    if (rs.next()) {
                        valid = true;
                    }
                }
                if (valid) {
                    cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_UPCOMING_DOCTOR_WEEKLY_RECORDS);
                    i = 0;
                    cs.setString(++i, cpsoNumber);
                    rs = cs.executeQuery();

                    if (rs != null) {
                        visitList = new ArrayList<VisitRecord>();
                        while (rs.next()) {
                            VisitRecord vr = new VisitRecord(rs.getInt("patient_id"),
                            rs.getString("cpso_number"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"));
                            visitList.add(vr);
                        }
                    }

                    //here is where you check if your time is conflicting with the time we have in db.
                    if (visitList != null && !visitList.isEmpty()) {
                        for (VisitRecord vr : visitList) {

                            if ((time1.before(vr.getEndTime()) && time1.after(vr.getStartTime())) || (time2.before(vr.getEndTime()) && time2.after(vr.getStartTime()))) {
                                conflicted = true;
                                break;
                            }
                        }
                    }
                    if (!conflicted) {
                        //insert
                        cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_VISIT_RECORD);
                        i = 0;
                        cs.setString(++i, patientId);
                        cs.setString(++i, cpsoNumber);
                        cs.setTimestamp(++i, time1);
                        cs.setTimestamp(++i, time2);
                        res = cs.executeUpdate();
                        if (res > 0) {
                            success = true;
                        }
                    }
                }
            } else {
                empty = true;
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } catch (ParseException e) {
            logger.error(e.toString());
            empty = true;
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
            
            output.append(" { \"success\": \"").append(success).append("\", \"conflicted\":\"").append(conflicted).append("\", \"empty\":\"").append(empty).append("\", \"inserted\":\"").append(res).append("\" } ");
            logger.info(output.toString());
            out.println(output.toString());
            out.close();
            
        }
    }
}
