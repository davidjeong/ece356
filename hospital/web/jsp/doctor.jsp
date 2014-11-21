<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/styles.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="../js/jquery-1.11.1.min.js"></script>
        <title>Welcome</title>
    </head>
    <body>
        <div class="top-panel">
            <div class="name">
            Hello Doctor <%=session.getAttribute("legalname")%>
        </div>
        <div class="left-panel">
            Functions
            <ul></ul>
        </div>
        <div class="right-panel">
            Second panel
        </div>
    </body>
</html>
