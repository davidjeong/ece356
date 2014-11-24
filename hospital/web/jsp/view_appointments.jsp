<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Visit Records</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <!-- Modal -->
        <div class="modal fade" id="editVisitModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="editModal">Edit Visit Information</h4>
              </div>
              <div class="modal-body" id="modalBody">
                  <table class="table table-hover">
                      <thead>
                      <tr>
                          <th>Surgery Name</th>
                          <th>Prescription</th>
                          <th>Diagnosis</th>
                          <th>Comments</th>
                      </tr>
                      </thead>
                      <tbody>
                      <form name="input" id="ajaxEditVisitRecord" class="form-control" role="form" method="POST">
                          <tr>
                              <td><p id="surgery_name" name="surgery_name"></p></td>
                              <td><input id="prescription" type="text" class="form-control" name="prescription" value=""></td>
                              <td><input id="diagnosis" type="text" class="form-control" name="diagnosis" value=""></td>
                              <td><input id="comments" type="text" class="form-control" name="comments" value=""></td>
                          </tr>
                      </form>
                      </tbody>
                  </table>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                <button id = "editRecordsSaveChanges" type="button" class="btn btn-primary">Save changes</button>
              </div>
            </div>
          </div>
        </div>
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
            
           function openVisitModal(id, start_time) {
               
               $.ajax({
                   type: "POST",
                   url: "../ViewExistingVisitInformation",
                   data: {patient_id: id, start_time: start_time},
                   dataType: "JSON",
                   success: function(data) {
                       console.log(data);
                       console.log("ss");
                       if (data.output !== null) {
                           console.log(data.output);
                            $("#surgery_name").html(data.output.surgery_name);
                            $("#prescription").val(data.output.prescription);
                            $("#diagnosis").val(data.output.diagnosis);
                            $("#comments").val(data.output.comments);
                        }
                       else {
                       }
                    }
               });
               
                $("#editModal").html("Edit Visit Details for Patient " + id);
                $("#editVisitModal").modal({
                    show: true
                });
            }
        </script>
    </body>
</html>
