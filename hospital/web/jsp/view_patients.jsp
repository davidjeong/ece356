<%@page import="org.hospital.entities.Patient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Patients</title>
    </head>
    <body>
        <jsp:include page="/ViewPatientsServlet"/>
        <% List<Patient> patientList = (List<Patient>)session.getAttribute("PatientList"); %>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Patient ID</th>
                    <th>Legal Name</th>
                    <th>Default Doctor</th>
                    <th>Health Status</th>
                </tr>
            </thead>
            <tbody>
            <% int i = 0;
            while (i<patientList.size()) { 
                Patient p = patientList.get(i); 
                int patientId = p.getPatientId();
                String legalName = p.getLegalName();
                String defaultDoctor = p.getDefaultDoctor();
                String healthStatus = p.getHealthStatus(); 
            %>
            <tr>
                <td><%=patientId%></td>
                <td><%=legalName%></td>
                <td><%=defaultDoctor%></td>
                <td><%=healthStatus%></td>
            </tr>
            </tbody>
            <% i++; 
            } %>
        </table>
    </body>
</html>
