<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Visit Records</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
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
                          <tr>
                              <td><p id="surgery_name" name="surgery_name"></p></td>
                              <td><input id="prescription" type="text" class="form-control" name="prescription" value=""></td>
                              <td><input id="diagnosis" type="text" class="form-control" name="diagnosis" value=""></td>
                              <td><input id="comments" type="text" class="form-control" name="comments" value="">
                              <input type="hidden" id="start_time" value="">
                                <input type="hidden" id="patient_id" value=""></td>
                          </tr>
                      </tbody>
                  </table>
              </div>
              <div class="modal-footer">
                   <button id = "editRecordsSaveChanges" type="button" class="btn btn-primary">Save changes</button>
                   <p id="error_message"></p>
              </div>
            </div>
          </div>
        </div>
        <div class="page-header refresh-header">
            <div class="form-inline">
                <p id="update_message"></p>
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
            
            function loadData() {
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
            }
            
            $(document).ready(function() {
                username = "${sessionScope.username}";
                loadData();
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
                cpso = "${sessionScope.cpsonumber}";
                prescription = $("#prescription").val();
                diagnosis = $("#diagnosis").val();
                comments = $("#comments").val();
                s_time = $("#start_time").val();
                p_id = $("#patient_id").val();
                
                $.ajax({
                    type: "POST",
                    url: "../EditPatientVisitRecordsServlet",
                    data: {
                        prescription: prescription,
                        diagnosis: diagnosis,
                        comments: comments,
                        start_time: s_time,
                        patient_id: p_id,
                        cpso_number: cpso
                    },
                    dataType: "JSON",
                    success: function (data) {
                            console.log(data);
                            if (data.success === "true") {
                                $("#update_message").html("Update Successful.");
                                $("#update_message").addClass("alert alert-success message");
                                $("#editVisitModal").modal('toggle');
                                $("#prescription").val("");
                                $("#diagnosis").val("");
                                $("#comments").val("");
                                loadData();
                            }
                            else {
                                $("#error_message").addClass("alert alert-danger message");
                                $("#error_message").html("Update failed.");
                        }
                    }
                });
            });
            
           function openVisitModal(id, start_time) {
               cpso = "${sessionScope.cpsonumber}";
               $.ajax({
                   type: "POST",
                   url: "../ViewExistingVisitInformation",
                   data: {cpso_number: cpso, start_time: start_time},
                   dataType: "JSON",
                   success: function(data) {
                       console.log(data);
                       if (data.output !== null) {
                            $("#surgery_name").html(data.output.surgery_name);
                            $("#prescription").val(data.output.prescription);
                            $("#diagnosis").val(data.output.diagnosis);
                            $("#comments").val(data.output.comments);
                            $("#patient_id").val(id);
                            $("#start_time").val(start_time);
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
