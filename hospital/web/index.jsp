<!DOCTYPE html>
<html>
    <head>
        <title>MediCare Log In</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    </head>
    <body>
        <form id="login_info" action="LoginServlet" method="POST">
            <div id="login">
                <div class="jumbotron">
                    <div class="bg"></div>
                    <div class="container">
                        <h1>Welcome to MediCare</h1>
                        <p>An all-in-one health care management tool</p>
                        <p>
                            User Name:&nbsp;<input id="login_username" type="text" name="username">
                        </p>
                        <p>
                            Password:&nbsp;<input id="login_password" type="password" name="password">
                        </p>
                        <c:if test="{$not empty message}">
                            ${message}
                        </c:if>
                        <p>
                            <button type="submit" class="btn btn-default" type="submit">Login</button>
                        </p>
                    </div>
                </div>
            </div>
        </form>
        <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script> 
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
    </body>
</html>