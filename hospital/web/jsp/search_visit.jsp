<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Visit Records</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestVisits" class="form-horizontal" role="form" method="POST">
            <div class="form-group">
                <label for="visit_date" class="col-sm-2 control-label" style="margin-right: 5px;">Visit Date</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="visit_date" id="datepicker" value="" style="cursor: pointer;" placeholder="Empty Timestamp">
                </div>
            </div>  

            <div class="form-group">
                <label for="legal_name" class="col-sm-2 control-label" style="margin-right: 5px;">Legal Name</label>
                <div class="col-sm-8">
                    <input name="legal_name" type="text" class="form-control" id="legal_name" placeholder="Legal Name">
                 </div>
            </div>

            <div class="form-group">
                <label for="diagnosis" class="col-sm-2 control-label" style="margin-right: 5px;">Keyword in Diagnosis</label>
                <div class="col-sm-8">
                    <input name="diagnosis" type="text" class="form-control" id="diagnosis" placeholder="Diagnosis">
                 </div>
            </div>
            
            <div class="form-group">
                <label for="comments" class="col-sm-2 control-label" style="margin-right: 5px;">Keyword in Comments</label>
                <div class="col-sm-8">
                    <input name="comments" type="text" class="form-control" id="comments" placeholder="Comments">
                 </div>
            </div>
            
            <div class="form-group">
                <label for="prescription" class="col-sm-2 control-label" style="margin-right: 5px;">Prescription Given</label>
                <div class="col-sm-8">
                    <input name="prescription" type="text" class="form-control" id="prescription" placeholder="Prescription">
                 </div>
            </div>
            
            <div class="form-group">
                <label for="surgeryName" class="col-sm-2 control-label">Surgery </label>
                <div class="col-sm-8" id="surgery_list">
                    
                </div>
            </div>
            

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="getVisits" type="button" class="btn btn-primary" style="vertical-align: middle;">Submit</button>
                </div>
            </div>
        </form>

        <div id="result"></div>
        <p id="failure_message"></p>
        <script type="text/javascript">
            $("#ajaxRequestVisits").submit(function(e){
                console.log("stopping");
                e.preventDefault();
            });
            
            $(document).ready(function() {
                $.ajax({
                    type: "POST",
                    url: "../GetAllSurgeriesServlet",
                    dataType: "JSON",
                    success: function (data) {
                        
                        $("#surgery_list").html(data.surgeries);
                    }
                });
            });

            $("#getVisits").click(function() { 

                dataString = $("#ajaxRequestVisits").serialize();
                console.log(dataString);
                $.ajax({
                    type: "POST",
                    url: "../SearchVisitRecordsServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === "true") {
                            $("#failure_message").hide();
                            $("#result").html(data.output);
                            $("#result").show();
                        } else if (data.success === "false") {
                             $("#failure_message").show();
                             $("#result").hide();
                             $("#failure_message").html(data.output);
                             $("#failure_message").addClass("alert alert-danger message");
                        }
                    }
                });
            });
        $(function() {
          $( "#datepicker" ).datepicker();
        });
        </script>
    </body>
</html>
