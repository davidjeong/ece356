<%-- 
    Document   : revenue
    Created on : Nov 23, 2014, 1:42:30 AM
    Author     : l22fu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hospital Revenue</title>
    </head>
    <body>
        <form name="input" id="ajaxRequestPatientVisits" class="form-horizontal" role="form" method="POST">
            <p class="mandatory-message"><strong>* marks mandatory fields.</strong></p>
            
            <div class="form-group">
                <label for="start_range" class="col-sm-3 control-label">Start Date*</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="start_range" id="start_range" value="" style="cursor: pointer;" placeholder="Empty Timestamp">
                </div>
            </div>
            <div class="form-group">
                <label for="end_range" class="col-sm-3 control-label">End Date*</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="end_range" id="end_range" value="" style="cursor: pointer;" placeholder="Empty Timestamp">
                </div>
            </div>
            <div class="form-group">
                <label for="end_range" class="col-sm-3 control-label" style="margin-right: 15px;">Revenue Stream*</label>
                <div class="btn-group btn-input clearfix">
                
                    <button type="button" class="btn btn-default dropdown-toggle form-control" data-toggle="dropdown">
                    <span data-bind="label">Select One</span> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Item 1</a></li>
                        <li><a href="#">Another item</a></li>
                        <li><a href="#">This is a longer item that will not fit properly</a></li>
                    </ul>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-primary" style="vertical-align: middle;">Submit</button>
                </div>
            </div>
            
            
        </form>
        
        <script type="text/javascript">
            $("#ajaxRequestPatientVisits").submit(function(e){
                console.log("stopping");
                e.preventDefault();
            });
            
            var startDateTextBox = $('#start_range');
            var endDateTextBox = $('#end_range');

            $.timepicker.datetimeRange(
                    startDateTextBox,
                    endDateTextBox,
                    {
                            minInterval: (1000*60*60), // 1hr
                            dateFormat: 'dd M yy', 
                            timeFormat: 'HH:mm',
                            start: {}, // start picker options
                            end: {} // end picker options					
                    }
            );
 
            $( document.body ).on( 'click', '.dropdown-menu li', function( event ) {
 
                var $target = $( event.currentTarget );

                $target.closest( '.btn-group' )
                   .find( '[data-bind="label"]' ).text( $target.text() )
                      .end()
                   .children( '.dropdown-toggle' ).dropdown( 'toggle' );

                return false;

             });
        </script>
    </body>
</html>
