function onDrawWithDbId(e) {
    var item = e.record, field = e.field, value = e.value;
    e.cellHtml = '<span style="color: green">'+e.record.dbId+'</span> | '+value;
}

function windowMiddle(openWindow){
    var left =(window.screen.availWidth-openWindow.getWidth())/2;
    var top = (window.screen.availHeight-100-openWindow.getHeight())/2;
    openWindow.getEl().style.left= left+"px";
    openWindow.getEl().style.top= top+"px";
}

function onCancel(e) {
    CloseWindow("cancel");
}

function CloseWindow(action) {
    if (window.CloseOwnerWindow)
        return window.CloseOwnerWindow(action);
    else window.close();
}