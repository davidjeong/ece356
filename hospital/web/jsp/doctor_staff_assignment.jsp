<%-- 
    Document   : doctor_staff_assignment
    Created on : 24-Nov-2014, 1:08:35 AM
    Author     : okamayana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Staff</title>
    </head>
    <body>
        <script>
            
            function init() {
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
        </script>
        <div>
            <div id="staffTable">
            </div>
            <button id="submit" type="button" class="btn btn-primary refresh-button">Apply</button>
        </div>
        <p id="update_message" style="margin-top: 20px"></p>
    </body>
</html>
