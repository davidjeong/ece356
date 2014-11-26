<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify Patients' Viewing Rights</title>
    </head>
    <body>
        <div class="page-header refresh-header">
            <p id="update_message"></p>
            <div class="form-inline">
                <p class="mandatory-message" style="text-align: left;"><strong>* Select a patient from the left table, and select doctors to grant/revoke them viewing permissions. Apply changes per patient.</strong>
                <button id="submit" type="button" style="margin-right: 10px; margin-left: 10px;"class="btn btn-warning">Apply Changes</button>
                <button id="refresh" type="button" class="btn btn-primary refresh-button">Refresh Data</button>
                </p>
            </div>
        </div>
        <div>
            <div id="patientsTable" style="float: left; margin-right: 10%; width: 45%; overflow: auto">
            </div>
            <div id="doctorsTable" style="float: left; width: 45%; overflow: auto">
            </div>

            <script type="text/javascript">
                function init() {
                    cpso = untruncateCpso(${sessionScope.cpsonumber});
                
                    $.ajax({
                        type: "POST",
                        url: "../DoctorPatientViewingOnLoadServlet",
                        data: {
                            cpsonumber : cpso
                        },
                        dataType: "JSON",
                        success: function (data) {
                            console.log(data);
                            $("#patientsTable").html(data.outputPatient);
                            $("#doctorsTable").html(data.outputDoctor);
                            
                            if (data.outputPatient === "You have no patients assigned to you") {
                                document.getElementById("submit").disabled = true;
                            } else {
                                document.getElementById("submit").disabled = false;
                            }   
                        }
                    });
                }
                
                var cpso = "";
                $(document).ready(init);
            
                function onPatientClick(id) {
                    var updateMessage = document.getElementById('update_message');
                    updateMessage.style.visibility = 'hidden';
                    
                    console.log("onPatientClick " + id);
                    $.ajax({
                        type: "POST",
                        url: "../DoctorPatientViewingOnPatientSelectServlet",
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
                    var updateMessage = document.getElementById('update_message');
                    updateMessage.style.visibility = 'hidden';
                    
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
                        if (patients[i].checked===true) {
                            return patients[i].value;
                        }
                    }
                }
                
                $("#refresh").click(function() {
                    $("#update_message").removeAttr("class");
                    $("#update_message").html("");
                    init();
                });
            
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
                        url: "../DoctorPatientViewingUpdateServlet",
                        data: {patientId: patientId, doctors: doctors},
                        dataType: "JSON",
                        success: function (data) {
                            var updateMessage = document.getElementById('update_message');
                            updateMessage.style.visibility = 'visible';
                        
                            $("#update_message").html(data.output);
                            if (data.success === 'true') {
                                $("#update_message").removeClass();
                                $("#update_message").addClass("alert alert-success message");
                                init();
                            } else if (data.success === 'false') {
                                $("#update_message").removeClass();
                                $("#update_message").addClass("alert alert-danger message");
                            }                        
                        }
                    });
                });
            </script>
        </div>
    </body>
</html>
