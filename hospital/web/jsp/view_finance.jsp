<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Visits</title>
    </head>
    <body>
        <form name="input" action="PatientVisitServlet" method="post">
            <p>CPSO Number: <input type="text" name="cpso"><p> 
            <p>Before Date (Format: YYYY-MM-DD:HH:MM:SS): <input type="text" name="searchtime"><p> 
            <br/>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
