function load_appointments() {
    $("#content-panel").load("../jsp/view_appointments.jsp");
}
            
function load_patients() {
    $("#content-panel").load("../jsp/view_patients.jsp");
}

function load_patient_info() {
    $("#content-panel").load("../jsp/patient_information.jsp");
}
            
function user_creation() {
    $("#content-panel").load("../jsp/user_creation.jsp");
}

function manage_appointments() {
    $("#content-panel").load("../jsp/manage_appointments.jsp");
}

function doctor_visits() {
    $("#content-panel").load("../jsp/doctor_visits.jsp");
}

function patient_visits() {
     $("#content-panel").load("../jsp/patient_visits.jsp");
}

function revenue() {
     $("#content-panel").load("../jsp/revenue.jsp");
}

function manage_doctor_patient_viewing() {
    $("#content-panel").load("../jsp/doctor_patient_viewing.jsp");
}

function search_patient() {
    $("#content-panel").load("../jsp/search_patient.jsp");
}

function search_visit() {
    $("#content-panel").load("../jsp/search_visit.jsp");
}

function manage_doctor_staff() {
    $("#content-panel").load("../jsp/doctor_staff_assignment.jsp");
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

function logout() {
    $.ajax({
        type: "POST",
        url: "../DeleteSessionServlet",
        dataType: "JSON",
        success: function () {
            window.location.replace("../index.jsp");
        }
    });
}