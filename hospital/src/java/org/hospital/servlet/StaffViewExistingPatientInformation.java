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
import org.hospital.entities.Patient;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.hospital.servlet.ViewExistingVisitInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/StaffViewExistingPatientInformation"})
public class StaffViewExistingPatientInformation extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(ViewExistingVisitInformation.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        //Using an array but we can actually just use a variable because it's just one entry
        List<Patient> patientList = null;
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
                Patient vr = null;
                patientList = new ArrayList();
                while (rs.next())
                {   
                    Patient p = new Patient (
                            rs.getString("default_doctor"),
                            rs.getString("health_status"),
                            rs.getString("health_card_number"),
                            rs.getString("phone_number"),
                            rs.getString("address"),
                            rs.getInt("patient_id"));
                    patientList.add(p);
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
                    Patient vr = patientList.get(0);
                    output.append(" { \"success\": \"").append(success).append("\", \"output\": ");
                    output.append(" { \"default_doctor\":\"").append(vr.getDefaultDoctor()).append("\",");
                    output.append(" \"patient_id\":\"").append(vr.getPatientId()).append("\",");        
                    output.append(" \"health_card_number\":\"").append(vr.getHealthCardNumber()).append("\",");
                    output.append(" \"health_status\":\"").append(vr.getHealthStatus()).append("\",");
                    output.append(" \"phone_number\":\"").append(vr.getPhoneNumber()).append("\",");
                    output.append(" \"address\":\"").append(vr.getAddress()).append("\" } } ");
                }
            } else {
                 output.append(" { \"success\": \"").append(success).append("\" } ");
            }
            out.println(output.toString());
            out.close();
        }
    }
}
