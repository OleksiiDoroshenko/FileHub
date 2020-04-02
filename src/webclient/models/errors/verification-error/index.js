/**
 * Contains description of verification errors with code '401'.
 */
export default class VerificationError extends Error {
  /**
   * Initial class constructor.
   * @param {string} filed - login-form input filed which data is invalid(login/pwd).
   * @param {string} message - error message.
   */
  constructor(filed, message) {
    super();
    this.field = filed;
    this.message = message;
  }
}
