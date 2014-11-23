<%@page import="org.hospital.other.SQLConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/jquery-ui.min.css">
        <link rel="stylesheet" type="text/css" href="../css/jquery-ui.theme.min.css">
        <link rel="stylesheet" type="text/css" href="../css/jquery-ui-timepicker-addon.css">
        <link rel="stylesheet" type="text/css" href="../css/styles.css">
        <title>Welcome to MediCare</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"></button>
                    <a class="navbar-brand">MediCare</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <% String userType = session.getAttribute("usertype").toString(); 
                        if ((userType).equals(SQLConstants.Doctor)) { %>
                            <li><a href="#" onclick="load_patients();">My Patients</a></li>
                            <li><a href="#" onclick="load_appointments();">My Appointments</a></li>
                            <li><a href="#" onclick="manage_doctor_patient_viewing();">Manage Patients</a></li>
                        <% } 
                        if ((userType).equals(SQLConstants.Staff)) { %>
                            <li><a href="#" onclick="user_creation();">Create New User</a></li>
                            <li><a href="#" onclick="manage_appointments();">Manage Appointments</a></li>
                        <% } 
                        if ((userType).equals(SQLConstants.Finance)) { %>
                             <li><a href="#" onclick="doctor_visits();">Doctor Visits</a></li>
                             <li><a href="#" onclick="patient_visits();">Patient Visits</a></li>
                        <% } %>
                     </ul>
                     <% String legalName = session.getAttribute("legalname").toString(); 
                        if (!legalName.isEmpty()) {
                     %>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="../index.jsp">Logout</a></li>
                        <li><a href="#">Welcome&nbsp;<%=legalName%></a></li>
                    </ul>
                    <% } %>
                </div>
            </div>
        </nav>
        <div class="container" id="content-panel">
            
        <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script> 
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
        <script type="text/javascript" src="../js/script.js"></script>
    </body>
</html>
