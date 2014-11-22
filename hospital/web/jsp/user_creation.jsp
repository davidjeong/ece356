<%@page import="java.util.List"%>
<%@page import="org.hospital.entities.Doctor"%>
<!DOCTYPE html>
<html>
    <head>
        <title>User Creation Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <jsp:include page="/UserCreationServlet" />
        <% List<Doctor> doctorList = (List<Doctor>)session.getAttribute("allDoctorList"); %>
        <div class="dropdown clearfix">
            <p>Type of User</p>
            <button class="btn btn-default dropdown-toggle" type="button" id="userTypeSelector" data-toggle="dropdown">Types&nbsp;<span class="caret"></span></button>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu2">
                <li role="presentation"><a role="menuitem" tabindex="0" value="doctor" href="#" onclick="showDoctorDiv();">Doctor</a></li>
                <li role="presentation"><a role="menuitem" tabindex="1" value="patient" href="#" onclick="showPatientDiv();">Patient</a></li>
                <li role="presentation"><a role="menuitem" tabindex="2" value="staff" href="#" onclick="showStaffDiv();">Staff</a></li>
                <li role="presentation"><a role="menuitem" tabindex="3" value="finance" href="#" onclick="showFinanceDiv();">Finance</a></li>
                <li role="presentation"><a role="menuitem" tabindex="4" value="legal" href="#" onclick="showLegalDiv();">Legal</a></li>
            </ul>
        </div>
        <form name="input" action="UserCreationServlet" class="form-horizontal" role="form" method="POST">
            <input id="usertype" type="hidden" name="usertype" value="">
            <p class="mandatory-message"><strong>* marks mandatory fields.</strong></p>
            <div class="form-group">
                <label for="legal_name" class="col-sm-2 control-label">Legal Name*</label>
                <div class="col-sm-10">
                    <input name="legal_name" type="text" class="form-control" id="legal_name" placeholder="Legal Name">
                 </div>
            </div>
            <div class="form-group">
                <label for="username" class="col-sm-2 control-label">User Name*</label>
                <div class="col-sm-10">
                    <input name="username" type="text" class="form-control" id="username" placeholder="User Name">
                 </div>
            </div>
            <div class="form-group">
                <label for="password" class="col-sm-2 control-label">Password*</label>
                <div class="col-sm-10">
                    <input name="password" type="password" class="form-control" id="password" placeholder="Password">
                 </div>
            </div>
            <div class="form-group">
                <label for="confirm_password" class="col-sm-2 control-label">Confirm Password*</label>
                <div class="col-sm-10">
                    <input name="confirm_password" type="password" class="form-control" id=confirm_password" placeholder="Re-type Password">
                </div>
            </div>
            <div class="form-group doctor-field">
                <label for="cpso" class="col-sm-2 control-label">CPSO Number*</label>
                <div class="col-sm-10">
                    <input name="cpso" type="text" class="form-control" id="cpso" placeholder="CPSO Number">
                </div>
            </div>
            <div class="form-group doctor-field">
                <label for="department" class="col-sm-2 control-label">Department</label>
                <div class="col-sm-10">
                    <input name="department" type="text" class="form-control" id="department" placeholder="Department">
                </div>
            </div>
            <div class="form-group patient-field">
                <label for="health_card_number" class="col-sm-2 control-label">Health Card Number*</label>
                <div class="col-sm-10">
                    <input name="health_card_number" type="text" class="form-control" id="health_card_number" placeholder="Health Card Number">
                </div>
            </div>
            <div class="form-group patient-field">
                <label for="default_doctor" class="col-sm-2 control-label">Default Doctor</label>
                <div class="col-sm-10">
                    <button class="btn btn-default dropdown-toggle" type="button" id="default_doctor_dropdown" data-toggle="dropdown">Doctors&nbsp;<span class="caret"></span></button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <% int i = 0;
                        while (i<doctorList.size()) {
                            Doctor doctor = doctorList.get(i);
                            String legalName = doctor.getLegalName();
                            String cpsoNumber = doctor.getCpsoNumber();
                        %>
                        <li value="<%=cpsoNumber%> role="presentation"><a role="menuitem" tabindex="<%=i%>" href="#" onclick="changeDefaultDoctor(this);"><%=legalName%></a></li>
                        <% i++; } %>
                    </ul>
                    <input name="default_doctor" type="hidden" id="default_doctor" value="">
                </div>
            </div>
            <div class="form-group patient-field">
                <label for="health_status" class="col-sm-2 control-label">Health Status</label>
                <div class="col-sm-10">
                    <input name="health_status" type="text" class="form-control" id="health_status" placeholder="Health Status">
                </div>
            </div>            
            <div class="form-group patient-field">
                <label for="sin_number" class="col-sm-2 control-label">SIN Number</label>
                <div class="col-sm-10">
                    <input name="sin_number" type="text" class="form-control" id="sin_number" placeholder="SIN Number">
                </div>
            </div>
            <div class="form-group patient-field">
                <label for="phone_number" class="col-sm-2 control-label">Phone Number</label>
                <div class="col-sm-10">
                    <input name="phone_number" type="text" class="form-control" id="phone_number" placeholder="Phone Number">
                </div>
            </div>
            <div class="form-group patient-field">
                <label for="sin_number" class="col-sm-2 control-label">Address</label>
                <div class="col-sm-10">
                    <input name="address" type="text" class="form-control" id="address" placeholder="Address">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="create_submit" type="submit" onclick="submitNewUser();" class="btn btn-default" disabled>Create User</button>
                </div>
            </div>
        </form>
    </body>
</html>