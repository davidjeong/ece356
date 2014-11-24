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
            var cpso = "";
            $(document).ready(function() {
                cpso = untruncateCpso(${sessionScope.cpsonumber});
                
                $.ajax({
                    type: "GET",
                    url: "../DoctorStaffAssignmentServlet",
                    data: {
                        cpsonumber : cpso
                    },
                    dataType: "JSON",
                    success: function (data) {
                        console.log(data);
                        $("#staffTable").html(data.output);
                    }
                });
            });
            
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
                    url: "../DoctorStaffAssignmentServlet",
                    data: { staffs: staffs },
                    dataType: "JSON",
                    success: function (data) {
                        $("#doctorsTable").html(data.outputDoctor);
                    }
                });
            });
        </script>
        <div>
            <div id="staffTable" style="float: left; margin-right: 2%; width: 45%; overflow: auto">
            </div>
            <button id="submit" type="button" class="btn btn-primary refresh-button">Apply</button>
        </div>
    </body>
</html>
