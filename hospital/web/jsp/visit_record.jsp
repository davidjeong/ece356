<%@page import="org.hospital.entities.VisitRecord"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Visit Records</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="/ViewRecordServlet"/>
        <% List<VisitRecord> visitList = (List<VisitRecord>)session.getAttribute("VisitList"); %>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Patient ID</th>
                    <th>CPSO Number</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Surgery</th>
                    <th>Prescription</th>
                    <th>Diagnosis</th>
                    <th>Comments</th>
                </tr>
            </thead>
            <tbody>
        
            <% int i = 0;
            while (i<visitList.size()) { 
                VisitRecord vr = visitList.get(i); 
                int patientID = vr.getPatientID();
                int CPSOnum = vr.getCPSONumber();
                String start = vr.getStartTime().toString();
                String end = vr.getEndTime().toString();
                String surgery = vr.getSurgeryName();
                String prescription = vr.getPerscription();
                String diagnosis = vr.getDiagnosis();
                String comments = vr.getComments();
            %>
            <tr>
                <td><%=patientID%></td>
                <td><%=CPSOnum%></td>
                <td><%=start%></td>
                <td><%=end%></td>
                <td><%=surgery%></td>
                <td><%=prescription%></td>
                <td><%=diagnosis%></td>
                <td><%=comments%></td>
            </tr>
            </tbody>
            <% i++; 
            } %>
        </table>
    </body>
</html>
