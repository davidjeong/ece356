<%-- 
    Document   : login
    Created on : Nov 10, 2014, 9:01:47 PM
    Author     : jeong_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <p><a href="user_creation.jsp">User creation</a></p>
    </body>
</html>