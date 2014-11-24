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
import java.util.Date;
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

@WebServlet(name = "ViewExistingVisitInformation", urlPatterns = {"/ViewExistingVisitInformation"})
public class ViewExistingVisitInformation extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewExistingVisitInformation.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        List<VisitRecord> visitList = null;
        boolean success = false;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_VISIT_RECORDS_FOR_START_TIME);
            String cpsoNumber = request.getParameter("cpso_number");
            String start_time = request.getParameter("start_time");
            if (!start_time.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date parsed = sdf.parse(start_time);
                Timestamp ts = new Timestamp(parsed.getTime());
                
                int i=0;
                cs.setString(++i, cpsoNumber);
                cs.setTimestamp(++i, ts);
                rs = cs.executeQuery();

                if (rs != null) { 
                    VisitRecord vr = null;
                    visitList = new ArrayList();
                    while (rs.next())
                    {   
                        String surgery_name = (rs.getString("surgery_name") == null ? "N/A" : rs.getString("surgery_name"));
                        String prescription = (rs.getString("prescription") == null ? "" : rs.getString("prescription"));
                        String comments = (rs.getString("comments") == null ? "" : rs.getString("comments"));
                        String diagnosis = (rs.getString("diagnosis") == null ? "" : rs.getString("diagnosis"));
                        
                        vr = new VisitRecord( rs.getInt("patient_id"),
                                                          rs.getString("cpso_number"),
                                                          rs.getTimestamp("start_time"),
                                                          rs.getTimestamp("end_time"),
                                                          surgery_name,
                                                          prescription,
                                                          comments,
                                                          diagnosis);
                        visitList.add(vr);
                        logger.info("Adding [" + vr + "] to visit list");
                    }
                    success = true;
                }
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } catch (ParseException e) {
            logger.error(e.toString());
        }catch (Exception e) {
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
                if (visitList.size() == 1) {
                    VisitRecord vr = visitList.get(0);
                    output.append(" { \"success\": \"").append(success).append("\", \"output\": ");
                    output.append(" { \"surgery_name\":\"").append(vr.getSurgeryName()).append("\",");
                    output.append(" \"prescription\":\"").append(vr.getPrescription()).append("\",");
                    output.append(" \"diagnosis\":\"").append(vr.getDiagnosis()).append("\",");
                    output.append(" \"comments\":\"").append(vr.getComments()).append("\" } } ");
                }
            } else {
                 output.append(" { \"success\": \"").append(success).append("\" } ");
            }
            out.println(output.toString());
            out.close();
        }
    }
}
