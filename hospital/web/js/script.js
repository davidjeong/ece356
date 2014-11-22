function load_visits() {
    $("#content-panel").load("../jsp/visit_record.jsp");
}
            
function load_patients() {
    $("#content-panel").load("../jsp/view_patients.jsp");
}
            
function user_creation() {
    $("#content-panel").load("../jsp/user_creation.jsp");
}

function manage_appointments() {
    $("#content-panel").load("../jsp/manage_appointments.jsp");
}

function Doctor_Visits() {
     $("#content-panel").load("../jsp/doctor_visits.jsp");
}

function Patient_Visits() {
     $("#content-panel").load("../jsp/patient_visits.jsp");
}

function manage_doctor_patient_viewing() {
    $("#content-panel").load("../jsp/doctor_patient_viewing.jsp");
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
    $('#newUserSubmit').prop("disabled", false);
}

function changeDefaultDoctor(doctor) {
    var parent = doctor.parentNode;
    var cpso = parent.value;
    $("#default_doctor").val(cpso);
    $("#default_doctor_dropdown").html(doctor.innerHTML + "&nbsp;<span class=\"caret\"></span>");
}

function untruncateCpso(value) {
    if (value.length === 6) {
        return value;
    }
    var fixed = value;
    var len = value.length;
    while (len < 6) {
        fixed = '0' + fixed;
        len++;
    }
    return fixed;
}
