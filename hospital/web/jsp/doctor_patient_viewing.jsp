<%@page import="org.hospital.entities.Patient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Patients' Viewing Rights</title>
    </head>
    <body>
        <jsp:include page="/DoctorPatientViewingServlet"/>
        <% List<Patient> patientList = (List<Patient>)session.getAttribute("PatientList"); %>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Patient ID</th>
                    <th>Legal Name</th>
                </tr>
            </thead>
            <tbody>
            <% int i = 0;
            while (i<patientList.size()) { 
                Patient p = patientList.get(i); 
                int patientId = p.getPatientId();
                String legalName = p.getLegalName(); 
            %>
            <tr>
                <td><%=patientId%></td>
                <td><%=legalName%></td>
            </tr>
            </tbody>
            <% i++; 
            } %>
        </table>
    </body>
</html>
