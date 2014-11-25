<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login To MediCare</title>
    </head>
    <body>
        <div class="page-header">
            <div class="form-inline">
                <p id="login_error_message"></p>
                <p class="mandatory-message"><strong>* marks mandatory fields.</strong></p>
            </div>
        </div>
        <div class="clearfix">
            <form id="ajaxRequestForLoginInfo" class="form-horizontal" action="LoginServlet" method="POST">
                <div class="form-group">
                    <label for="login_username" class="col-sm-2 control-label">User Name*</label>
                    <div class="col-sm-10">
                        <input id="login_username" type="text" class="form-control" id="login_user_name" name="username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="login_password" class="col-sm-2 control-label">Password*</label>
                    <div class="col-sm-10">
                        <input id="login_username" type="text" class="form-control" id="login_password" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button id="loginButton" type="submit" class="btn btn-default">Login</button>
                    </div>
                </div>
                </div>
            </form>
        </div>
        <script type="text/javascript">
             $("#ajaxRequestForLoginInfo").submit(function(e){
                e.preventDefault();
            });

            $("#loginButton").click(function(e) { 
                dataString = $("#ajaxRequestForLoginInfo").serialize();

                $.ajax({
                    type: "POST",
                    url: "LoginServlet",
                    data: dataString,
                    dataType: "JSON",
                    success: function (data) {
                        if (data.success === 'true') {
                            window.location = data.output;
                        }
                        else {
                            $("#login_error_message").html(data.output);
                            $("#login_error_message").addClass("alert alert-danger message login-error");
                            $("#ajaxRequestForLoginInfo")[0].reset();
                        }
                    }
                });
            });
        </script>
    </body>
</html>
