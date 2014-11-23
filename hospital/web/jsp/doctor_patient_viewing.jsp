<%@page import="org.hospital.entities.Patient"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Patients' Viewing Rights</title>
    </head>
    <body>
        <script type="text/javascript">
            var cpso = "";
            $(document).ready(function() {
                cpso = untruncateCpso(${sessionScope.cpsonumber});
                dataString = " {\"cpsonumber\":\"" + cpso + "\"}";
                
                $.ajax({
                    type: "GET",
                    url: "../DoctorPatientViewingServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#patientsTable").html(data.outputPatient);
                        $("#doctorsTable").html(data.outputDoctor);
                    }
                });
            });
        </script>
        <p>Grant/Revoke other doctors viewing rights to your patients</p>
        <div>
            <div id="patientsTable" style="float: left; margin-right: 10%; width: 45%">
            </div>
            <div id="doctorsTable" style="float: left; width: 45%">
            </div>
        </div>
    </body>
</html>
