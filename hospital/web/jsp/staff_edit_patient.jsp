<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Information</title>
    </head>
    <body>
        <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="editLabel">Edit Patient Information</h4>
                <p class="mandatory-message" style="text-align: right"><strong>* marks mandatory fields.</strong></p>
              </div>
              <div class="modal-body" id="modalBody">
                  <div>
                  <form name="input" id="ajaxRequestUpdatePatientInfo" class="form-horizontal" role="form" method="POST">
                      <input name="patient_id" type="hidden" id="patient_id" value="">
                       <input name="cpso" type="hidden" id="cpso" value="">
                    <div class="form-group">
                        <label for="default_doctor" class="col-sm-2 control-label">Default Doctor*</label>
                        <div id="default_doctor_div" class="col-sm-10">                    
                        </div>
                        <input name="default_doctor" type="hidden" id="default_doctor" value="">
                    </div>
                    <div class="form-group">
                        <label for="health_status" class="col-sm-2 control-label">Health Status</label>
                        <div class="col-sm-10">
                            <input name="health_status" type="text" class="form-control" id="health_status" placeholder="Health Status">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="health_card_number" class="col-sm-2 control-label">Health Card Number*</label>
                        <div class="col-sm-10">
                            <input name="health_card_number" type="text" class="form-control" id="health_card_number" placeholder="Health Card Number">
                        </div>
                    </div>
                     <div class="form-group">
                        <label for="sin_number" class="col-sm-2 control-label">SIN Number</label>
                        <div class="col-sm-10">
                            <input name="sin_number" type="text" class="form-control" id="sin_number" placeholder="SIN Number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="phone_number" class="col-sm-2 control-label">Phone Number</label>
                        <div class="col-sm-10">
                            <input name="phone_number" type="text" class="form-control" id="phone_number" placeholder="Phone Number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-2 control-label">Address</label>
                        <div class="col-sm-10">
                            <input name="address" type="text" class="form-control" id="address" placeholder="Address">
                        </div>
                    </div>
                  </form>
                  <p id="error_message"></p>
                  </div>
              </div>
              <div class="modal-footer">
                <button id="staffEditPatientSaveChanges" type="button" class="btn btn-warning">Apply Changes</button>
                
              </div>
            </div>
          </div>
        </div>
        <div class="page-header refresh-header">
            <div class="form-inline">
                <p id="creation_message"></p>
                <button id="refreshPatientInformation" type="button" class="btn btn-primary">Refresh Data</button>
            </div>
        </div>
        <div id="patientInformationDiv">
            <p class="lead">
                All Patient Information
            </p>
            <div id="patientInformationContent"></div>
        </div>
        
        <script type="text/javascript">
            
            $("#ajaxRequestUpdatePatientInfo").submit(function(e){
                e.preventDefault();
            });

            $(document).ready(function() {
                loadData();
            });
            
            function loadData() {
                $.ajax({
                    type: "POST",
                    url: "../StaffViewPatientInformationServlet",
                    data: {},
                    dataType: "JSON",
                    success: function (data) {
                        $("#patientInformationContent").html(data.output);
                    }
                });
            }
            
            $("#refreshPatientInformation").click(function() {
                loadData();
            });
            
            function getDoctorList(cpso, doctor_name) {
                $.ajax({
                   type: "POST",
                   url: "../GetAllDoctorsServlet",
                   data: {},
                   dataType: "JSON",
                   success: function(data) {
                       if (data.success === "true") {
                            var html = "<button class='btn btn-default dropdown-toggle' type='button' id='default_doctor_dropdown' data-toggle='dropdown'>Doctors&nbsp;<span class='caret'></span></button>";
                             html += "<ul class='dropdown-menu' role='menu' aria-labelledby='dropdownMenu1'>";
                             for (var i = 0; i < data.output.length; i++) {
                                html += "<li value='" + data.output[i].cpso_number + "' role='presentation'><a role='menuitem' tabindex='" + i + "' href='#' onclick='changeDefaultDoctor(this);'>" + data.output[i].legal_name + "</a></li>";
                            }
                            html += "</ul>";
                        } else {
                               var html = "<p>There are no doctors</p>";
                        }
                        $("#default_doctor_div").html(html);
                        $("#default_doctor").val(cpso);
                        $("#default_doctor_dropdown").html(doctor_name + "&nbsp;<span class=\"caret\"></span>");
                   }
                });
            }
            
            function openEditModal(patient_id, patient_name, doctor_name, cpso, health_status, health_card_number, sin_number, phone_number, address) {
                
                getDoctorList(cpso, doctor_name);
                
                $("#editLabel").html("Edit Information For " + patient_name);
                
                if (health_status === "N/A") {
                    health_status = "";
                }
                if (health_card_number === "N/A") {
                    health_card_number = "";
                }
                if (sin_number === "N/A") {
                    sin_number = "";
                }
                if (phone_number === "N/A") {
                    phone_number = "";
                }
                if (address === "N/A") {
                    address = "";
                }
                
                
                $("#health_status").val(health_status);
                $("#health_card_number").val(health_card_number);
                $("#sin_number").val(sin_number);
                $("#phone_number").val(phone_number);
                $("#address").val(address);
                $("#cpso").val(cpso);
                $("#patient_id").val(patient_id);
                
                $("#editModal").modal({
                    show: true
                });
                
            }
            
            $("#staffEditPatientSaveChanges").click(function() {
                
                dataString = $("#ajaxRequestUpdatePatientInfo").serialize();
                
                $.ajax({
                   type: "POST",
                   url: "../UpdatePatientInformationServlet",
                   data: dataString,
                   dataType: "JSON",
                   success: function(data) {
                      if (data.success === 'true') {
                             $("#error_message").removeAttr("class");
                             $("#error_message").html("");
                             $("#creation_message").html("Update Succesful.");
                             $("#creation_message").addClass("alert alert-success message");
                             $("#ajaxRequestUpdatePatientInfo")[0].reset();
                             $("#editModal").modal('toggle');
                            loadData();
                      } else if (data.success === 'false') {
                            $("#creation_message").removeAttr("class");
                            $("#creation_message").html("");
                            $("#error_message").addClass("alert alert-danger message");
                            $("#error_message").html("Cannot update information.");
                      }
                   }
                });
            });
            
        </script>
    </body>
</html>
