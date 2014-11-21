package org.hospital.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.entities.Doctor;
import org.hospital.entities.Patient;
import org.hospital.other.SQLConstants;
import org.hospital.entities.User;
import org.hospital.other.MySQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/UserCreationServlet"})
public class UserCreationServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(UserCreationServlet.class);
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CallableStatement cs = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            SQLConstants.CONN.setAutoCommit(false);
            
            User newUser = new User();
            newUser.setLegalName(request.getParameter("legal_name"));
            newUser.setUserName(request.getParameter("username"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setUserType(request.getParameter("userType"));
            
            if (newUser.isValid()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_USER);
                int i = 0;
                cs.setString(++i, newUser.getLegalName());
                cs.setString(++i, newUser.getUserName());
                cs.setString(++i, newUser.getPassword());
                cs.setString(++i, newUser.getUserType());
                cs.executeUpdate();
            
                if (newUser.getUserType().equals(SQLConstants.Patient)) {
                    Patient patient = new Patient();
                    patient.setUserName(request.getParameter("username"));
                    patient.setDefaultDoctor(request.getParameter("default_doctor"));
                    patient.setHealthStatus(request.getParameter("health_status"));
                    patient.setHealthCardNumber(request.getParameter("health_card_number"));
                    patient.setSinNumber(request.getParameter("sin_number"));
                    patient.setPhoneNumber(request.getParameter("phone_number"));
                    patient.setAddress(request.getParameter("address"));

                    cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_PATIENT);
                    i = 0;
                    cs.setString(++i, patient.getUserName());
                    cs.setString(++i, patient.getDefaultDoctor());
                    cs.setString(++i, patient.getHealthStatus());
                    cs.setString(++i, patient.getHealthCardNumber());
                    cs.setString(++i, patient.getSinNumber());
                    cs.setString(++i, patient.getPhoneNumber());
                    cs.setString(++i, patient.getAddress());
                    cs.executeUpdate();
                }
                else if (newUser.getUserType().equals(SQLConstants.Doctor)) {
                    Doctor doctor = new Doctor();
                    doctor.setUserName(request.getParameter("username"));
                    doctor.setCpsoNumber(request.getParameter("cpso"));
                    doctor.setDepartment(request.getParameter("department"));

                    cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_DOCTOR);
                    i = 0;
                    cs.setString(++i, doctor.getUserName());
                    cs.setString(++i, doctor.getCpsoNumber());
                    cs.setString(++i, doctor.getDepartment());
                    cs.executeUpdate();
                } 
                SQLConstants.CONN.commit();
            }
        } catch (SQLException e) {
            try {
                logger.error(e.toString());
                SQLConstants.CONN.rollback();
            } catch (SQLException ex) {
            }
        } catch (Exception e) {
            try {
                logger.error(e.toString());
                SQLConstants.CONN.rollback();
            } catch (SQLException ex) {
            }
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                }
            }
            try {
                SQLConstants.CONN.setAutoCommit(true);
            } catch (SQLException e) {
            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
