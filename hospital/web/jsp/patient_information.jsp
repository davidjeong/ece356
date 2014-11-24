<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Information</title>
    </head>
    <body>
        <div class="page-header">
            <p class="mandatory-message"><strong>* marks mandatory fields.</strong></p>
        </div>
        
        <div class="summary-panel" id="resultsDiv">
             <p class="lead">
               Details of Personal Information of Patient
             </p>
        <div id="summaryPatientContent"></div>
        </div>
        
        <div class="modal fade" id="editPatientModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="editModal">Edit Patient Information</h4>
              </div>
              <div class="modal-body" id="modalBody">
                  <table class="table table-hover">
                      <thead>
                      <tr>
                          <th>Health Card Number</th>
                          <th>Sin Number</th>
                          <th>Phone Number</th>
                          <th>Address</th>
                      </tr>
                      </thead>
                      <tbody>
                          <tr>
                              <td><input id="Health_Card_Number" type="text" class="form-control" name="Health_Card_Number"></td>
                              <td><input id="Sin_Number" type="text" class="form-control" name="Sin_Number" value=""></td>
                              <td><input id="Phone_Number" type="text" class="form-control" name="Phone_Number" value=""></td>
                              <td><input id="Address" type="text" class="form-control" name="Address" value="">
                          </tr>
                      </tbody>
                  </table>
              </div>
              <div class="modal-footer">
                   <button id = "editPatientSaveChanges" type="button" class="btn btn-primary">Save changes</button>
                   <p id="error_message"></p>
              </div>
            </div>
          </div>
        </div>
        
        <script type="text/javascript">
            $(document).ready(function() {
                $.ajax({
                    type: "POST",
                    url: "../PatientInformationServlet",
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#summaryPatientContent").html(data.summaryOutput);
                            $("#resultsDiv").show();
                        }
                    }
                });
            });
            
            function loadData() {
                $.ajax({
                    type: "POST",
                    url: "../PatientInformationServlet",
                    dataType: "JSON",
                    success: function (data) {
                        $("#summaryPatientContent").html(data.summaryOutput);
                        $("#resultsDiv").show();
                    }
                });
            }
            
            $("#editPatientSaveChanges").click(function() {
                Health_Card_Number = $("#Health_Card_Number").val();
                Sin_Number = $("#Sin_Number").val();
                Phone_Number = $("#Phone_Number").val();
                Address = $("#Address").val();
                p_id = $("#patient_id").val();
                console.log(p_id);
                
                $.ajax({
                    type: "POST",
                    url: "../EditPatientInformationServlet",
                    data: {
                        Health_Card_Number: Health_Card_Number,
                        Sin_Number: Sin_Number,
                        Phone_Number: Phone_Number,
                        Address: Address,
                        patient_id: p_id

                    },
                    dataType: "JSON",
                    success: function (data) {
                            console.log(data);
                            if (data.success === "true") {
                                $("#update_message").html("Update Successful.");
                                $("#update_message").addClass("alert alert-success message");
                                $("#editPatientModal").modal('toggle');
                                $("#Health_Card_Number").val("");
                                $("#Sin_Number").val("");
                                $("#Phone_Number").val("");
                                $("#Address").val("");
                                loadData();
                            }
                            else {
                                $("#error_message").addClass("alert alert-danger message");
                                $("#error_message").html("Update failed.");
                        }
                    }
                });
            });
            
            function openPatientModal(id) {
               $.ajax({
                   type: "POST",
                   url: "../ViewExistingPatientInformation",
                   data: {patient_id: id},
                   dataType: "JSON",
                   success: function(data) {
                       if (data.output !== null) {
                            $("#Health_Card_Number").val(data.output.Health_Card_Number);
                            $("#Sin_Number").val(data.output.Sin_Number);
                            $("#Phone_Number").val(data.output.Phone_Number);
                            $("#Address").val(data.output.Address);
                        }
                       else {
                       }
                    }
               });
               
                $("#editModal").html("Edit Patient " + id + " Details");
                $("#editPatientModal").modal({
                    show: true
                });
            }
      
        </script>
    </body>
</html>
