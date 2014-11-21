<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="../js/jquery-1.11.1.min.js"></script>
        <title>Welcome <%=session.getAttribute("legalname")%></title>
    </head>
    <body>
        <div id="page">
            <div id="maincontent">
                <div id="firstcontent">firstcontent</div>
                <div id="secondcontent">secondcontent</div>
            </div>
            <div id="menuleftcontent">
                <ul id="menu">
                    <li><a href="#firstcontent">first</a></li>
                    <li><a href="#secondcontent">second</a></li>
                </ul>
            </div>
        <div id="clearingdiv"></div>
        </div>
    </body>
</html>
