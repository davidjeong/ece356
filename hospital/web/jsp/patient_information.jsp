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
        <form name="input" id="ajaxRequestDoctorVisits" class="form-horizontal" role="form" method="POST">

         <div class="col-sm-offset-2 col-sm-10">
            <button id="UpdatePatientInformation" type="submit" class="btn btn-default" >Submit</button>
        </div>
            <div class="summary-panel" id="resultsDiv">
                 <p class="lead">
                   Details of Personal Information of Patient
                 </p>
            <div id="summaryVisitsContent"></div>
            </div>
        </form>
        <script type="text/javascript">
            $(document).ready(function() {
                $.ajax({
                    type: "POST",
                    url: "../PatientInformationServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#summaryVisitsContent").html(data.summaryOutput);
                            $("#resultsDiv").show();
                        }
                    }
                });
            });
 
            $("#UpdatePatientInformation").submit(function(e) { 
                dataString = $("#ajaxRequestPatientInformation").serialize();
                $.ajax({
                    type: "POST",
                    url: "../PatientInformationServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#summaryVisitsContent").html(data.summaryOutput);
                            $("#resultsDiv").show();
                        }
                    }
                });
            });
        </script>
    </body>
</html>
