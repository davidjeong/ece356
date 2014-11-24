<%@page import="org.hospital.entities.Patient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Patients</title>
    </head>
    <body>
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
              </div>
            </div>
          </div>
        </div>
        <div class="page-header refresh-header">
            <div class="form-inline">
                <button id="refreshViewPatients" type="button" class="btn btn-primary">Refresh Data</button>
            </div>
        </div>
        <div id="viewPatientContent"></div>
        <script type="text/javascript">
            
            var cpso = "";
            $(document).ready(function() {
                cpso = untruncateCpso(${sessionScope.cpsonumber});
                dataString = " {\"cpsonumber\":\"" + cpso + "\"}";
                
                $.ajax({
                    type: "POST",
                    url: "../ViewPatientsServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#creation_message").html(data.output);
                        if (data.success === 'true') {
                            $("#viewPatientContent").html(data.output);
                        } 
                    }
                });
            });

            $("#refreshViewPatients").click(function(e) {
                dataString = "{\"cpsonumber\":\"" + cpso + "\"}";
                $.ajax({
                    type: "POST",
                    url: "../ViewPatientsServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#creation_message").html(data.output);
                        if (data.success === 'true') {
                            $("#viewPatientContent").html(data.output);
                        }
                    }
                });
            });
            
            
            function openModal(id) {
                $.ajax({
                    type: "POST",
                    url: "../ViewPatientVisitRecordsServlet",
                    data: {patient_id: id},
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === 'true') {
                            $("#modalBody").html(data.output);
                            $("#myModalLabel").html("Visit Records for Patient "+id)
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
