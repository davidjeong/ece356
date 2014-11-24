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
import org.hospital.entities.Doctor;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "GetAllDoctorsServlet", urlPatterns = {"/GetAllDoctorsServlet"})
public class GetAllDoctorsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(GetAllDoctorsServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder output = null;
        
        List<Doctor> doctorList = null;
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.All_DOCTOR_INFORMATION);
            rs = cs.executeQuery();
            
            if (rs != null) {
                doctorList = new ArrayList<Doctor>();
                while (rs.next()) {
                    Doctor d = new Doctor();
                    d.setLegalName(rs.getString("legal_name"));
                    d.setCpsoNumber(rs.getString("cpso_number"));
                    doctorList.add(d);
                }
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
            
            
            
            if (doctorList != null) {
                if (!doctorList.isEmpty()) {
                    output = new StringBuilder();
                    output.append(" { \"success\":\"true\", \"output\": [ ");
                    String delim = "";
                    for (Doctor d : doctorList) {
                        output.append(delim).append(" { \"cpso_number\":\"").append(d.getCpsoNumber()).append("\", \"legal_name\":\"").append(d.getLegalName()).append("\" } ");
                        delim = ",";
                    }    
                    output.append(" ] } ");
                }
            }
            if (output == null) {
                output = new StringBuilder();
                output.append(" \"success\":\"false\", \"output\":\"There are no doctors\"");
            }
            out.println(output.toString());
            out.close();
        }
    }
}
