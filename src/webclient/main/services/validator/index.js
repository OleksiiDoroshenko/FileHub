/**
 * Implements methods for login, password and confirm password inputs.
 * */
import VerificationError from '../../../models/errors/verification-error';

/**
 * Implements methods for user data validation.
 */
export default class Validator {
  /**
   *  Validates login string value if it is fully matches regex rules.
   *  <p> Rules: Login should contains only latin letters or numbers.
   *  <p> Not case sensitive.
   * @param {string} login - username.
   * @return {Promise} if login fits special rules returns true,
   *  if not returns false.
   */
  validateLogin(login) {
    const rules = /^\w+/;
    const minLength = 4;
    return new Promise(((resolve, reject) => {
      if (login.length < minLength) {
        reject(new VerificationError('login',
          `Login should be longer than ${minLength} symbols.`));
      }
      if (!rules.test(login)) {
        reject(new VerificationError('login',
          'Login should contains only latin letters or numbers.'));
      }
      resolve();
    }));
  }

  /**
   *  Validates password string value if it is fully matches regex rules.
   *  <p> Rules:
   *        1) Password should contains only latin letters or numbers.
   *        2) Password should contain at least 1 uppercase and lowercase letters and 1 digit.
   * @param {string} password - user password.
   * @return {Promise} if login fits special rules returns true,
   *  if not returns false.
   */
  validatePassword(password) {
    const rules = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])/;
    const minLength = 8;
    return new Promise(((resolve, reject) => {
        if (password.length < minLength) {
          reject(new VerificationError('password',
            `Password should be longer than ${minLength} symbols.`));
        }
        if (!rules.test(password)) {
          reject(new VerificationError('password',
            'Password should contain at least 1 uppercase and lowercase letters and 1 digit.'));
          resolve();
        }
        resolve();
      }
    ));
  }


  /**
   *  Validates confirm password row if its fully fits the rules.
   *  Rules:
   *      1)passwords should be strict equal.
   * @param {string} confirmPassword - form row that contains user confirmation password.
   * @param {string} password - form row that contains user password.
   * @return {Promise} returns true if confirmation password and password are strict equal,
   * if they are not:
   *      1)Renders warning message under the confirmation password input.
   *      <p> Message contains explanation of what is not ok.
   *      2)Returns false.
   */
  comparePasswords(confirmPassword, password) {
    return new Promise(((resolve, reject) => {
      if (confirmPassword.password) {
        resolve();
      } else {
        reject(new VerificationError('confirmPassword', 'Passwords do not match.'));
      }
    }));
  }
}
