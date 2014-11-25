<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Information</title>
    </head>
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
                          <th>Default Doctor</th>
                          <th>Patient Id</th>
                          <th>Health Card Number</th>
                          <th>Health Status</th>
                          <th>Phone Number</th>
                          <th>Address</th>
                      </tr>
                      </thead>
                      <tbody>
                          <tr>
                              <td><input id="Default_Doctor" type="text" class="form-control" name="Default_Doctor" value=""></td>
                              <td><input id="Patient_Id" type="text" class="form-control" name="Patient_Id"></td>
                              <td><input id="Health_Card_Number" type="text" class="form-control" name="Health_Card_Number" value=""></td>
                              <td><input id="Health_Status" type="text" class="form-control" name="Health_Status"></td>
                              <td><input id="Phone_Number" type="text" class="form-control" name="Phone_Number" value=""></td>
                              <td><input id="Address" type="text" class="form-control" name="Address" value="">
                          </tr>
                      </tbody>
                  </table>
              </div>
              <div class="modal-footer">
                   <button id = "staffEditPatientSaveChanges" type="button" class="btn btn-primary">Save changes</button>
                   <p id="error_message"></p>
              </div>
            </div>
          </div>
        </div>
        
        <script type="text/javascript">
            $(document).ready(function() {
                loadData();
            });
            
            function loadData() {
                $.ajax({
                    type: "POST",
                    url: "../StaffViewPatientInformationServlet",
                    dataType: "JSON",
                    success: function (data) {
                        $("#summaryPatientContent").html(data.summaryOutput);
                        $("#resultsDiv").show();
                    }
                });
            }
            
            $("#staffEditPatientSaveChanges").click(function() {
                Default_Doctor = $("#Default_Doctor").val();
                p_id = $("#Patient_Id").val();
                Health_Card_Number = $("#Health_Card_Number").val();
                Health_Status = $("#Health_Status").val();
                Phone_Number = $("#Phone_Number").val();
                Address = $("#Address").val();
                console.log(p_id);
                
                $.ajax({
                    type: "POST",
                    url: "../StaffEditPatientInformationServlet",
                    data: {
                        Default_Doctor: Default_Doctor,
                        Patient_Id: p_id,
                        Health_Card_Number: Health_Card_Number,
                        Health_Status: Health_Status,
                        Phone_Number: Phone_Number,
                        Address: Address
                    },
                    dataType: "JSON",
                    success: function (data) {
 
                            console.log(data);
                            if (data.success === "true") {
                                $("#update_message").html("Update Successful.");
                                $("#update_message").addClass("alert alert-success message");
                                $("#editPatientModal").modal('toggle');
                                $("#Default_Doctor").val("");
                                $("#Patient_Id").val("");
                                $("#Health_Card_Number").val("");
                                $("#Health_Status").val("");
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
                   url: "../StaffViewExistingPatientInformation",
                   data: {patient_id: id},
                   dataType: "JSON",
                   success: function(data) {
                       if (data.output !== null) {
                            $("#Default_Doctor").val(data.output.Default_Doctor);
                            $("#Patient_Id").val(data.output.Patient_Id);
                            $("#Health_Card_Number").val(data.output.Health_Card_Number);
                            $("#Health_Status").val(data.output.Health_Status);
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
