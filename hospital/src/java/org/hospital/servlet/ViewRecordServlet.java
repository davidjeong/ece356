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
import org.hospital.entities.VisitRecord;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ViewRecordServlet", urlPatterns = {"/ViewRecordServlet"})
public class ViewRecordServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                CallableStatement cs = null;
        ResultSet rs = null;
        
        List<VisitRecord> visitList = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            
            String userType = request.getSession().getAttribute("usertype").toString();
            if(userType.equals("doctor") || 
               userType.equals("staff") ||
               userType.equals("patient") ) {

                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_VISIT_RECORDS_FOR_USER);
                int i=0;
                cs.setString(++i, request.getSession().getAttribute("username").toString());
            } else {
                //legal and finance  
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_VISIT_RECORDS);
            }
            rs = cs.executeQuery();
            
            if (rs != null)
            { 
                visitList = new ArrayList();
                while (rs.next())
                {   
                    VisitRecord vr = new VisitRecord( rs.getInt("patient_id"),
                                                      rs.getInt("cpso_number"),
                                                      rs.getDate("start_time"),
                                                      rs.getDate("end_time"),
                                                      rs.getString("surgery_name"),
                                                      rs.getString("prescription"),
                                                      rs.getString("comments"),
                                                      rs.getString("diagnosis"));
                    visitList.add(vr);
                    logger.info("Adding [" + vr + "] to visit list");
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
            if (visitList != null) 
            {
                request.getSession().setAttribute("VisitList", visitList);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
