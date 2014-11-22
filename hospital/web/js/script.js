function load_visits() {
    $("#content-panel").load("jsp/visit_record.jsp");
}
            
function load_patients() {
    $("#content-panel").load("jsp/view_patients.jsp");
}
            
function user_creation() {
    $("#content-panel").load("jsp/user_creation.jsp");
}

function manage_appointments() {
    $("#content-panel").load("jsp/manage_appointments.jsp");
}

function view_finance() {
     $("#content-panel").load("jsp/view_finance.jsp");
}

function showDoctorDiv() {
    hideAll();
    $("#usertype").val("doctor");
    $(".doctor-field").show();
    $("#userTypeSelector").html("Doctor&nbsp;<span class=\"caret\"></span>");
}

function showPatientDiv() {
    hideAll();
    $("#usertype").val("patient");
    $(".patient-field").show();
    $("#userTypeSelector").html("Patient&nbsp;<span class=\"caret\"></span>");
}

function showStaffDiv() {
    hideAll();
    $("#usertype").val("staff");
    $("#userTypeSelector").html("Staff&nbsp;<span class=\"caret\"></span>");
}

function showFinanceDiv() {
    hideAll();
    $("#usertype").val("finance");
    $("#userTypeSelector").html("Finance&nbsp;<span class=\"caret\"></span>");
}

function showLegalDiv() {
    hideAll();
    $("#usertype").val("legal");
    $("#userTypeSelector").html("Legal&nbsp;<span class=\"caret\"></span>");
}

function hideAll() {
    $(".doctor-field").hide();
    $(".patient-field").hide();
    $('#create_submit').prop("disabled", false);
}


function manage_doctor_patient_viewing() {
    $("#content-panel").load("jsp/doctor_patient_viewing.jsp");
}