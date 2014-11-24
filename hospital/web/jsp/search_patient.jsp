<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search For Patient</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestPatients" class="form-horizontal" role="form" method="POST">
            <div class="form-group">
                <label for="last_visit_date" class="col-sm-2 control-label" style="margin-right: 5px;">Last Visit Date</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="last_visit_date" id="datepicker" value="" style="cursor: pointer;" placeholder="Empty Timestamp">
                </div>
            </div>  

            <div class="form-group">
                <label for="legal_name" class="col-sm-2 control-label" style="margin-right: 5px;">Legal Name</label>
                <div class="col-sm-8">
                    <input name="legal_name" type="text" class="form-control" id="legal_name" placeholder="Legal Name">
                 </div>
            </div>

            <div class="form-group">
                <label for="patient_id" class="col-sm-2 control-label" style="margin-right: 5px;">Patient ID</label>
                <div class="col-sm-8">
                    <input name="patient_id" type="text" class="form-control" id="patient_id" placeholder="ID of patient">
                 </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="getPatients" type="button" class="btn btn-primary" style="vertical-align: middle;">Submit</button>
                </div>
            </div>
        </form>

        <div id="result"></div>
        <p id="failure_message"></p>
        <script type="text/javascript">
            $("#ajaxRequestPatients").submit(function(e){
                console.log("stopping");
                e.preventDefault();
            });

            $("#getPatients").click(function() { 

                dataString = $("#ajaxRequestPatients").serialize();
                console.log(dataString);
                $.ajax({
                    type: "POST",
                    url: "../searchPatientServlet",
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
