function openFormOfNewBadHabit() {
    document.getElementById("form-for-add-bad-habit").style.display = "block";
    document.getElementById("input-info-add-bad-habit").value = "";
}

function closeFormOFNewBadHabit() {
    document.getElementById("form-for-add-bad-habit").style.display = "none";
}

function openFormOfUpdateBadHabit() {
    document.getElementById("form-for-update-bad-habit").style.display = "block";
}

function closeFormOfUpdateBadHabit() {
    document.getElementById("form-for-update-bad-habit").style.display = "none";
}

function openUpdateBadHabitForm(habit) {
    habit = JSON.parse(habit);
    document.getElementById("input-update-bad-habit").value = habit;
    document.getElementById("input-old-update-bad-habit").value = habit;
    openFormOfUpdateBadHabit();
}