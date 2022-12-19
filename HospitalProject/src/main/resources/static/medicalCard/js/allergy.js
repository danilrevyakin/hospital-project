var AllergyObj = {
    title: "default title of allergy",
    reaction: "default reaction of allergy"
};
var $table = $("#allergy-table"),
    $remove = $("#remove-allergies"),
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
                    title: "Edit",
                    field: "edit",
                    align: "center",
                    sortable: true,
                    valign: "middle",
                    events: actionEvents,
                    formatter: actionFormatterEdit
                },

                {
                    title: "Delete",
                    field: "action",
                    align: "center",
                    sortable: true,
                    valign: "middle",
                    events: actionEvents,
                    formatter: actionFormatterDelete
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
        showRefresh: true,
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
    $('[data-toggle="dropdown"] >i').removeClass("glyphicon-export").addClass("fa-download");
});

function getIdSelections() {
    return $.map($table.bootstrapTable("getSelections"), function (row) {
        return row.id;
    });
}

function actionFormatterEdit(value, row, index) {
    return ['<button class="edit btn btn-danger btn-sm">Edit</button>'].join(
        "");
}

function actionFormatterDelete(value, row, index) {
    return ['<button class="remove btn btn-danger btn-sm">Delete</button>'].join(
        "");
}

function openForm() {
    document.getElementById("form-for-allergy").style.display = "block";
}

function closeForm() {
    document.getElementById("form-for-allergy").style.display = "none";
}


function saveFormUpdate() {
    // document.getElementById("form-for-allergy").style.display = "none";
    let title = document.getElementById("input-title-of-allergy").value;
    let reaction = document.getElementById("input-reaction-of-allergy").value;
    AllergyObj.title = title;
    AllergyObj.reaction = reaction;

}

function myGet() {
    var url = 'http://localhost:8080/medical-card/get/1';
    fetch(url, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Access-Control-Allow-Origin':'*',
            Accept: 'application/json',
        }
    }).then((response) => {
        console.log(response);
        console.log("response.bodyUsed: " + response.bodyUsed);
        var body = response.json();
        console.log("body: " + body);
        console.log("method: " + body["body"]);
    });
}

// body: JSON.stringify({ "title": title, "reaction": reaction })

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
    }
};