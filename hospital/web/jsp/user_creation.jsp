<%-- 
    Document   : CreateUser
    Created on : Nov 10, 2014, 11:03:27 PM
    Author     : AnthonyVAIO
--%>

<html>
    <head>
        <title>User Creation Page</title>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h2>Please enter the following to create a new user:</h2>
        <form name="input" action="UserCreationServlet" method="get">
            <p>Legal Name: <input type="text" name="legalName"><p> 
            <p>Username: <input type="text" name="username"><p>
            <p>Password: <input type="text" name="password"><p>
            <p>User type: <input type = "text" name="userType">
            <input type="submit" value="Submit">
            </div>
        </form>
    </body>
</html>