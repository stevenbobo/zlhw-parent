function onDrawWithDbId(e) {
    var item = e.record, field = e.field, value = e.value;
    e.cellHtml = '<span style="color: green">'+e.record.dbId+'</span> | '+value;
}