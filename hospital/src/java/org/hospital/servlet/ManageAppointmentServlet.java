package org.hospital.servlet;

import java.io.IOException;
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
import org.apache.log4j.Logger;
import org.hospital.entities.Doctor;
import org.hospital.other.SQLConstants;

@WebServlet(name = "ManageAppointmentServlet", urlPatterns = {"/ManageAppointmentServlet"})
public class ManageAppointmentServlet extends HttpServlet {
    
    Logger logger = Logger.getLogger(ManageAppointmentServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // This is where we get the data for each doctor. The calendar.
        StringBuilder sb = new StringBuilder();
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Doctor> doctorList = null;
        
        String userName = request.getSession().getAttribute("username").toString();

        if (userName != null && !userName.isEmpty()) {
            try {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_DOCTORS_FOR_STAFF);
                int i=0;
                cs.setString(++i, userName);
                rs = cs.executeQuery();
                
                if (rs != null) {
                    doctorList = new ArrayList<Doctor>();
                    while (rs.next()) {
                        Doctor d = new Doctor();
                        d.setLegalName(rs.getString("legal_name"));
                        d.setUserName(rs.getString("user_name"));
                        d.setCpsoNumber(rs.getString("cpso_number"));
                        d.setDepartment(rs.getString("department"));
                        doctorList.add(d);
                        logger.info("Adding [" + d + "] to dropdown list.");
                    }
                } 
            } catch (SQLException e) {
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
                if (doctorList != null) {
                    request.getSession().setAttribute("doctorList", doctorList);
                }
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
        processRequest(request, response);
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
