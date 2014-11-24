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
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
              </div>
              <div class="modal-body" id="modalBody">
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id = "editRecordsSaveChanges" type="button" class="btn btn-primary">Save changes</button>
              </div>
            </div>
          </div>
        </div>
        <script type="text/javascript">
            $("#ajaxEditRecords").submit(function(e){
                e.preventDefault();
            });
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
            
            $("#editRecordsSaveChanges").click(function() {
                dataString = $("#ajaxEditRecords").serialize();
                console.log(dataString);
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
            
           function openVisitModal(id) {
                $.ajax({
                    type: "POST",
                    url: "../EditPatientVisitRecordsServlet",
                    data: {patient_id: id},
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === 'true') {
                            $("#modalBody").html(data.output);
                            $("#myModalLabel").html("Edit Records for Patient "+id)
                        } else if (data.success === 'false') {
                            $("#modalBody").html("Sorry, an error occured. Please contact an administrator");
                        }
                        $('#myModal').modal({
                            show: true
                        });
                    }
                });
            }
        </script>
    </body>
</html>
