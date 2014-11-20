package org.hospital.servlet;

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
import javax.servlet.http.HttpSession;
import org.apache.log4j.PropertyConfigurator;
import org.hospital.other.MySQLConnection;
import org.hospital.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    
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
        
        PropertyConfigurator.configure("log4j.properties");
        
        try {
            
            if (SQLConstants.CONN == null) {
                MySQLConnection.establish();
            }
            
            if (SQLConstants.CONN != null) {
                String userName = request.getParameter("username");
                String password = request.getParameter("password");

                cs = SQLConstants.CONN.prepareCall(SQLConstants.SELECT_VERIFY_USER);
                int i=0;
                cs.setString(++i, userName);
                cs.setString(++i, password);
                rs = cs.executeQuery();

                if (rs != null) {
                    if (rs.next()) {
                        String userType = rs.getString("user_type");
                        SQLConstants.USER = new User();
                        SQLConstants.USER.setUserName(userName);
                        SQLConstants.USER.setPassword(password);
                        SQLConstants.USER.setUserType(userType);

                        HttpSession session = request.getSession(true);
                        session.setAttribute("user", SQLConstants.USER);
                    }
                }

                if (SQLConstants.USER == null) {
                   logger.warn("No user");
                }
                else {
                    logger.info("User found with user name [" + SQLConstants.USER.getUserName() + "], password [" + SQLConstants.USER.getPassword() + "]");

                    //Redirect user based on user type
                    if (SQLConstants.USER.getUserType().equals(SQLConstants.USER_TYPE.Doctor)) {
                        
                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.USER_TYPE.Patient)) {

                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.USER_TYPE.Staff)) {

                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.USER_TYPE.Finance)) {

                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.USER_TYPE.Legal)) {

                    }
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
                } catch (SQLException e) {
                    logger.error(e.toString());
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error(e.toString());
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
