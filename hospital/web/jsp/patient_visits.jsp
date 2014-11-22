<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestPatientVisits" class="form-horizontal" role="form" method="POST">
            <input type ="hidden" name="usertype" value="<%=session.getAttribute("usertype").toString() %>" >
            <p>Before Date (Format: YYYY-MM-DD:HH:MM:SS): <input type="text" name="searchtime"><p> 
            <div>
                <button id="CountPatientVisits" type="submit" class="btn btn-default" >Submit</button>
            </div>
            <div id="content_div"></div>
        </form>
        <script type="text/javascript">
            
        </script>
    </body>
</html>
