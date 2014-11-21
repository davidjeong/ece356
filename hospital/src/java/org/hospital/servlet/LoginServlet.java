package org.hospital.servlet;

import org.hospital.other.SQLConstants;
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
                        String legalName = rs.getString("legal_name");
                        SQLConstants.USER = new User();
                        SQLConstants.USER.setLegalName(legalName);
                        SQLConstants.USER.setUserName(userName);
                        SQLConstants.USER.setPassword(password);
                        SQLConstants.USER.setUserType(userType);

                        HttpSession session = request.getSession(true);
                        session.setAttribute("legalname", SQLConstants.USER.getLegalName());
                        session.setAttribute("username", SQLConstants.USER.getUserName());
                        session.setAttribute("usertype", SQLConstants.USER.getUserType());
                    }
                }

                if (SQLConstants.USER == null) {
                   logger.warn("No user");
                   request.setAttribute("message", "User name or password is incorrect.");
                   request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                else {
                    logger.info("User found with user name [" + SQLConstants.USER.getUserName() + "], password [" + SQLConstants.USER.getPassword() + "]");

                    //Redirect user based on user type
                    boolean typeFound = false;
                    if (SQLConstants.USER.getUserType().equals(SQLConstants.Doctor)) {
                        typeFound = true;
                        getServletContext().getRequestDispatcher("/jsp/doctor_ui.jsp").forward(request, response);
                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.Patient)) {
                        typeFound = true;
                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.Staff)) {
                        typeFound = true;
                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.Finance)) {
                        typeFound = true;
                    }
                    else if (SQLConstants.USER.getUserType().equals(SQLConstants.Legal)) {
                        typeFound = true;
                    }
                    if (!typeFound) {
                        logger.error("Couldn't find user type");
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
