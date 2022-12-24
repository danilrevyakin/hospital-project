function openForm() {
    document.getElementById("form-for-allergy").style.display = "block";
}

function openNewAllergyForm(){
    document.getElementById("form-for-allergy-add").style.display = "block";
}

function openUpdateRecordFormWithObj(par) {
    record = JSON.parse(par)
    document.getElementById("input-info").value = record.info;
    document.getElementById("input-symptoms").value = record.symptoms;
    document.getElementById("input-treatment").value = record.treatment;
    document.getElementById("input-doctor").value = record.doctor;
    document.getElementById("input-date").value = record.date;
    document.getElementById("input-edited").value = record.edited;
    document.getElementById("form-for-update-record").style.display = "block";
}

function closeFormRecordUpdate() {
    document.getElementById("form-for-update-record").style.display = "none";
}
function closeFormRecordAdd() {
    document.getElementById("form-for-add-record").style.display = "none";
}
function openNewRecordForm(){
    document.getElementById("form-for-add-record").style.display = "block";
}