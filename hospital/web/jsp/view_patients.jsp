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
        <div>
            <button id="refreshViewPatients" type="button" class="btn btn-primary refresh-button">Refresh Data</button>
            <div id="viewPatientContent"></div>
        </div>
        <script type="text/javascript">
            
            var cpso = "";
            $(document).ready(function() {
                cpso = untruncateCpso(${sessionScope.cpsonumber}.toString());
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
                dataString = "{\"cpsonumber\":\"\${sessionScope.cpsonumber}\"";
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
        </script>
    </body>
</html>
