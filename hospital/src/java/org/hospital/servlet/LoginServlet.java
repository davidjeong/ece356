package org.hospital.servlet;

import org.hospital.other.SQLConstants;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        CallableStatement cs = null;
        ResultSet rs = null;
        StringBuilder message = null;
        boolean typeFound = false;
        
        try {
            
            SQLConstants.USER = null;
            message = new StringBuilder();
            
            if (SQLConstants.CONN == null) {
                MySQLConnection.establish();
            }
            
            if (SQLConstants.CONN != null) {
                String userName = request.getParameter("username");
                String password = request.getParameter("password");

                if (!userName.isEmpty() && !password.isEmpty()) {
                
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
                       message.append("User name or password is <strong>incorrect</strong>.");
                    }
                    else {
                        logger.info("User found with user name [" + SQLConstants.USER.getUserName() + "], password [" + SQLConstants.USER.getPassword() + "]");
                        //Redirect user based on user type
                        if (SQLConstants.USER.getUserType().equals(SQLConstants.Doctor)) {
                            typeFound = true;
                            cs = SQLConstants.CONN.prepareCall(SQLConstants.USERNAME_TO_CPSONUMBER);
                            cs.setString(1, SQLConstants.USER.getUserName());
                            rs = cs.executeQuery();
                            if (rs.next())
                            {
                                String number = rs.getString("cpso_number");
                                request.getSession().setAttribute("cpsonumber", number);
                            }
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
                        if (typeFound) {
                            message.append("jsp/home_page.jsp");
                        }
                        else {
                            logger.error("Couldn't find user type");
                        }
                    }
                }
                else {
                    logger.warn("Empty fields");
                    message.append("Fields are <strong>empty</strong>.");
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
            
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
            
            if (message != null) {
                out.println(" { \"success\": \"" + typeFound + "\", \"output\": \"" + message.toString() + "\"} ");
                out.close();
            } 
        }
    }
}
