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
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
        <div>
            <button id="refreshViewPatients" type="button" class="btn btn-primary refresh-button">Refresh Data</button>
            <div id="viewPatientContent"></div>
        </div>
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
                        } else if (data.success === 'false') {
                            console.log("failure");
                        }
                        console.log(data.output);
                    }
                });
            });

            $("#refreshViewPatients").click(function(e) {
                dataString = "{\"cpsonumber\":\"" + cpso + "\"}";
                console.log(dataString);
                $.ajax({
                    type: "POST",
                    url: "../ViewPatientsServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#creation_message").html(data.output);
                        if (data.success === 'true') {
                            $("#viewPatientContent").html(data.output);
                        } else if (data.success === 'false') {
                            console.log("failure");
                        }
                    }
                });
            });
            
            
            function openModal(id) {
                console.log(dataString);
                $.ajax({
                    type: "POST",
                    url: "../ViewPatientVisitRecordsServlet",
                    data: {patient_id: id},
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === 'true') {
                            $("#modalBody").html(data.output);
                        } else if (data.success === 'false') {
                            console.log("failure");
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
