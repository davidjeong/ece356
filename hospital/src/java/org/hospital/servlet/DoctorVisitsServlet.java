/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hospital.servlet;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.other.MySQLConnection;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/DoctorVisitsServlet"})
public class DoctorVisitsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(DoctorVisitsServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         CallableStatement cs = null;
        ResultSet rs = null;
        int PatientVisits = 0;
        String cpso = null;
        String datetime = null;
        
        if (SQLConstants.CONN == null) {
            MySQLConnection.establish();
        }
        try {
            cpso = request.getParameter("cpso");
            datetime = request.getParameter("searchtime");
            logger.info(datetime);
            if (!cpso.isEmpty() && !datetime.isEmpty()) {
                cs = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_PATIENT_VISIT);
                cs.setString(1, cpso);
                cs.setString(2, datetime);
                rs = cs.executeQuery();
                while (rs.next())
                {
                    PatientVisits = rs.getInt("count(distinct v.patient_id)");
                }
            }
        }            
        catch (SQLException e)
        {
            logger.error(e.toString());
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
            if (PatientVisits > 0) 
            {
                request.getSession().setAttribute("PatientVisits", PatientVisits);
                request.getSession().setAttribute("DesiredDoctor", cpso);
            }
        }
        getServletContext().getRequestDispatcher("/jsp/view_finance_results.jsp").forward(request, response);
    }
}
