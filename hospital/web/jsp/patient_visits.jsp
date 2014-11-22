<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestPatientVisits" class="form-horizontal" role="form" method="POST">
            <input type ="hidden" name="usertype" value="<%=session.getAttribute("usertype").toString() %>" >
            <p>Before Date (Format: YYYY-MM-DD:HH:MM:SS): <input type="text" name="searchtime"><p> 
            <div>
                <button id="CountPatientVisits" type="submit" class="btn btn-default" >Submit</button>
            </div>
            <div id="creation_message"></div>
        </form>
        <script type="text/javascript">
            $("#ajaxRequestPatientVisits").submit(function(e){
                console.log("stopping");
                e.preventDefault();
            });

            $("#CountPatientVisits").click(function(e) { 
                dataString = $("#ajaxRequestPatientVisits").serialize();
                console.log("lol");
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
