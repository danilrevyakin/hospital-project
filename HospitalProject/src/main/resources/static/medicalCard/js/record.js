var $table = $("#records-table"),
    selections = [];
$(function () {
    $table.bootstrapTable({
        columns: [
            [
                {
                    title: "Info",
                    field: "info",
                    sortable: true,
                    valign: "center",
                    editable: {
                        type: "text"
                    }
                },

                {
                    title: "Symptoms",
                    field: "symptoms",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text"
                    }
                },
                {
                    title: "Treatment",
                    field: "treatment",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text"
                    }
                },
                {
                    title: "Doctor",
                    field: "doctor",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text"
                    }
                },
                {
                    title: "Date",
                    field: "date",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text"},
                },
                {
                    title: "Edited",
                    field: "edited",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "date"
                    }
                },

                {
                    title: "Action",
                    field: "action",
                    align: "center",
                    sortable: true,
                    valign: "middle",
                }
            ]],


        classes: "table table-hover table-no-bordered",
        toolbar: "#toolbar-records",
        buttonsClass: "outline-secondary",
        sortClass: undefined,
        undefinedText: "-",
        striped: true,
        sortName: "number",
        sortOrder: "desc",
        sortStable: false,
        sortable: true,
        pagination: true,
        paginationLoop: false,
        onlyInfoPagination: false,
        pageNumber: 1,
        pageSize: 5,
        pageList: [1, 3, 5, 10, "ALL"],
        paginationPreText: "Previous",
        paginationNextText: "Next",
        // selectItemName: "btSelectItem",
        smartDisplay: true,
        search: true,
        uniqueId: "date",
        searchOnEnterKey: false,
        strictSearch: false,
        searchText: "",
        searchTimeOut: "500",
        trimOnSearch: true,
        searchalign: "right",
        buttonsAlign: "right",
        toolbarAlign: "left",
        paginationVAlign: "bottom",
        paginationHAlign: "right",
        paginationDetailHAlign: "left",
        showHeader: true,
        showFooter: false,
        showColumns: true,
        showRefresh: false,
        showToggle: false,
        showExport: true,
        showPaginationSwitch: true,
        showFullscreen: false,
        minimumCountColumns: 5,
        idField: undefined,
        clickToSelect: false,
        singleSelect: false,
        checkboxHeader: true,
        maintainSelected: true
        // reorderableColumns: true,
        // iconsPrefix: "material-icons", // material-icons of fa (font awesome)
        // icons: {
        //   paginationSwitchDown: "material-icons-collapse-down icon-chevron-down",
        //   paginationSwitchUp: "material-icons-collapse-up icon-chevron-up",
        //   refresh: "material-icons-refresh icon-refresh",
        //   toggle: "material-icons-list-alt icon-list-alt",
        //   columns: "material-icons-th icon-th",
        //   detailOpen: "glyphicon-plus icon-plus",
        //   detailClose: "glyphicon-minus icon-minus"
        // }
    });
    // $('[data-toggle="dropdown"] >i').removeClass("glyphicon-export").addClass("fa-download");
});

function getIdSelections() {
    return $.map($table.bootstrapTable("getSelections"), function (row) {
        return row.date;
    });
}

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
