<!DOCTYPE html>
<html>
    <head>
        <title>Welcome to MediCare. Please log in.</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
    </head>
    <body>
        <form action="LoginServlet" method="POST">
            <div style="padding-left: 30px;" id="login">
                <h1>Welcome to MediCare</h1>
                <p>
                    User Name:&nbsp;<input id="login_username" type="text" name="username">
                </p>
                <p>
                    Password:&nbsp;<input id="login_password" type="password" name="password">
                </p>
                <c:if test="${not empty message}">
                    <p class="error-message">${message}</p>
                </c:if>
                <p>
                    <input id="login_submit" type="submit" value="Submit">
                </p>
            </div>
        </form>
        <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    </body>
</html>