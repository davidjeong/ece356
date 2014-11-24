<%-- 
    Document   : revenue
    Created on : Nov 23, 2014, 1:42:30 AM
    Author     : l22fu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Revenue</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestRevenue" class="form-horizontal" role="form" method="POST">
            <p class="mandatory-message"><strong>* marks mandatory fields.</strong></p>
            
            <div class="form-group">
                <label for="start_range" class="col-sm-3 control-label">Start Date*</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="start_range" id="start_range" value="" style="cursor: pointer;" placeholder="Empty Timestamp">
                </div>
            </div>
            <div class="form-group">
                <label for="end_range" class="col-sm-3 control-label">End Date*</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="end_range" id="end_range" value="" style="cursor: pointer;" placeholder="Empty Timestamp">
                </div>
            </div>  
            
            <div class="form-group">
                <label for="surgeryName" class="col-sm-3 control-label">Surgery*</label>
                <div class="col-sm-8" id="surgery_list">
                    
                </div>
            </div>
            
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="getRevenue" type="button" class="btn btn-primary" style="vertical-align: middle;">Submit</button>
                </div>
            </div>
        </form>
        
        <div class="summary-panel" id="summaryDiv">
            <p class="lead">
                Summary
            </p>
            <div id="summaryContent"></div>
        </div>
        
        <div class="summary-panel" id="visitsDiv">
            <p class="lead">
                Visits Summary
            </p>
            <div id="visitsContent"></div>
        </div>
        <p id="failure_message"></p>
        
        <script type="text/javascript">
            $("#ajaxRequestRevenue").submit(function(e){
                console.log("stopping");
                e.preventDefault();
            });
            
            var startDateTextBox = $('#start_range');
            var endDateTextBox = $('#end_range');

            $.timepicker.datetimeRange(
                    startDateTextBox,
                    endDateTextBox,
                    {
                            minInterval: (1000*60*60), // 1hr
                            dateFormat: 'dd M yy', 
                            timeFormat: 'HH:mm',
                            start: {}, // start picker options
                            end: {} // end picker options					
                    }
            );
 
            $(document).ready(function() {
                username = "${sessionScope.username}";
                dataString = "{ \"username\": \"" + username + "\" }";
                $.ajax({
                    type: "POST",
                    url: "../GetAllSurgeriesServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        
                        $("#surgery_list").html(data.surgeries);
                    }
                });
            });

             
              $("#getRevenue").click(function() { 

                dataString = $("#ajaxRequestRevenue").serialize();
                
                console.log(dataString);
                $.ajax({
                    type: "POST",
                    url: "../RevenueGeneratedServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#failure_message").hide();
                            $("#visitsContent").html(data.visitsOutput);
                            console.log(data.visitsOutput);
                            $("#visitsDiv").show();
                        } else if (data.success === "false") {
                             $("#failure_message").show();
                             $("#visitsDiv").hide();
                             $("#failure_message").html(data.output);
                             $("#failure_message").addClass("alert alert-danger message");
                        }
                    }
                });
            });
        </script>
    </body>
</html>
