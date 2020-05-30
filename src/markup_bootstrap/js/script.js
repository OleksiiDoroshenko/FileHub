function uploadFile() {

    let uploadBtn = document.getElementById("upload-file");
    uploadBtn.click();

}

function deleteElement(el) {

    let parentElement = el.parentElement;
    let parentElement1 = parentElement.parentElement;
    let parentElement2 = parentElement1.parentElement;
    parentElement2.removeChild(parentElement1);

}

function addDirectory() {

    let table = document.getElementById("file-list");
    let row = table.insertRow(0);

    let cell = row.insertCell(0);
    let cell1 = row.insertCell(1);
    let cell2 = row.insertCell(2);
    let cell3 = row.insertCell(3);

    cell.innerHTML = "<i class=\"glyphicon glyphicon-triangle-right\"></i> " +
        "<i class=\"glyphicon glyphicon-folder-close\"></i>";
    cell1.innerHTML = "<a href=\"#\">New Directory</a>";
    cell2.innerHTML = "0 items";
    cell3.innerHTML = "<i onclick=\"uploadFile()\" class=\"glyphicon glyphicon-upload\"></i>" +
        "                        <i onclick='deleteElement(this)' class=\"glyphicon glyphicon-remove-circle\"></i>";


    cell.classList.add("item-icon");
    cell.classList.add("folder-icon");
    cell1.classList.add("name");
    cell1.classList.add("folder");
    cell2.classList.add("items");
    cell3.classList.add("clickable");
    return false;
}
