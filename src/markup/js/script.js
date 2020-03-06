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

    let table = document.getElementById("file-container");
    let row = table.insertRow(0);
    let cell1 = row.insertCell(0);
    let cell2 = row.insertCell(1);
    let cell3 = row.insertCell(2);
    let cell4 = row.insertCell(3);
    let cell5 = row.insertCell(4);

    cell1.innerHTML = "<i class=\"glyphicon glyphicon-triangle-right\">";
    cell2.innerHTML = "<i class=\"glyphicon glyphicon-folder-close\"></i>";
    cell2.classList.add("folder-icon");
    cell3.innerHTML = "<a href=\"#\"> New Directory</a>";
    cell4.innerHTML = "0 items";

    cell5.innerHTML = "<i onclick=\"uploadFile()\" class=\"glyphicon glyphicon-upload\"></i>\n" +
        "                        <i onclick='deleteElement(this)' class=\"glyphicon glyphicon-remove-circle\"></i>";
    cell5.classList.add("clickable");
}