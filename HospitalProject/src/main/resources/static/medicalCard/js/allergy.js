var $table = $("#allergy-table");
var $table2 = $("#records-table");
var $tableHabit = $("#bad-habit-table");
    selections = [];
$(function () {
    $tableHabit.bootstrapTable({
        columns: [
            [
                {
                    title: "Title",
                    field: "title",
                    sortable: true,
                    valign: "center",
                    editable: {
                        type: "text"
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
        toolbar: "#toolbar-bad-habit",
        buttonsClass: "outline-secondary",
        sortClass: undefined,
        undefinedText: "-",
        striped: true,
        sortName: "title",
        sortOrder: "asc",
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
        uniqueId: "title",
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
        idField: "title",
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

    $table.bootstrapTable({
        columns: [
            [
                {
                    title: "Title",
                    field: "title",
                    sortable: true,
                    valign: "center",
                    editable: {
                        type: "text"
                    }
                },

                {
                    title: "Reaction",
                    field: "reaction",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text"
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
        toolbar: "#toolbar",
        buttonsClass: "outline-secondary",
        sortClass: undefined,
        undefinedText: "-",
        striped: true,
        sortName: "title",
        sortOrder: "asc",
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
        uniqueId: "title",
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
        idField: "title",
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
    $table2.bootstrapTable({
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
                        type: "date"},
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
        sortName: "date",
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
        idField: "date",
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

function openUpdateAllergyForm(title, reaction) {
    console.log("update allergy form");
    document.getElementById("allergy-logo").innerHTML = "Update Allergy";
    document.getElementById("input-title-of-allergy").value = title;
    document.getElementById("input-old-title-of-allergy").value = title;
    document.getElementById("input-reaction-of-allergy").value = reaction;
    document.getElementById("form-for-allergy").style.display = "block";
}

function openUpdateAllergyFormWithObj(par) {
    allergy = JSON.parse(par)
    openUpdateAllergyForm(allergy.title, allergy.reaction);
}

function closeForm() {
    document.getElementById("form-for-allergy").style.display = "none";
}
function closeFormNewAllergy() {
    document.getElementById("form-for-allergy-add").style.display = "none";
}