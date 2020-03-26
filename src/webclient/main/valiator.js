export default class Validator {


  validateLogin(login) {
    let rules = /^\w+/;
    return rules.test(login.toLowerCase());
  }

  validatePassword(password) {
    let rules = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/;
    return rules.test(password);
  }

  /**
   *
   * @param{FormRow} usernameRow
   */
  validateLoginRow(usernameRow) {
    if (usernameRow.value.length < 4) {
      usernameRow.showWarning('Username should be longer than 4 characters.');
      return false;
    } else if (!this.validateLogin(usernameRow.value)) {
      usernameRow.showWarning('Username should contain only latin letters and numbers.');
      return false;
    }
    return true;
  }

  /**
   *
   * @param{FormRow} pwdRow
   */
  validatePasswordRow(pwdRow) {
    if (pwdRow.value.length < 8) {
      pwdRow.showWarning('Password should be longer than 8 characters.');
      return false;
    } else if (!this.validatePassword(pwdRow.value)) {
      pwdRow.showWarning('Password should contain at least 1 uppercase and lowercase letters and 1 digit.');
      return false;
    }
    return true;
  }

  /**
   *
   * @param {FormRow} cnfPwdRow
   * @param {FormRow} pwdRow
   */
  validateCnfPasswordRow(cnfPwdRow, pwdRow) {
    if (cnfPwdRow.value !== pwdRow.value) {
      cnfPwdRow.showWarning('Passwords do not match.');
      return false;
    }
    return true;
  }
}
