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
import org.hospital.entities.User;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/DoctorListStaffAssignmentServlet"})
public class DoctorListStaffAssignmentServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CallableStatement csAll = null;
        CallableStatement csAssigned = null;

        ResultSet rsAll = null;
        ResultSet rsAssigned = null;

        List<User> staffAllList = null;
        List<String> staffAssignedList = new ArrayList<>();

        StringBuilder outputAll = null;
        boolean success = false;

        String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();

        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }

        try {
            csAll = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_STAFF);
            rsAll = csAll.executeQuery();
            if (rsAll != null) {
                staffAllList = new ArrayList();
                while (rsAll.next()) {
                    User staff = new User();
                    staff.setLegalName(rsAll.getString("legal_name"));
                    staff.setUserName(rsAll.getString("user_name"));
                    staffAllList.add(staff);
                }
                success = true;
            }

            csAssigned = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_STAFFS_FOR_DOCTOR);
            csAssigned.setString(1, cpsoNumber);
            rsAssigned = csAssigned.executeQuery();

            while (rsAssigned.next()) {
                String staffName = rsAssigned.getString("user_name");
                staffAssignedList.add(staffName);
            }

        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            if (csAll != null) {
                try {
                    csAll.close();
                } catch (SQLException ex) {
                }
            }
            if (rsAll != null) {
                try {
                    rsAll.close();
                } catch (SQLException ex) {
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
            
            outputAll = new StringBuilder();
            if (staffAllList != null && !staffAllList.isEmpty()) {
                outputAll.append("<table class='table table-hover'>");
                outputAll.append("<thead>");
                outputAll.append("<tr>");
                outputAll.append("<th/>");
                outputAll.append("<th>User Name</th>");
                outputAll.append("<th>Legal Name</th>");
                outputAll.append("</tr>");
                outputAll.append("</thead>");
                if (!staffAllList.isEmpty()) {
                    outputAll.append("<tbody>");
                    for (User staff : staffAllList) {
                        outputAll.append("<tr>");
                        if (staffAssignedList.contains(staff.getUserName())) {
                            outputAll.append("<td align=\'center\'>").append("<input type=\'checkbox\' name=\'staff[]\'").append(" checked=\'").append(staff.getUserName()).append("\'").append(" value=\'").append(staff.getUserName()).append("\'").append(" onclick=\'onStaffClick(\\\"").append(staff.getUserName()).append("\\\");\'>").append("</td>");
                        } else {
                            outputAll.append("<td align=\'center\'>").append("<input type=\'checkbox\' name=\'staff[]\'").append(" value=\'").append(staff.getUserName()).append("\'").append(" onclick=\'onStaffClick(\\\"").append(staff.getUserName()).append("\\\");\'>").append("</td>");
                        }
                        outputAll.append("<td>").append(staff.getUserName()).append("</td>");
                        outputAll.append("<td>").append(staff.getLegalName()).append("</td>");
                        outputAll.append("</tr>");
                    }
                }
                outputAll.append("</table>");
                out.println(" { \"success\": \"" + success + "\", \"output\": \"" + outputAll.toString() + "\"} ");
            } else {
                out.println(" { \"success\": \"" + success + "\", \"output\": \"" + "There are no staff members registered in the database" + "\"} ");
            }
            out.close();
        }
    }
}
