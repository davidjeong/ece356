<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <title>Welcome</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar" />
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#" onclick="load_patients()">My Patients</a></li>
                        <li><a href="#">My History</a></li>
                        <li><a href="#" onclick="load_visits()">My Appointments</a></li>
                        <li><a href="#" onclick="user_creation()">Create New User</a></li>
                     </ul>
                </div>
            </div>
        </nav>
        <div class="container" id="right-panel">
        </div>

        <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script> 
        <script type="text/javascript">
            function load_visits() {
                $("#right-panel").load("jsp/visit_record.jsp");
            }
            
            function load_patients() {
                $("#right-panel").load("jsp/view_patients.jsp");
            }
            
            function user_creation() {
                $("#right-panel").load("jsp/user_creation.jsp");
            }
        </script> 
        <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
