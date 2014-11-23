<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Visits</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestDoctorVisits" class="form-horizontal" role="form" method="POST">
            <input type ="hidden" name="usertype" value="<%=session.getAttribute("usertype").toString() %>" >
            <p>CPSO Number: <input type="text" name="cpso"><p>  
            <p>Start Date Time: <input type="text" name="start_range" id="start_range" value="" style="cursor: pointer;"><p>
            <p>End Date Time: <input type="text" name="end_range" id="end_range" value="" style="cursor: pointer;"><p>
            <div>
                <button id="CountDoctorVisits" type="submit" class="btn btn-default" >Submit</button>
            </div>
            <div id="creation_message"></div>
        </form>
        <script type="text/javascript">
            $("#ajaxRequestDoctorVisits").submit(function(e){
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
 
            $("#CountDoctorVisits").click(function(e) { 
                dataString = $("#ajaxRequestDoctorVisits").serialize();

                $.ajax({
                    type: "POST",
                    url: "../CountDoctorVisitsServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        $("#creation_message").html(data.output);
                        if (data.success === 'true') {
                            $("#creation_message").html(data.output);
                            console.log("success");
                        } else if (data.success === 'false') {
                            $("#creation_message").html(data.output);
                            console.log("false");
                        }
                    }
                });
            });
        </script>
    </body>
</html>
