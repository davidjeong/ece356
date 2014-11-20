package org.hospital.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hospital.entities.SQLConstants;
import org.hospital.entities.User;
import org.hospital.other.MySQLConnection;

@WebServlet(urlPatterns = {"/UserCreationServlet"})
public class UserCreationServlet extends HttpServlet {

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

        User user = new User();
        user.setLegalName(request.getParameter("legal_name"));
        user.setUserName(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setUserType(request.getParameter("userType"));

        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }

        if (user.isValid()) {
            try (CallableStatement cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_USER)) {
                int i = 0;
                cs.setString(++i, user.getLegalName());
                cs.setString(++i, user.getUserName());
                cs.setString(++i, user.getPassword());
                cs.setString(++i, user.getUserType());
                cs.executeUpdate();
            } catch (SQLException e) {
            }

            if (user.getUserType().equals("patient")) {
                Patient patient = new Patient();
                patient.setUserName(request.getParameter("username"));
                patient.setDefaultDoctor(request.getParameter("default_doctor"));
                patient.setHealthStatus(request.getParameter("health_status"));
                patient.setHealthCardNumber(request.getParameter("health_card_number"));
                patient.setSinNumber("sin_number");
                patient.setPhoneNumber("phone_number");
                patient.setAddress("address");

                try (CallableStatement cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_PATIENT)) {
                    int i = 0;
                    cs.setString(++i, patient.getUserName());
                    cs.setString(++i, patient.getDefaultDoctor());
                    cs.setString(++i, patient.getHealthStatus());
                    cs.setString(++i, patient.getHealthCardNumber());
                    cs.setString(++i, patient.getSinNumber());
                    cs.setString(++i, patient.getPhoneNumber());
                    cs.setString(++i, patient.getAddress());
                    cs.executeUpdate();
                } catch (SQLException e) {
                }
            } else if (user.getUserType().equals("doctor")) {
                Doctor doctor = new Doctor();
                doctor.setUserName(request.getParameter("username"));
                doctor.setCpsoNumber(request.getParameter("cpso_number"));
                doctor.setDepartment(request.getParameter("department"));

                try (CallableStatement cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_DOCTOR)) {
                    int i = 0;
                    cs.setString(++i, doctor.getUserName());
                    cs.setString(++i, doctor.getCpsoNumber());
                    cs.setString(++i, doctor.getDepartment());
                    cs.executeUpdate();
                } catch (SQLException e) {
                }
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
