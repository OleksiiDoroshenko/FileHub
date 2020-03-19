import FormRow from "./form-row.js";
import NavPanel from "./nav-panel.js";

const root = document.querySelector('.form-horizontal');

let userNameRow = new FormRow(root);
userNameRow.labelText = "Username";
userNameRow.warningText = "Username can't be empty";
userNameRow.inputAttributes("email", "email", "Email");

let passwordRow = new FormRow(root);
passwordRow.labelText = "Password";
passwordRow.warningText = "Password can't be empty and should contain letters and numbers";
passwordRow.inputAttributes("pwd", "password", "Password");

let navPanel = new NavPanel(root);
navPanel.btnText = "Log in";
navPanel.linkText = "Don't have an account yet?";
navPanel.linkHref = "#";
