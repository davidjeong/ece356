<%@page import="java.util.List"%>
<%@page import="org.hospital.entities.Doctor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Appointments</title>
    </head>
    <body>
        <jsp:include page="/ManageAppointmentServlet" />
        <% List<Doctor> doctorList = (List<Doctor>)session.getAttribute("doctorList"); %>
        <div class="dropdown clearfix">
            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">Doctors&nbsp;<span class="caret"></span></button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <% int i = 0;
                while (i<doctorList.size()) {
                    Doctor doctor = doctorList.get(i);
                    String legalName = doctor.getLegalName();
                    String cpsoNumber = doctor.getCpsoNumber();
                %>
                <li role="presentation"><a role="menuitem" tabindex="<%=i%>" value="<%=cpsoNumber%>"href="#"><%=legalName%></a></li>
                <% i++; } %>
            </ul>
        </div>
    </body>
</html>
