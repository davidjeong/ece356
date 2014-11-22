<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% List list = (List)session.getAttribute("PatientList"); %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View your patients</title>
    </head>
    <body>
        <jsp:include page="/ViewPatientsServlet"/>
        <table style="width:100%">
            <tr>
                <th>Patient ID</th>
                <th>Legal Name</th>
                <th>Default Doctor</th>
                <th>Health Status</th>
            </tr>
            <c:forEach items="${PatientList}" var="Patient">
                <tr>
                    <td><c:out value="${Patient.getPatientId}"/></td>
                    <td><c:out value="${Patient.LegalName}"/></td>
                    <td><c:out value="${Patient.defaultDoctor}"/></td>
                    <td><c:out value="${Patient.healthStatus}"/></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
