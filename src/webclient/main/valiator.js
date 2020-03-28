/**
 * Implements methods for login, password and confirm password inputs.
 * */
export default class Validator {
  /**
   *  Validates login string value if it is fully matches regex rules.
   *  <p> Rules: Login should contains only latin letters or numbers.
   *  <p> Not case sensitive.
   * @param {string} login - username.
   * @return {boolean} if login fits special rules returns true,
   *  if not returns false.
   */
  validateLogin(login) {
    const rules = /^\w+/;
    return rules.test(login.toLowerCase());
  }

  /**
   *  Validates password string value if it is fully matches regex rules.
   *  <p> Rules:
   *        1) Password should contains only latin letters or numbers.
   *        2) Password  contain at least 1 uppercase and lowercase letters and 1 digit.
   *  <p> Not case sensitive.
   * @param {string} password - user password.
   * @return {boolean} if login fits special rules returns true,
   *  if not returns false.
   */
  validatePassword(password) {
    const rules = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/;
    return rules.test(password);
  }

  /**
   *  Validates login row value if its value fully matches validation rules.
   *  <p> Rules:
   *      1)Login should be longer than 4 characters.
   *      2)Login should contains only latin letters or numbers.
   *  <p> Not case sensitive.
   * @param {FormRow} usernameRow - form row that contains fields with user username.
   * @return {boolean} returns true if login is valid,
   * if it is not:
   *      1)Renders warning message under the login input.
   *      <p> Message contains explanation of what is not ok.
   *      2)Returns false.
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
   *  Validates password row if its value fully matches validation rules.
   *  <p> Rules:
   *      1)Password should contains only latin letters or numbers.
   *      2)Password  contain at least 1 uppercase and lowercase letters and 1 digit.
   *  <p> Not case sensitive.
   * @param {FormRow} pwdRow - form row that contains fields with user password.
   * @return {boolean} returns true if password is valid,
   * if it is not:
   *      1)Renders warning message under the password input.
   *      <p> Message contains explanation of what is not ok.
   *      2)Returns false.
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
   *  Validates confirm password row if its fully fits the rules.
   *  Rules:
   *      1)passwords should be strict equal.
   * @param {FormRow} cnfPwdRow - form row that contains user confirmation password.
   * @param {FormRow} pwdRow - form row that contains user password.
   * @return {boolean} returns true if confirmation password and password are strict equal,
   * if they are not:
   *      1)Renders warning message under the confirmation password input.
   *      <p> Message contains explanation of what is not ok.
   *      2)Returns false.
   */
  validateCnfPasswordRow(cnfPwdRow, pwdRow) {
    if (cnfPwdRow.value !== pwdRow.value) {
      cnfPwdRow.showWarning('Passwords do not match.');
      return false;
    }
    return true;
  }
}
