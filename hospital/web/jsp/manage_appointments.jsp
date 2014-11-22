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
            <p class=navbar-brand">Select the doctor which you wish to view appointments:&nbsp;</p>
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
        <div class="row">
            <div id="calendar" class="cal-context" style="width: 100%;">
                <div class="cal-week-box">
                    <div class="cal-offset1 cal-column"></div>
                    <div class="cal-offset2 cal-column"></div>
                    <div class="cal-offset3 cal-column"></div>
                    <div class="cal-offset4 cal-column"></div>
                    <div class="cal-offset5 cal-column"></div>
                    <div class="cal-offset6 cal-column"></div>
                    <div class="cal-offset7 cal-column"></div>
                    <div class="cal-row-fluid cal-row-head"></div>
                </div>
            </div>
        </div>
    </body>
</html>
