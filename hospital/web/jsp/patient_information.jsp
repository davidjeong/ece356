<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Information</title>
    </head>
    <body>
        Missing Ability to input and change table below...
        <button id="UpdatePatientInformation" type="button" class="btn btn-primary refresh-button">Refresh Data</button>
        <div id="summaryVisitsContent"></div>
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
                        }
                    }
                });
            });
 
            $("#UpdatePatientInformation").click(function(e) { 
                dataString = $("#ajaxRequestPatientInformation").serialize();
                $.ajax({
                    type: "POST",
                    url: "../PatientInformationServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#summaryVisitsContent").html(data.summaryOutput);
                        }
                    }
                });
            });
        </script>
    </body>
</html>
