<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update My Information</title>
    </head>
    <body>
        <div class="clearfix">
            <div class="form-inline">
                <p id="creation_message"></p>
            </div>
            <div class="lead">My Personal Information</div>
             <form name="input" id="ajaxRequestForPatientUpdate" class="form-horizontal" role="form" method="POST">
                <div id="summaryPatientContent">
                    <div class="form-group">
                       <label for="phone_number" class="col-sm-2 control-label">Phone Number</label>
                        <div class="col-sm-10">
                            <input name="phone_number" type="text" class="form-control" id="phone_number" placeholder="Phone Number">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="col-sm-2 control-label">Address</label>
                        <div class="col-sm-10">
                            <input name="address" type="text" class="form-control" id="address" placeholder="Address">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button id="applyBtn" type="submit" class="btn btn-warning" disabled>Apply Changes</button>
                        </div>
                    </div>
                </div>
             </form>
        </div>
        
        <script type="text/javascript">
            
            $("#ajaxRequestForPatientUpdate").submit(function(e){
                e.preventDefault();
            });
            
            $(document).ready(function() {
                loadData();
            });
            
            function loadData() {
                $.ajax({
                    type: "POST",
                    url: "../PatientInformationServlet",
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#phone_number").val(data.phone_number);
                            $("#address").val(data.address);
                            $("#applyBtn").removeAttr("disabled");
                        }
                    }
                });
            }
            
            $("#applyBtn").click(function() {
                
                dataString = $("#ajaxRequestForPatientUpdate").serialize();
                
                $.ajax({
                    type: "POST",
                    url: "../UpdatePersonalInformationServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                            console.log(data);
                            if (data.count !== "0") {
                                $("#creation_message").html("Saved succesfully.");
                                $("#creation_message").removeClass();
                                $("#creation_message").addClass("alert alert-success message");
                            } else {
                                $("#creation_message").html("Failed to apply changes.");
                                $("#creation_message").removeClass();
                                $("#creation_message").addClass("alert alert-danger message");
                            }
                    }
                });
            });
            
        </script>
    </body>
</html>
