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

@WebServlet(name = "SearchVisitRecordsServlet", urlPatterns = {"/SearchVisitRecordsServlet"})
public class SearchVisitRecordsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(SearchVisitRecordsServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<VisitRecord> visits = new ArrayList<VisitRecord>();
        StringBuilder output = new StringBuilder();
        boolean success = false;
        String date = null;
        String surgery = null;
        String name = null;
        String comments = null;
        String prescription = null;
        String diagnosis = null;
        
        String userType = request.getSession().getAttribute("usertype").toString();
        String userName = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            String sql = "SELECT * FROM ";
            if (userType.equals(SQLConstants.Legal)) 
                sql += " visit_backup_schema v ";
            else { 
                //doctor
                userName = request.getSession().getAttribute("username").toString();
                sql += " visit_schema v ";
            }
            
            date = request.getParameter("visit_date");
            name = request.getParameter("legal_name");
            surgery = request.getParameter("surgeryName");
            diagnosis = request.getParameter("diagnosis");
            comments = request.getParameter("comments");
            prescription = request.getParameter("prescription");
            
            if (!name.equals("")) {

                sql += "INNER JOIN patient_schema p ON p.patient_id = v.patient_id INNER JOIN ";
                
                if (userType.equals(SQLConstants.Doctor)) {
                    sql += " (SELECT up.patient_id FROM user_patient_view_schema up INNER JOIN doctor_schema d ON " +
                        " d.user_name = up.user_name WHERE up.user_name = '"+userName+"') up " + 
                        " ON up.patient_id = v.patient_id INNER JOIN ";
                }
                
                sql += " user_schema u ON u.user_name = p.user_name WHERE u.legal_name LIKE '%"+name+"%' AND ";

            } else {
                if (userType.equals(SQLConstants.Doctor)) {
                    sql += "INNER JOIN ( SELECT up.patient_id FROM user_patient_view_schema up INNER JOIN " +
                            " doctor_schema d ON d.user_name = up.user_name WHERE up.user_name = '" +
                            userName + "') up ON up.patient_id = v.patient_id ";
                }
                sql += " WHERE ";
            }
            
            if (!date.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                long start_time = sdf.parse(date).getTime();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(start_time);
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 23, 59);

                Timestamp time1 = new Timestamp(start_time);
                Timestamp time2 = new Timestamp(c.getTime().getTime());
                
                sql += "'"+time1.toString() + "' <= start_time AND end_time <='" + time2.toString() + "' AND ";
            }
            
            if (!surgery.equals("All")) {
                sql += " v.surgery_name = '"+surgery+"' AND ";
            }
            
            if (!diagnosis.equals("")) {
                sql += " v.diagnosis LIKE '%"+diagnosis+"%' AND ";
            }
            
            if (!comments.equals("")) {
                sql += " v.comments LIKE '%"+comments+"%' AND ";
            }
            
            if (!prescription.equals("")) {
                sql += " v.prescription LIKE '%"+prescription+"%' AND ";
            }
            
            sql = sql.substring(0, sql.length() - 4);
            ps = SQLConstants.CONN.prepareStatement(sql);
            rs = ps.executeQuery();
            VisitRecord vr= null;
            if (rs != null) {
                while (rs.next()) {
                    String surgery_name = ((rs.getString("surgery_name") == null || rs.getString("surgery_name").isEmpty()) ? "N/A" : rs.getString("surgery_name"));
                    String p = ((rs.getString("prescription") == null || rs.getString("prescription").isEmpty()) ? "N/A" : rs.getString("prescription"));
                    String c = ((rs.getString("comments") == null || rs.getString("comments").isEmpty()) ? "N/A" : rs.getString("comments"));
                    String d = ((rs.getString("diagnosis") == null || rs.getString("diagnosis").isEmpty()) ? "N/A" : rs.getString("diagnosis"));

                    vr = new VisitRecord( rs.getInt("patient_id"),
                                                      rs.getString("cpso_number"),
                                                      rs.getTimestamp("start_time"),
                                                      rs.getTimestamp("end_time"),
                                                      surgery_name,
                                                      p,
                                                      c,
                                                      d);
                    visits.add(vr);
                    logger.info("Adding [" + vr + "] to upcoming list");
                    success = true;
                }
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } catch (ParseException e) {
            
        } finally {
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
                if (!visits.isEmpty()) {
                    output.append("<table class='table table-hover'>");
                    output.append("<thead>");
                    output.append("<tr>");
                    output.append("<th>Patient ID</th>");
                    output.append("<th>CPSO Number</th>");
                    output.append("<th>Start Time</th>");
                    output.append("<th>End Time</th>");
                    output.append("<th>Surgery</th>");
                    output.append("<th>Prescription</th>");
                    output.append("<th>Diagnosis</th>");
                    output.append("<th>Comments</th>");

                    output.append("</tr>");
                    output.append("</thead>");
                    output.append("<tbody>");
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm");
                    for (VisitRecord vr : visits) {
                        String startTime = sdf.format(vr.getStartTime());
                        String endTime = sdf.format(vr.getEndTime());
                        output.append("<tr>");
                        output.append("<td>").append(vr.getPatientID()).append("</td>");
                        output.append("<td>").append(vr.getCPSONumber()).append("</td>");
                        output.append("<td>").append(startTime).append("</td>");
                        output.append("<td>").append(endTime).append("</td>");
                        output.append("<td>").append(vr.getSurgeryName()).append("</td>");
                        output.append("<td>").append(vr.getPrescription()).append("</td>");
                        output.append("<td>").append(vr.getDiagnosis()).append("</td>");
                        output.append("<td>").append(vr.getComments()).append("</td>");

                        output.append("</tr>");
                    }
                    output.append("</tbody>");
                    output.append("</table>");

                } else {
                    output.append("<p>There are no visitation records for the parameters you entered.</p>");
                }
                out.println(" { \"success\":\"" + success + "\", \"output\":\"" + output.toString() + "\"} ");
            } else {
                out.println("{ \"success\":\"" + success + "\", \"output\":\"Input Parameters are incorrect.\" }");
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
