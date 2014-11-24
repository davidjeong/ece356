/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hospital.entities.PatientRecord;
import org.hospital.entities.VisitRecord;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.hospital.servlet.ViewExistingVisitInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Leo Zhao
 */
@WebServlet(urlPatterns = {"/ViewExistingPatientInformation"})
public class ViewExistingPatientInformation extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(ViewExistingVisitInformation.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        List<PatientRecord> patientList = null;
        boolean success = false;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_INFORMATION);
            int patientId = Integer.parseInt(request.getParameter("patient_id"));
            int i=0;
            cs.setInt(++i, patientId);
            rs = cs.executeQuery();

            if (rs != null) { 
                PatientRecord vr = null;
                patientList = new ArrayList();
                while (rs.next())
                {   
                    String health_card_number = (rs.getString("health_card_number") == null ? "" : rs.getString("health_card_number"));
                    String sin_number = (rs.getString("sin_number") == null ? "" : rs.getString("sin_number"));
                    String phone_number = (rs.getString("phone_number") == null ? "" : rs.getString("phone_number"));
                    String address = (rs.getString("address") == null ? "" : rs.getString("address"));

                    vr = new PatientRecord( rs.getInt("patient_id"),
                                                      health_card_number,
                                                      sin_number,
                                                      phone_number,
                                                      address);
                    patientList.add(vr);
                    logger.info("Adding [" + vr + "] to visit list");
                }
                success = true;
            }

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
            if (patientList != null) {
                if (patientList.size() == 1) {
                    PatientRecord vr = patientList.get(0);
                    output.append(" { \"success\": \"").append(success).append("\", \"output\": ");
                    output.append(" { \"Health_Card_Number\":\"").append(vr.getHealthCardNumber()).append("\",");
                    output.append(" \"Sin_Number\":\"").append(vr.getSinNumber()).append("\",");
                    output.append(" \"Phone_Number\":\"").append(vr.getPhoneNumber()).append("\",");
                    output.append(" \"Address\":\"").append(vr.getAddress()).append("\" } } ");
                }
            } else {
                 output.append(" { \"success\": \"").append(success).append("\" } ");
            }
            out.println(output.toString());
            out.close();
        }
    }
}
