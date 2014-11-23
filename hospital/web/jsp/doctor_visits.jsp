<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Visits</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestDoctorVisits" class="form-horizontal" role="form" method="POST">
            <p class="mandatory-message"><strong>* marks mandatory fields.</strong></p>
            <div class="form-group">
                <label for="requested_patient_id" class="col-sm-2 control-label">CPSO Number*</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="cpso" placeholder="CPSO Number">
                </div>
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
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="CountDoctorVisits" type="submit" class="btn btn-default" >Submit</button>
                </div>
            </div>
            <div class="summary-panel" id="summaryVisitsDiv">
                <p class="lead">
                    Summary of Doctor
                </p>
                <div id="summaryVisitsContent"></div>
            </div>
            <div class="summary-panel" id="detailVisitsDiv">
                <p class="lead">
                    Details of All Appointments
                </p>
                <div id="detailVisitsContent"></div>
            </div>
            <div id="failure_message"></div>
        </form>
        <script type="text/javascript">
            $("#ajaxRequestDoctorVisits").submit(function(e){
                console.log("stopping");
                e.preventDefault();
            });
            
            var startDateTextBox = $('#start_range');
            var endDateTextBox = $('#end_range');

            $.timepicker.datetimeRange(
                    startDateTextBox,
                    endDateTextBox,
                    {
                            minInterval: (1000*60*60), // 1hr
                            dateFormat: 'dd M yy', 
                            timeFormat: 'HH:mm',
                            start: {}, // start picker options
                            end: {} // end picker options					
                    }
            );
 
            $("#CountDoctorVisits").click(function(e) { 
                dataString = $("#ajaxRequestDoctorVisits").serialize();

                $.ajax({
                    type: "POST",
                    url: "../CountDoctorVisitsServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#failure_message").hide();
                            $("#summaryVisitsContent").html(data.summaryOutput);
                            $("#detailVisitsContent").html(data.allOutput);
                            $("#summaryVisitsDiv").show();
                            $("#detailVisitsDiv").show();
                        } else if (data.success === "false") {
                             $("#failure_message").show();
                             $("#summaryVisitsDiv").hide();
                             $("#detailVisitsDiv").hide();
                             $("#failure_message").html(data.output);
                             $("#failure_message").addClass("alert alert-danger message");
                        }
                    }
                });
            });
        </script>
    </body>
</html>
