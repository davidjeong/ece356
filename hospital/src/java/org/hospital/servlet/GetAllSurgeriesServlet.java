/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author l22fu
 */
@WebServlet(name = "GetAllSurgeriesServlet", urlPatterns = {"/GetAllSurgeriesServlet"})
public class GetAllSurgeriesServlet extends HttpServlet {
Logger logger = LoggerFactory.getLogger(GetAllSurgeriesServlet.class);
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<String> surgeries = null;
        StringBuilder sb = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            surgeries = new ArrayList();
                cs = SQLConstants.CONN.prepareCall(SQLConstants.GET_ALL_SURGERIES);
                rs = cs.executeQuery();
                if (rs != null) {
                    while(rs.next()) {
                        surgeries.add(rs.getString("surgery_name"));
                    }
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
            boolean wrote = false;
            sb = new StringBuilder();
            if (surgeries != null && !surgeries.isEmpty()) {
                
                sb.append(" <select class='form-control' id='revenueStream' name='revenueStream'> ");
                sb.append("<option>All</option>");
                for (String s : surgeries) {
                    sb.append("<option>");
                    sb.append(s);
                    sb.append("</option>");
                }
                sb.append("</select>");
                wrote = true;
            }
            if (!wrote) {
                sb.append("<p>There are no Surgeries.</p>");
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
            
            out.println(" { \"surgeries\":\"" + sb.toString() + "\"} ");
            out.close();
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
