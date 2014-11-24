<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Visit Records</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="page-header refresh-header">
            <div class="form-inline">
                <button id="refreshViewAppointments" type="button" class="btn btn-primary">Refresh Data</button>
            </div>
        </div> 
        <div class="appointments-panel" id="upcomingAppointmentsDiv">
            <p class="lead">
                Upcoming Appointments
            </p>
            <div id="upcomingAppointmentsContent"></div>
        </div>
        <div class="appointments-panel" id="pastAppointmentsDiv">
            <p class="lead">
                Past Appointments
            </p>
            <div id="pastAppointmentsContent"></div>
        </div>
        <script type="text/javascript">
            
            var username = "";
            $(document).ready(function() {
                username = "${sessionScope.username}";
                dataString = "{ \"username\": \"" + username + "\" }";
                $.ajax({
                    type: "POST",
                    url: "../ViewAppointmentServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#upcomingAppointmentsContent").html(data.upcoming);
                        $("#pastAppointmentsContent").html(data.past);
                    }
                });
            });

            $("#refreshViewAppointments").click(function() {
                dataString = "{ \"username\": \"" + username + "\" }";
                $.ajax({
                    type: "POST",
                    url: "../ViewAppointmentServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#upcomingAppointmentsContent").html(data.upcoming);
                        $("#pastAppointmentsContent").html(data.past);
                    }
                });
            });
            
            $("#editAppointments").click(function() {
                dataString = "{ \"username\": \"" + username + "\" }";
                $.ajax({
                    type: "POST",
                    url: "../ViewAppointmentServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#upcomingAppointmentsContent").html(data.upcoming);
                        $("#pastAppointmentsContent").html(data.past);
                    }
                });
            });
        </script>
    </body>
</html>
