<!DOCTYPE html>
<html>
    <head>
        <title>User Creation Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="../js/jquery-1.11.1.min.js"></script>
    </head>
    <body>
        <h2>Please enter the following to create a new user:</h2>
        <form name="input" action="UserCreationServlet" method="post">
            <p>User type: <select name="userType" id="userType">
                    <option value="select">&lt;Select a user type&gt;</option>
                    <option value="patient">Patient</option>
                    <option value="doctor">Doctor</option>
                    <option value="staff">Staff</option>
                    <option value="legal">Legal</option>
                    <option value="finance">Finance</option>
                </select>
            </p>
            <p>Legal name: <input type="text" name="legal_name"><p> 
            <p>Username: <input type="text" name="username"><p>
            <p>Password: <input type="text" name="password"><p>
            <div id="doctor_input">
                <p>CPSO Number: <input type="text" name="cpso"></p>
                <p>Department: <input type="text" name="department"></p>
            </div>
            <div id="patient_input">
                <p>Default doctor: <input type="text" name="default_doctor"></p>
                <p>Health status: <input type="text" name="health_status"></p>
                <p>Health card number: <input type="text" name="health_card_number"></p>
                <p>SIN number: <input type="text" name="sin_number"></p>
                <p>Phone number: <input type="text" name="phone_number"></p>
                <p>Address: <input type="text" name="address"></p>
            </div>
            <br/>
            <input type="submit" value="Submit">
        </form>
        <script type='text/javascript'>//<![CDATA[
            
            hideAllDivs = function () {
                $("#doctor_input").hide();
                $("#patient_input").hide();
            };
        
            handleNewSelection = function () {
                hideAllDivs();
            
                switch ($(this).val()) {
                    case 'doctor':
                        $("#doctor_input").show();
                        break;
                    case 'patient':
                        $("#patient_input").show();
                        break;
                }
            };
        
            $(document).ready(function() {
                $("#userType").change(handleNewSelection);
                handleNewSelection.apply($("#userType"));
            });
            //]]>
        </script>
    </body>
</html>