/**
 * Contains description of verification errors with code '401'.
 */
export default class VerificationError {
  /**
   * Initial class constructor.
   * @param {number} code - error code.
   * @param {string} filed - form input filed which data is invalid(login/pwd).
   * @param {string} message - error message.
   */
  constructor(code, filed, message) {
    this.code = code;
    this.field = filed;
    this.message = message;
  }
}
