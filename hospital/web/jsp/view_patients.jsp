<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>View your patients</title>
    </head>
    <body>

        Second panel
        <% String s = session.getAttribute("cpsonumber").toString(); %>
        <%=s%>

    </body>
</html>
