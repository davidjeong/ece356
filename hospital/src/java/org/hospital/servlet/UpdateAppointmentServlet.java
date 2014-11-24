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
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "UpdateAppointmentServlet", urlPatterns = {"/UpdateAppointmentServlet"})
public class UpdateAppointmentServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(UpdateAppointmentServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        int res = 0;
        List<VisitRecord> visitList = null;
        boolean success = false;
        boolean conflicted = false;
        
        String cpsoNumber = request.getParameter("cpso_number");
        String s = request.getParameter("start_time");
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_UPCOMING_DOCTOR_WEEKLY_RECORDS);
            int i = 0;
            cs.setString(++i, cpsoNumber);
            rs = cs.executeQuery();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm");
            long old_start_time = sdf.parse(request.getParameter("start_time")).getTime();
            long start_time = sdf.parse(request.getParameter("new_start")).getTime();
            long end_time = sdf.parse(request.getParameter("new_end")).getTime();
            Timestamp time1 = new Timestamp(start_time);
            Timestamp time2 = new Timestamp(end_time);

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

                    if ((time1.equals(vr.getStartTime())) || time2.equals(vr.getEndTime())) {
                        conflicted = true;
                        break;
                    }

                    if ((time1.before(vr.getEndTime()) && time1.after(vr.getStartTime())) || (time2.before(vr.getEndTime()) && time2.after(vr.getStartTime()))) {
                        conflicted = true;
                        break;
                    }
                }
            }
            if (!conflicted) {
                //update
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } catch (ParseException e) {
            logger.error(e.toString());
        }
    }
}
