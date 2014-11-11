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
            width:auto;
            background-color:#eee;
            border:1px solid  #666666;
            border-spacing:5px;/*cellspacing:poor IE support for  this*/
           /* border-collapse:separate;*/
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
            background-color:#ccc;
        }
    </style>
    
    <head>
        <title>Visit Records</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="divTable">
            <div class="headRow">
                <div class="divCell" align="center">Customer ID</div>
                <div  class="divCell">Customer Name</div>
                <div  class="divCell">Customer Address</div>
            </div>
            <div class="divRow">
                <div class="divCell">001</div>
            </div>
            <div class="divRow">
                <div class="divCell">xxx</div>
            </div>
            <div class="divRow">
                <div class="divCell">111-ttt</div>
            </div>

        </div>
    </body>
</html>
