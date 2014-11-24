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
                
                $.ajax({
                    type: "GET",
                    url: "../DoctorPatientViewingServlet",
                    data: {
                        cpsonumber : cpso
                    },
                    dataType: "JSON",
                    success: function (data) {
                        console.log(data);
                        $("#patientsTable").html(data.outputPatient);
                        $("#doctorsTable").html(data.outputDoctor);
                    }
                });
            });
            
            function onPatientClick(id) {   
                console.log("onPatientClick " + id);
                $.ajax({
                    type: "GET",
                    url: "../DoctorPatientViewingServlet",
                    data: {
                        patient_id: id
                    },
                    dataType: "JSON",
                    success: function (data) {
                        $("#doctorsTable").html(data.outputDoctor);
                    }
                });
            }
            
            function onDoctorClick(checkbox, id) {   
                console.log("onDoctorClick " + id + " " + checkbox.checked);
                if (checkbox.checked) {
                    checkbox.check = checkbox.value;
                } else {
                    checkbox.check = "";
                }
            }
            
            function getSelectedPatient() {
                var patients = document.getElementsByName("patients[]");
                var sizes = patients.length;
                for (i=0; i < sizes; i++) {
                    if (patients[i].checked==true) {
                        return patients[i].value;
                    }
                }
            }
            
            $("#submit").click(function() {
                var checkGroup = document.getElementsByName("doctors[]");
                var length = checkGroup.length;
                var patientId = getSelectedPatient();
               
                var checkedDoctors = [];
                for (var i = 0; i < length; i++) {
                    if (checkGroup[i].checked) {
                        checkedDoctors.push(checkGroup[i].value);
                    }
                }
               
                var doctors = [];
                
                for (var d in checkedDoctors) {
                    var item = checkedDoctors[d];
                    console.log(item);
                    doctors.push(item);
                }
                                
                $.ajax({
                    type: "POST",
                    url: "../DoctorPatientViewingServlet",
                    data: {patientId: patientId, doctors: doctors},
                    dataType: "JSON",
                    success: function (data) {
                        $("#doctorsTable").html(data.outputDoctor);
                    }
                });
            });
        </script>
        <div>
            <div id="patientsTable" style="float: left; margin-right: 10%; width: 45%; overflow: auto">
            </div>
            <div id="doctorsTable" style="float: left; width: 45%; overflow: auto">
            </div>
            <button id="submit" type="button" class="btn btn-primary refresh-button">Apply</button>
        </div>
    </body>
</html>
