<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Welcome to Medicare. Please log in</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <form action="LoginServlet" method="POST">
            <div id="header">
                <h1>Login Page</h1>
                <p>
                    User Name:&nbsp;<input type="text" name="username">
                </p>
                <p>
                    Password:&nbsp;<input type="password" name="password">
                </p>
                <p>
                <input type="submit" value="Submit">
                </p>
            </div>
        </form>
        <p><a href="jsp/user_creation.jsp">User creation</a></p>
    </body>
</html>