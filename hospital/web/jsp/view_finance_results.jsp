<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>Doctor Visits Results</title>
    </head>
    <body>
        <% String NumberOfPatients = session.getAttribute("PatientVisits").toString(); %>
         <table class="table table-hover">
            <thead>
                <tr>
                    <th>Patient ID</th>
                    <th>Legal Name</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th><%=NumberOfPatients%></th>
                </tr>
            </thead>
            <tbody>
                
    </body>
</html>
