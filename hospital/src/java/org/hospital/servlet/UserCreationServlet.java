package org.hospital.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.hospital.entities.SQLConstants;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.entities.User;

@WebServlet(urlPatterns = {"/UserCreationServlet"})
public class UserCreationServlet extends HttpServlet {

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
       
        CallableStatement cs = null;
        ResultSet rs = null;
       
        try{
            User user = new User();
            user.setLegalName(request.getParameter("legalName"));
            user.setUserName(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setUserType(request.getParameter("userType"));
            
            if (user.isValid()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.INSERT_NEW_USER);
                int i=0;
                cs.setString(++i, user.getLegalName());
                cs.setString(++i, user.getUserName());
                cs.setString(++i, user.getPassword());
                cs.setString(++i, user.getUserType());
                rs = cs.executeQuery();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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
        }
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
        processRequest(request, response);
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
