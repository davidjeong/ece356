<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Appointments</title>
    </head>
    <body>
        <div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="inputLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="inputLabel">New Appointment Form</h4>
                <p class="mandatory-message" style="text-align: right"><strong>* marks mandatory fields.</strong></p>
              </div>
              <div class="modal-body" id="modalBody">
                  <div>
                      <form name="input" id="ajaxRequestCreateNewAppointment" class="form-horizontal" role="form" method="POST">
                            <div class="form-group">
                                <label for="cpso" class="col-sm-2 control-label">Doctor*</label>
                                
                                <div class="col-sm-10" id="doctor_div">
                                    <p>There are no doctors.</p>
                                </div>
                                <input type="hidden" name="cpso" id="cpso" value="">
                            </div>
                            <div class="form-group">
                                <label for="patient_id" class="col-sm-2 control-label">Patient*</label>
                                
                                <div class="col-sm-10" id="patient_div">
                                    <p>No patient for selected doctor.</p>
                                </div>
                                <input type="hidden" name="patient_id" id="patient_id" value="">
                            </div>
                            <div class="form-group">
                                <label for="start_range" class="col-sm-2 control-label">Start Date Time*</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="start_range" name="start_range" style="cursor: pointer;" placeholder="Empty Timestamp">
                                </div>
                            </div><div class="form-group">
                                <label for="end_range" class="col-sm-2 control-label">End Date Time*</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="end_range" name="end_range" style="cursor: pointer;"placeholder="Empty Timestamp">
                                </div>
                            </div>
                          <div class="form-group">
                                <label for="surgeryName" class="col-sm-2 control-label">Surgery</label>
                                <div class="col-sm-10" id="surgery_list"></div>
                          </div>
                          <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <p id="error_message"></p>
                                </div>
                          </div>
                      </form>
                  </div>
              </div>
                <div class="modal-footer">
                    <button id="formSubmit" type="submit" class="btn btn-primary" disabled>Submit Form</button>
              </div>
            </div>
          </div>
        </div>
        <div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modifyLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modifyLabel">Modal title</h4>
                <p class="mandatory-message" style="text-align: right"><strong>* marks mandatory fields.</strong></p>
              </div>
              <div class="modal-body" id="modalBody">
                  <form name="input" id="ajaxRequestUpdateAppointment" class="form-horizontal" role="form" method="POST">
                    <div class="form-group">
                        <label for="start_updated_range" class="col-sm-2 control-label">Start Date Time*</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="start_updated_range" name="start_updated_range" style="cursor: pointer;" placeholder="Empty Timestamp">
                        </div>
                    </div><div class="form-group">
                        <label for="end_updated_range" class="col-sm-2 control-label">End Date Time*</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="end_updated_range" name="end_updated_range" style="cursor: pointer;"placeholder="Empty Timestamp">
                        </div>
                    </div>
                      <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <p id="error_update_message"></p>
                            </div>
                      </div>
                  </form>
                  </div>
                <div class="modal-footer">
                    <div class="btn-group nav-btn">
                        <button id="delete_btn" class="btn btn-danger">Delete</button>
                        <button id="update_btn" class="btn btn-warning">Apply</button>
                   </div>
              </div>
            </div>
          </div>
        </div>
        <div class="page-header">
            <div class="form-inline">
                <p id="creation_message"></p>
                <button id="createAppointment" class="btn btn-default nav-btn">Create Appointment</button>
                <div class="btn-group nav-btn">
                    <button class="btn btn-primary nav-button" data-calendar-nav="prev">&lt;&lt; Prev</button>
                    <button class="btn nav-button" data-calendar-nav="today">Today</button>
                    <button  class="btn btn-primary nav-button" data-calendar-nav="next">Next &gt;&gt;</button>
                </div>
                <div class="btn-group nav-btn">
                    <button class="btn btn-warning view-button" data-calendar-view="week">Week</button>
                    <button class="btn btn-warning view-button" data-calendar-view="day">Day</button>
                </div>
            </div>
        </div>
        <div>
            <p class="lead">
                Schedules For All Doctors
            </p>
            <p id="date_status" class="lead date-status"></p>
        </div>
        <div id="calendar" class="cal-context"></div>
        <script type="text/javascript">

            $("#ajaxRequestCreateNewAppointment").submit(function(e){
                e.preventDefault();
            });
            
            $("#ajaxRequestUpdateAppointment").submit(function(e){
                e.preventDefault();
            });
            
            function retrieveCalendar() {
                 $.ajax({
                     type: "POST",
                     url: "../ViewDoctorSchedulesServlet",
                     data: {username: username},
                     dataType: "JSON",
                     success: function(data) {
                        calendar = $("#calendar").calendar({
                            view: "week",
                            events_source: data.events_source
                        });
                       
                        var s = calendar.options.position.start.toDateString();
                        var e = calendar.options.position.end.toDateString();
                        var str = "";
                        if (s !== e) {
                            str += s + " - " + e;
                           }
                        else {
                            str = s;
                        }
                        $("#date_status").html(str);
                        }
                 });
            }

            var username = "";
            var calendar = null;
            $(document).ready(function() {
                username = "${sessionScope.username}";
                retrieveCalendar();
            });
            
            $(".nav-button").click(function() {
                calendar.navigate($(this).data('calendar-nav'));
                var s = calendar.options.position.start.toDateString();
                var e = calendar.options.position.end.toDateString();
                var str = "";
                if (calendar.options.view === "week") {
                    str += s + " - " + e;
                }
                else {
                    str += s;
                }
                $("#date_status").html(str);
                clearMessages();
            });

            $(".view-button").click(function() {
                calendar.view($(this).data('calendar-view'));
                var s = calendar.options.position.start.toDateString();
                var e = calendar.options.position.end.toDateString();
                var str = "";
                if (calendar.options.view === "week") {
                    str += s + " - " + e;
                }
                else {
                    str += s;
                }
                $("#date_status").html(str);
                clearMessages();
            });
            
            function showCreateForm() {
                
                $.ajax({
                   type: "POST",
                   url: "../GetAllDoctorsServlet",
                   data: {},
                   dataType: "JSON",
                   success: function(data) {
                       if (data.success === "true") {
                            var html = "<button class='btn btn-default dropdown-toggle' type='button' id='doctor_dropdown' data-toggle='dropdown'>Doctors&nbsp;<span class='caret'></span></button>";
                             html += "<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>";
                             for (var i = 0; i < data.output.length; i++) {
                                html += "<li value='" + data.output[i].cpso_number + "' role='presentation'><a role='menuitem' tabindex='" + i + "' href='#' onclick='getPatients(&#39;" + data.output[i].legal_name + "&#39;, &#39;" + data.output[i].cpso_number + "&#39;);'>" + data.output[i].legal_name + "</a></li>";
                            }
                            html += "</ul>";
                        } else {
                               var html = "<p>There are no doctors</p>";
                        }
                        $("#doctor_div").html(html);
                   }
                });
                
                $.ajax({
                    type: "POST",
                    url: "../GetAllSurgeriesServlet",
                    data: {},
                    dataType: "JSON",
                    success: function (data) {
                        $("#surgery_list").html(data.surgeries);
                    }
                });
                
                $("#inputModal").modal({
                    show: true
                });
            }
            
            $("#createAppointment").click(function() {
               showCreateForm();
            });
            
            var startDateTextBox = $('#start_range');
            var endDateTextBox = $('#end_range');
   
            $.timepicker.datetimeRange(
                    startDateTextBox, 
                    endDateTextBox,
                    {
                            minDate: 0,
                            minInterval: (1000*60*30), // 30 min
                            dateFormat: 'dd M yy', 
                            timeFormat: 'HH:mm',
                            start: {}, // start picker options
                            end: {} // end picker options					
                    }
            );
    
            var startupDateTextBox = $('#start_updated_range');
            var endupDateTextBox = $('#end_updated_range');
            
            $.timepicker.datetimeRange(
                    startupDateTextBox,
                    endupDateTextBox,
                    {
                            minDate: 0,
                            minInterval: (1000*60*30), // 30 min
                            dateFormat: 'dd M yy', 
                            timeFormat: 'HH:mm',
                            start: {}, // start picker options
                            end: {} // end picker options					
                    }
            );
    
            
            function changePatient(patient, patient_id) {
                $("#patient_id").val(patient_id);
                $("#patient_dropdown").html(patient + "&nbsp;<span class=\"caret\"></span>");
                $("#formSubmit").removeAttr('disabled');
            }
            
            function getPatients(doctor, cpso) {
                $("#cpso").val(cpso);
                $("#doctor_dropdown").html(doctor + "&nbsp;<span class=\"caret\"></span>");
                
                $.ajax({ 
                    type: "POST",
                    url: "../GetAllPatientsForDoctorServlet",
                    data: {cpso_number: cpso},
                    dataType: "JSON",
                    success: function(data) {
                        if (data.success === "true") {
                            var html = "<button class='btn btn-default dropdown-toggle' type='button' id='patient_dropdown' data-toggle='dropdown'>Patients&nbsp;<span class='caret'></span></button>";
                             html += "<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>";
                             for (var i = 0; i < data.output.length; i++) {
                                html += "<li value='" + data.output[i].cpso_number + "' role='presentation'><a role='menuitem' tabindex='" + i + "' href='#' onclick='javascript:changePatient(&#39;" + data.output[i].legal_name + "&#39;, &#39;" + data.output[i].patient_id + "&#39;);'>" + data.output[i].legal_name + "</a></li>";
                            }
                            html += "</ul>";
                            $("#formSubmit").attr('disabled', 'disabled');
                        } else {
                               var html = "<p>There are no patients for this doctor.</p>";
                        }
                        $("#patient_div").html(html);
                    }
                });
            }
            

    
            $("#formSubmit").click(function() {
                
                dataString = $("#ajaxRequestCreateNewAppointment").serialize();
                
               $.ajax({
                   type: "POST",
                   url: "../CreateNewAppointmentServlet",
                   data: dataString,
                   dataType: "JSON",
                   success: function(data) {
                         if (data.inserted !== "0") {
                             //new appointment created.
                             $("#creation_message").html("Appointment Created.");
                             $("#creation_message").addClass("alert alert-success message");
                             $("#ajaxRequestCreateNewAppointment")[0].reset();
                             $("#inputModal").modal('toggle');
                             retrieveCalendar();
                        } else {
                           $("#error_message").addClass("alert alert-danger message");
                           if (data.conflicted === "true") {
                               $("#error_message").html("There is already an appointment for this doctor at the selected times.");
                           } else {
                               if (data.empty === "true") {
                                    $("#error_message").html("Mandatory fields are empty.");
                                }
                                else {
                                    $("#error_message").html("Selected doctor cannot view selected patient.");
                                }
                            }
                        }
                     }
                });
            });
            
            function modifyAppointment(doctor, cpso_number, patient_id, start_time) {
                var date = new Date(start_time);
                var dateString = date.toLocaleDateString();
                var timeString = date.toLocaleTimeString();
                
                $("#modifyLabel").html("Appointment For " + doctor + " At " + dateString + " " + timeString);
                $("#delete_btn").attr("onclick", "javascript:deleteAppointment('" + cpso_number + "','" + start_time + "');");
                $("#update_btn").attr("onclick", "javascript:updateAppointment('" + cpso_number + "','" + start_time + "');");
                $("#modifyModal").modal({
                    show: true
                });
            }
            
            function deleteAppointment(cpso, start_time) {
                               
                $.ajax({
                    type: "POST",
                    url: "../DeleteAppointmentServlet",
                    data: 
                    { 
                        cpso_number: cpso,
                        start_time: start_time
                    },
                    dataType: "JSON",
                    success: function(data) {
                        if (data.count !== "0") {
                             $("#creation_message").html("Appointment Deleted.");
                             $("#creation_message").addClass("alert alert-success message");
                             $("#ajaxRequestUpdateAppointment")[0].reset();
                             $("#modifyModal").modal('toggle');

                             $("#start_updated_range").val("");
                             $("#end_updated_range").val("");

                             retrieveCalendar();
                        } else {
                            $("#error_update_message").html("Cannot delete appointment.");
                            $("#error_update_message").addClass("alert alert-danger message");
                        }
                    } 
                });
            } 
            
            function clearMessages() {
                $("#error_update_message").removeAttr("class");
                $("#error_message").removeAttr("class");
                $("#creation_message").removeAttr("class");
                
                $("#error_update_message").html("");
                $("#error_message").html("");
                $("#creation_message").html("");
            }
            
            function updateAppointment(cpso, start_time) {
                
                new_start = $("#start_updated_range").val();
                new_end = $("#end_updated_range").val();
                
                $.ajax({
                    type: "POST",
                    url: "../UpdateAppointmentServlet",
                    data: 
                    { 
                        cpso_number: cpso,
                        start_time: start_time,
                        new_start: new_start,
                        new_end: new_end
                    },
                    dataType: "JSON",
                    success: function(data) {
                        if (data.updated !== "0") {
                             $("#creation_message").html("Appointment Updated.");
                             $("#creation_message").addClass("alert alert-success message");
                             $("#ajaxRequestUpdateAppointment")[0].reset();
                             $("#modifyModal").modal('toggle');

                             $("#start_updated_range").val("");
                             $("#end_updated_range").val("");

                             retrieveCalendar();
                        } else {
                            $("#error_update_message").addClass("alert alert-danger message");
                            if (data.conflicted === "true") {
                                $("#error_update_message").html("There is already an appointment for this doctor at the selected times.");
                            } else {
                                if (data.empty === "true") {
                                    $("#error_update_message").html("Mandatory fields are empty.");
                                } else {
                                    $("#error_update_message").html("Selected doctor cannot view selected patient.");
                                }
                            }
                        }
                    } 
                });
            }
            
        </script>
    </body>
</html>
