package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "DeleteAppointmentServlet", urlPatterns = {"/DeleteAppointmentServlet"})
public class DeleteAppointmentServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(DeleteAppointmentServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        CallableStatement cs = null;
        StringBuilder output = null;
        
        String cpso = request.getParameter("cpso_number");
        String start_time = request.getParameter("start_time");
        int res = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsed = sdf.parse(start_time);
            Timestamp ts = new Timestamp(parsed.getTime());
            
            cs = SQLConstants.CONN.prepareCall(SQLConstants.DELETE_VISIT_RECORD);
            int i = 0;
            cs.setString(++i, cpso);
            cs.setTimestamp(++i, ts);
            res = cs.executeUpdate();
        }
        catch (SQLException e) {
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
            output.append(" { \"count\":\"").append(res).append("\" } ");
            out.println(output.toString());
            out.close();
        }
    }
}
