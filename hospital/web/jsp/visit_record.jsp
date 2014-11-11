<%-- 
    Document   : visit_record
    Created on : Nov 10, 2014, 9:59:39 PM
    Author     : l22fu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <style type="text/css">
        .divTable
        {
            display:  table;
            margin-left: auto;
            margin-right: auto;
            width:auto;
            background-color:#eee;
            border-spacing:5px;/*cellspacing:poor IE support for  this*/
           /* border-collapse:separate;*/
           text-align: center;
        }

        .divRow
        {
           display:table-row;
           width:auto;
        }

        .divCell
        {
            float:left;/*fix for  buggy browsers*/
            display:table-column;
            width:200px;
            margin-left: 5px;
            background-color:#ccc;
        }
    </style>
    
    <head>
        <title>Visit Records</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form action="ViewRecordServlet" method="POST">
            <div class="divTable">
                <div class="headRow">
                    <div class="divCell">User ID</div>
                    <div  class="divCell">Name</div>
                    <div  class="divCell">Address</div>
                </div>
                <div class="divRow">    
                    <div class="divCell">001</div>
                    <div class="divCell">xxx</div>
                    <div class="divCell">111-ttt</div>
                </div>

            </div>
        </form>
    </body>
</html>
