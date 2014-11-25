package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "UpdatePersonalInformationServlet", urlPatterns = {"/UpdatePersonalInformationServlet"})
public class UpdatePersonalInformationServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(UpdatePersonalInformationServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        StringBuilder output = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        String patientId = request.getSession().getAttribute("patientid").toString();
        String phoneNumber = request.getParameter("phone_number");
        String address = request.getParameter("address");
        
        int res = 0;
        
        try {
            if (!patientId.isEmpty()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.UPDATE_PERSONAL_RECORD);
                int i = 0;
                cs.setInt(++i, Integer.parseInt(patientId));
                cs.setString(++i, phoneNumber);
                cs.setString(++i, address);
                
                res = cs.executeUpdate();
            }
        }
        catch (SQLException e) {
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
