<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    User Name:&nbsp;<input type="text" name="username">
                </p>
                <p>
                    Password:&nbsp;<input type="password" name="password">
                </p>
                <c:if test="${not empty message}">
                    <p class="error-message">${message}</p>
                </c:if>
                <p>
                <input type="submit" value="Submit">
                </p>
            </div>
        </form>
        <p><a href="jsp/user_creation.jsp">User creation</a></p>
    </body>
</html>