/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import org.hospital.other.SQLConstants;
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
import javax.servlet.http.HttpSession;
import org.hospital.entities.Doctor;
import org.hospital.entities.Patient;
import org.hospital.other.SQLConstants;
import org.hospital.entities.User;
import org.hospital.other.MySQLConnection;
import org.hospital.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "ViewPatientsServlet", urlPatterns = {"/ViewPatientsServlet"})
public class ViewPatientsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);
    
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
        
        List<Patient> PatientList = null;
   
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        
        try {
            cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ASSIGNED_DOCTOR);
            cs.setString(1, request.getSession().getAttribute("cpsonumber").toString());
            
            logger.info("Adding [" + request.getSession().getAttribute("cpsonumber").toString() + "] to patient list");
            
            rs = cs.executeQuery();
            
            if (rs != null)
            { 
                PatientList = new ArrayList();
                while (rs.next())
                {
                    Patient p = new Patient();
                    p.setPatientId(rs.getString("patient_id"));
                    p.setLegalName(rs.getString("legal_name"));
                    p.setDefaultDoctor(rs.getString("default_doctor"));
                    p.setHealthStatus(rs.getString("health_status"));
                    PatientList.add(p);
                    logger.info("Adding [" + p + "] to patient list");
                }
            }
        }
        catch (SQLException e)
        {
                    
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
            if (PatientList != null) 
            {
                request.getSession().setAttribute("PatientList", PatientList);
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