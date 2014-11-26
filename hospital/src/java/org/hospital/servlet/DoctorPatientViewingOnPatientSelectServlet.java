package org.hospital.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hospital.entities.Doctor;
import org.hospital.other.SQLConstants;

@WebServlet(urlPatterns = {"/DoctorPatientViewingOnPatientSelectServlet"})
public class DoctorPatientViewingOnPatientSelectServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientId = request.getParameter("patient_id");
        String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();


        CallableStatement csDoctorsAll = null;
        ResultSet rsDoctorsAll = null;
        ArrayList<Doctor> doctorAllList = new ArrayList<>();

        try {
            csDoctorsAll = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_DOCTORS);
            rsDoctorsAll = csDoctorsAll.executeQuery();

            while (rsDoctorsAll.next()) {
                Doctor d = new Doctor();
                d.setLegalName(rsDoctorsAll.getString("legal_name"));
                d.setUserName(rsDoctorsAll.getString("user_name"));
                d.setCpsoNumber(rsDoctorsAll.getString("cpso_number"));
                d.setDepartment(rsDoctorsAll.getString("department"));
                doctorAllList.add(d);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (csDoctorsAll != null) {
            try {
                csDoctorsAll.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rsDoctorsAll != null) {
            try {
                rsDoctorsAll.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        CallableStatement csDoctorsAssigned = null;
        ResultSet rsDoctorsAssigned = null;
        ArrayList<String> doctorAssignedList = new ArrayList<>();

        try {
            csDoctorsAssigned = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_DOCTORS_FOR_PATIENT);
            csDoctorsAssigned.setString(1, patientId);
            rsDoctorsAssigned = csDoctorsAssigned.executeQuery();

            while (rsDoctorsAssigned.next()) {
                String cpsoAssigned = rsDoctorsAssigned.getString("cpso_number");
                doctorAssignedList.add(cpsoAssigned);
            }
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (csDoctorsAll != null) {
            try {
                csDoctorsAll.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (rsDoctorsAssigned != null) {
            try {
                rsDoctorsAssigned.close();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingUpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        PrintWriter output = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");

        StringBuilder sbDoctors = new StringBuilder();
        sbDoctors.append("<table class='table table-hover'>");
        sbDoctors.append("<thead>");
        sbDoctors.append("<tr>");
        sbDoctors.append("<th/>");
        sbDoctors.append("<th>CPSO Number</th>");
        sbDoctors.append("<th>Legal Name</th>");
        sbDoctors.append("<th>Department</th>");
        sbDoctors.append("</tr>");
        sbDoctors.append("</thead>");
        sbDoctors.append("<tbody>");
        for (Doctor d : doctorAllList) {
            if (!d.getCpsoNumber().equals(cpsoNumber)) {
                sbDoctors.append("<tr>");
                if (doctorAssignedList.contains(d.getCpsoNumber())) {
                    sbDoctors.append("<td>").append("<input name=\'doctors[]\' type=\'checkbox\' value=\'")
                            .append(d.getUserName()).append("\' checked=\'")
                            .append(d.getCpsoNumber()).append("\'")
                            .append(" onclick=\'onDoctorClick(this, \\\"" + d.getCpsoNumber() + "\\\")\'")
                            .append("/>")
                            .append("</td>");
                } else {
                    sbDoctors.append("<td>").append("<input name=\'doctors[]\' type=\'checkbox\' value=\'")
                            .append(d.getUserName()).append("\'")
                            .append(" onclick=\'onDoctorClick(this, \\\"" + d.getCpsoNumber() + "\\\")\'")
                            .append("/>")
                            .append("</td>");
                }
                sbDoctors.append("<td>").append(d.getCpsoNumber()).append("</td>");
                sbDoctors.append("<td>").append(d.getLegalName()).append("</td>");
                sbDoctors.append("<td>").append(d.getDepartment()).append("</td>");
                sbDoctors.append("</tr>");
            }
        }
        sbDoctors.append("</tbody>");
        sbDoctors.append("</table>");
        output.write(" { \"outputDoctor\": \"" + sbDoctors.toString() + "\" } ");
    }
}
