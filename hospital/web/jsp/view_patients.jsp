<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View your patients</title>
    </head>
    <body>

        Second panel
        <% String s = session.getAttribute("cpsonumber").toString(); %>
        <%=s%>

    </body>
</html>
