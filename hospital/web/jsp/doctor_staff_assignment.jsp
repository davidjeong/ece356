<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Staff</title>
    </head>
    <body>
        <div class="page-header refresh-header">
            <p id="update_message"></p>
            <div class="form-inline">
                <p class="mandatory-message"><strong>* Assign staff members to yourself</strong>
                <button id="submit" type="button" style="margin-left: 20px;"class="btn btn-warning">Apply Changes</button>
                <button id="refresh" type="button" style="margin-left: 10px;" class="btn btn-primary refresh-button">Refresh Data</button>
                </p>
            </div>
        </div>
        <div>
            <div id="staffTable">
            </div>
        </div>
        <script>
            
            function init() {;             
                cpso = untruncateCpso(${sessionScope.cpsonumber});
                
                $.ajax({
                    type: "POST",
                    url: "../DoctorListStaffAssignmentServlet",
                    data: {
                        cpsonumber : cpso
                    },
                    dataType: "JSON",
                    success: function (data) {
                        console.log(data);
                        $("#staffTable").html(data.output);
                        
                        
                        if (data.output === "There are no staff members registered in the database") {
                            document.getElementById("submit").disabled = true;
                        } else {
                            document.getElementById("submit").disabled = false;
                        }
                    }
                });
            }
            
            function onStaffClick(name) {
                var updateMessage = document.getElementById('update_message');
                updateMessage.style.visibility = 'hidden';
            }
            
            var cpso = "";
            $(document).ready(init);
            
            $("#submit").click(function() {
                var checkGroup = document.getElementsByName("staff[]");
                var length = checkGroup.length;
               
                var checkedStaff = [];
                for (var i = 0; i < length; i++) {
                    if (checkGroup[i].checked) {
                        checkedStaff.push(checkGroup[i].value);
                    }
                }
               
                var staffs = [];
                
                for (var s in checkedStaff) {
                    var item = checkedStaff[s];
                    console.log(item);
                    staffs.push(item);
                }
                                
                $.ajax({
                    type: "POST",
                    url: "../DoctorUpdateStaffAssignmentServlet",
                    data: { staffs: staffs },
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
            
            $("#refresh").click(function() {
                $("#update_message").removeAttr("class");
                $("#update_message").html("");
                init();
            });
        </script>
    </body>
</html>
