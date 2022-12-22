var $table = $("#allergy-table"),
    $remove = $("#remove-allergies"),
    $allergyForm = $("#form-for-allergy"),
    selections = [];
$(function () {
    $table.bootstrapTable("destroy");
    $table.bootstrapTable({
        columns: [
            [
                {
                    field: "state",
                    checkbox: true,
                    align: "center",
                    valign: "middle"
                },

                {
                    title: "Title",
                    field: "title",
                    sortable: true,
                    valign: "middle",
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
                    events: actionEvents,
                }
            ]],


        classes: "table table-hover table-no-bordered",
        toolbar: "#toolbar",
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
        selectItemName: "btSelectItem",
        smartDisplay: true,
        search: true,
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
        uniqueId: "id",
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
    $table.on(
        "check.bs.table uncheck.bs.table " +
        "check-all.bs.table uncheck-all.bs.table",
        function () {
            $remove.prop("disabled", !$table.bootstrapTable("getSelections").length);
            selections = getIdSelections();
        });

    $remove.click(function () {
        var ids = getIdSelections();
        $table.bootstrapTable("remove", {
            field: "id",
            values: ids
        });

        $remove.prop("disabled", true);
    });
    $table.bootstrapTable('refresh')
    $('[data-toggle="dropdown"] >i').removeClass("glyphicon-export").addClass("fa-download");
});

function getIdSelections() {
    return $.map($table.bootstrapTable("getSelections"), function (row) {
        return row.id;
    });
}

function openForm() {
    document.getElementById("form-for-allergy").style.display = "block";
}

function openUpdateAllergyForm(title, reaction) {
    console.log("update allergy form");
    alert(URLObect.getDataURL());
    document.getElementById("allergy-logo").innerHTML = "Update Allergy";
    document.getElementById("input-title-of-allergy").value = title;
    document.getElementById("input-old-title-of-allergy").value = title;
    
    OldTitleVarObj.set(title);
    document.getElementById("input-reaction-of-allergy").value = reaction;
    document.getElementById("form-for-allergy").style.display = "block";
    initOldAllergyTitle();
}


function openUpdateAllergyFormWithObj(par) {
    alert("openUPD");
    allergy = JSON.parse(par)
    openUpdateAllergyForm(allergy.title, allergy.reaction);
}

function closeForm() {
    document.getElementById("form-for-allergy").style.display = "none";
}


function saveFormUpdate() {
    // document.getElementById("form-for-allergy").style.display = "none";
}

function initOldAllergyTitle(){
    let oldTitle = document.getElementById("oldTitleVar").value;
    console.log("old" + oldTitle);
    // AllergyObj.setAttributeOfForm("th:action", AllergyObj.getAllergyUpdateURL(oldTitle));
    alert(AllergyObj.getAllergyFormElement().getAttribute("th:action"))
}

window.actionEvents = {
    "click .remove": function (e, value, row, index) {
        $table.bootstrapTable("remove", {
            field: "title",
            values: [row.title]
        });

    }
    ,
    "click .edit": function (e, value, row, index) {
        console.log("here we go!");
        openForm();
    },
};


//
// function actionFormatterEdit(value, row, index) {
//     return ['<button class="edit btn btn-danger btn-sm">Edit</button>'].join(
//         "");
// }
//
// function actionFormatterDelete(value, row, index) {
//     return ['<button class="remove btn btn-danger btn-sm">Delete</button>'].join(
//         "");
// }