/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hospital.entities.Patient;
import org.hospital.other.SQLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/DoctorPatientViewingServlet"})
public class DoctorPatientViewingServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ViewPatientsServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cpsoNumber = request.getSession().getAttribute("cpsonumber").toString();
        
        String cpso = request.getParameter("cpsonumber");
        String patientId = request.getParameter("patient_id");
        String doctorId = request.getParameter("doctor_id");

        if (cpso != null) {
            CallableStatement csPatients = null;
            ResultSet rsPatients = null;
            ArrayList<Patient> patientList = new ArrayList<>();

            try {
                csPatients = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ASSIGNED_DOCTOR);
                csPatients.setString(1, cpsoNumber);
                rsPatients = csPatients.executeQuery();

                while (rsPatients.next()) {
                    Patient p = new Patient();
                    p.setPatientId(rsPatients.getInt("patient_id"));
                    p.setLegalName(rsPatients.getString("patient_legal_name"));
                    p.setDefaultDoctor(rsPatients.getString("doctor_legal_name"));
                    p.setHealthStatus(rsPatients.getString("health_status"));
                    patientList.add(p);
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (csPatients != null) {
                    try {
                        csPatients.close();
                    } catch (SQLException ex) {
                        java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (rsPatients != null) {
                    try {
                        rsPatients.close();
                    } catch (SQLException ex) {
                        java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            CallableStatement csDoctors = null;
            ResultSet rsDoctors = null;
            ArrayList<Doctor> doctorList = new ArrayList<>();

            try {
                csDoctors = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_DOCTORS);
                rsDoctors = csDoctors.executeQuery();

                while (rsDoctors.next()) {
                    Doctor d = new Doctor();
                    d.setLegalName(rsDoctors.getString("legal_name"));
                    d.setCpsoNumber(rsDoctors.getString("cpso_number"));
                    d.setDepartment(rsDoctors.getString("department"));
                    doctorList.add(d);
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (csDoctors != null) {
                try {
                    csDoctors.close();
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rsDoctors != null) {
                try {
                    rsDoctors.close();
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
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

            StringBuilder sbPatients = new StringBuilder();
            sbPatients.append("<table class='table table-hover'>");
            sbPatients.append("<thead>");
            sbPatients.append("<tr>");
            sbPatients.append("<th/>");
            sbPatients.append("<th>Patient ID</th>");
            sbPatients.append("<th>Legal Name</th>");
            sbPatients.append("<th>Health Status</th>");
            sbPatients.append("</tr>");
            sbPatients.append("</thead>");
            if (patientList.size() > 0) {
                sbPatients.append("<tbody>");
                for (Patient p : patientList) {
                    sbPatients.append("<tr>");
                    sbPatients.append("<td>").append("<input type=\'radio\'/ name=\'patients\' onclick=\'onPatientClick(").append(p.getPatientId()).append(");\'>").append("</td>");
                    sbPatients.append("<td>").append(p.getPatientId()).append("</td>");
                    sbPatients.append("<td>").append(p.getLegalName()).append("</td>");
                    sbPatients.append("<td>").append(p.getHealthStatus()).append("</td>");
                    sbPatients.append("</tr>");
                }
                sbPatients.append("</tbody>");
            }
            sbPatients.append("</table>");

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
            if (patientList.size() > 0) {
                sbDoctors.append("<tbody>");
                for (Doctor d : doctorList) {
                    sbDoctors.append("<tr>");
                    sbDoctors.append("<td>").append("<input name=\'doctors[]\' type=\'checkbox\' value=\'").append(d.getCpsoNumber()).append("\'").append(" onclick=\'onDoctorClick(this, \\\"" + d.getCpsoNumber() + "\\\")\'").append("/>").append("</td>");   
                    sbDoctors.append("<td>").append(d.getCpsoNumber()).append("</td>");
                    sbDoctors.append("<td>").append(d.getLegalName()).append("</td>"); 
                    sbDoctors.append("<td>").append(d.getDepartment()).append("</td>");
                    sbDoctors.append("</tr>");
                }
                sbDoctors.append("</tbody>");
            }
            sbDoctors.append("</table>");

            output.println(" { \"outputPatient\": \"" + sbPatients.toString() + "\", \"outputDoctor\": \"" + sbDoctors.toString() + "\" } ");
        } 
        
        else if (patientId != null) {
            CallableStatement csDoctorsAll = null;
            ResultSet rsDoctorsAll = null;
            ArrayList<Doctor> doctorAllList = new ArrayList<>();

            try {
                csDoctorsAll = SQLConstants.CONN.prepareCall(SQLConstants.VIEW_ALL_DOCTORS);
                rsDoctorsAll = csDoctorsAll.executeQuery();

                while (rsDoctorsAll.next()) {
                    Doctor d = new Doctor();
                    d.setLegalName(rsDoctorsAll.getString("legal_name"));
                    d.setCpsoNumber(rsDoctorsAll.getString("cpso_number"));
                    d.setDepartment(rsDoctorsAll.getString("department"));
                    doctorAllList.add(d);
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (csDoctorsAll != null) {
                try {
                    csDoctorsAll.close();
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rsDoctorsAll != null) {
                try {
                    rsDoctorsAll.close();
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (csDoctorsAll != null) {
                try {
                    csDoctorsAll.close();
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rsDoctorsAssigned != null) {
                try {
                    rsDoctorsAssigned.close();
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(DoctorPatientViewingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
                sbDoctors.append("<tr>");
                if (doctorAssignedList.contains(d.getCpsoNumber())) {
                    sbDoctors.append("<td>").append("<input name=\'doctors[]\' type=\'checkbox\' value=\'")
                            .append(d.getCpsoNumber()).append("\' checked=\'")
                            .append(d.getCpsoNumber()).append("\'")
                            .append(" onclick=\'onDoctorClick(this, \\\"" + d.getCpsoNumber() + "\\\")\'")
                            .append("/>")
                            .append("</td>");
                } else {
                    sbDoctors.append("<td>").append("<input name=\'doctors[]\' type=\'checkbox\' value=\'")
                            .append(d.getCpsoNumber()).append("\'")
                            .append(" onclick=\'onDoctorClick(this, \\\"" + d.getCpsoNumber() + "\\\")\'")
                            .append("/>")
                            .append("</td>");
                }
                sbDoctors.append("<td>").append(d.getCpsoNumber()).append("</td>");
                sbDoctors.append("<td>").append(d.getLegalName()).append("</td>");
                sbDoctors.append("<td>").append(d.getDepartment()).append("</td>");
                sbDoctors.append("</tr>");
            }
            sbDoctors.append("</tbody>");
            sbDoctors.append("</table>");
            output.write(" { \"outputDoctor\": \"" + sbDoctors.toString() + "\" } ");
        }
    }
}