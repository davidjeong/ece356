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
            <p>Before Date (Format: YYYY-MM-DD:HH:MM:SS): <input type="text" name="searchtime"><p> 
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
            
            $("#CountDoctorVisits").click(function(e) { 
                dataString = $("#ajaxRequestDoctorVisits").serialize();

                $.ajax({
                    type: "POST",
                    url: "../ViewPatientsServlet",
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
