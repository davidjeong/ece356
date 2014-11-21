<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>Welcome</title>
    </head>
    <body>
        <div class="top-panel">
            <div class="name">
            Hello Doctor
        </div>
        <div class="left-panel" id="left-panel">
            <ul><a href="#">My Patients</a></ul>
            <ul><a href="view_history.jsp">My History</a></ul>
            <ul><a href="#" onclick="load_visits()">My Appointments</a></ul>
        </div>
        <div class="right-panel" id="right-panel">
            Second panel
        </div>
        
        <div style="background-color:lightgrey">
            <a href="jsp/visit_record.jsp">Click!</a>
        </div>
            
        <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script> 
        <script type="text/javascript">
            function load_visits() {
                $("#right-panel").load("jsp/visit_record.jsp");
            }
            
            function load_patients() {
                $("#right-panel").load("jsp/view_patients.jsp");
            }
        </script> 
            
    </body>
</html>
