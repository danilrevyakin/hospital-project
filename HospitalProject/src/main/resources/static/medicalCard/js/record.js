function openForm() {
    document.getElementById("form-for-allergy").style.display = "block";
}

function openNewAllergyForm(){
    document.getElementById("form-for-allergy-add").style.display = "block";
}

function formatDate(str){
    var date = new Date(str);
    var strArray=['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var res = date.getDate()+"/"+strArray[date.getMonth()]+"/"+(date.getYear() - 100);
    res+= '\n' + date.getHours() + ":" + date.getMinutes();
    return res;
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
