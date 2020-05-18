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

    let table = document.getElementsByClassName("table")[0];
    let row = table.insertRow(0);

    let cell = row.insertCell(0);
    let cell1 = row.insertCell(1);
    let cell2 = row.insertCell(2);
    let cell3 = row.insertCell(3);

    cell.innerHTML = "<i class=\"glyphicon glyphicon-triangle-right\"></i> " +
        "<i class=\"glyphicon glyphicon-folder-close\"></i>";
    cell1.innerHTML = "<span class='cell-name-text'><a href=\"#\">New Directory</a></span>";
    cell2.innerHTML = "0 items";
    cell3.innerHTML = "<i onclick=\"uploadFile()\" class=\"glyphicon glyphicon-upload\"></i>" +
        "                        <i onclick='deleteElement(this)' class=\"glyphicon glyphicon-remove-circle\"></i>";

    cell.classList.add("cell-icon");
    cell1.classList.add("cell-name");
    cell2.classList.add("cell-size");
    cell3.classList.add("cell-actions");
    return false;
}
