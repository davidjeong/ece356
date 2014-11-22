<%@page import="org.hospital.other.SQLConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
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
                            <li><a href="#">My History</a></li>
                            <li><a href="#" onclick="load_visits();">My Appointments</a></li>
                            <li><a href="#" onclick="manage_doctor_patient_viewing();">Manage Patients</a></li>
                        <% } 
                        if ((userType).equals(SQLConstants.Staff)) { %>
                            <li><a href="#" onclick="user_creation();">Create New User</a></li>
                            <li><a href="#" onclick="manage_appointments();">Manage Appointments</a></li>
                        <% } 
                        if ((userType).equals(SQLConstants.Finance)) { %>
                             <li><a href="#" onclick="view_finance();">Patient Visits</a></li>      
                        <% } %>
                     </ul>
                     <% String legalName = session.getAttribute("legalname").toString(); 
                        if (!legalName.isEmpty()) {
                     %>
                     <p class="navbar-text navbar-right">
                         Welcome&nbsp;<%=legalName%>
                     </p>
                     <% } %>
                </div>
            </div>
        </nav>
        <div class="container" id="content-panel">
        </div>
        <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script> 
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
    </body>
</html>
