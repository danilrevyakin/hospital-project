var $table = $("#table"),
    $remove = $("#remove"),
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
                    valign: "middle" },

                {
                    title: "ID",
                    field: "id",
                    sortable: true,
                    valign: "middle" },

                {
                    title: "Date",
                    field: "date",
                    sortable: true,
                    valign: "middle",
                    id: "dob",
                    editable: {
                        type: "combodate",
                        format: "YYYY/MM/DD",
                        template: "YYYY / MM / DD",
                        combodate: {
                            maxYear: 2030,
                            minYear: 2018,
                            firstItem: "none" //'name', 'empty', 'none'
                        },
                        emptytext: "-" } },


                {
                    title: "Supplier",
                    field: "supplier",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text" } },


                {
                    title: "Items",
                    field: "items",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text" } },


                {
                    title: "Deadline",
                    field: "deadline",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text" } },


                {
                    title: "Quantity",
                    field: "quantity",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "text" } },


                {
                    title: "Amount",
                    field: "amount",
                    sortable: true,
                    valign: "middle",
                    editable: {
                        type: "number" } },


                {
                    title: "Closing",
                    field: "closing",
                    align: "center",
                    sortable: true,
                    id: "status",
                    valign: "middle",
                    editable: {
                        type: "select",
                        placement: "right",
                        value: "是",
                        source: [{ value: "10 PM", text: "10 PM" }, { value: "是", text: "是" }] } },


                {
                    title: "Progress",
                    field: "progress",
                    align: "center",
                    sortable: true,
                    valign: "middle" },

                {
                    title: "Print",
                    field: "print",
                    align: "center",
                    sortable: true,
                    valign: "middle" },

                {
                    title: "Action",
                    field: "action",
                    align: "center",
                    sortable: true,
                    valign: "middle",
                    events: actionEvents,
                    formatter: actionFormatter }]],



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
            values: ids });

        $remove.prop("disabled", true);
    });
    $('[data-toggle="dropdown"] >i').
    removeClass("glyphicon-export").
    addClass("fa-download");
});
function getIdSelections() {
    return $.map($table.bootstrapTable("getSelections"), function (row) {
        return row.id;
    });
}
function actionFormatter(value, row, index) {
    return ['<button class="remove btn btn-danger btn-sm">Delete</button>'].join(
        "");

}
window.actionEvents = {
    "click .remove": function (e, value, row, index) {
        $table.bootstrapTable("remove", {
            field: "id",
            values: [row.id] });

    } };