package org.hospital.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "EditPatientVisitRecordsServlet", urlPatterns = {"/EditPatientVisitRecordsServlet"})
public class EditPatientVisitRecordsServlet extends HttpServlet {

   Logger logger = LoggerFactory.getLogger(EditPatientVisitRecordsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        StringBuilder output = null;
        
        String prescription = request.getParameter("prescription");
        String diagnosis = request.getParameter("diagnosis");
        String comments = request.getParameter("comments");
        String cpso = request.getParameter("cpso_number");
        
        int patient_id = Integer.parseInt(request.getParameter("patient_id"));
        int res = 0;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.UPDATE_VISIT_RECORD);
            int i = 0;
            cs.setInt(++i, patient_id);
            cs.setString(++i, cpso);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            long start_time = sdf.parse(request.getParameter("start_time")).getTime();
            Timestamp ts = new Timestamp(start_time);
            cs.setTimestamp(++i, ts);
            cs.setString(++i, prescription);
            cs.setString(++i, comments);
            cs.setString(++i, diagnosis);
            
            res = cs.executeUpdate();
            
        } catch (SQLException e) {
            logger.error(e.toString());
        } catch (ParseException e) {
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
