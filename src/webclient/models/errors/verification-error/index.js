/**
 * Contains description of verification errors with code '401'.
 */
export default class VerificationError extends Error {
  /**
   * Initial class constructor.
   * @param {string} field - form input filed where data is invalid(login/pwd).
   * @param {string} message - error message.
   */
  constructor(field, message) {
    super();
    this.field = field;
    this.message = message;
  }
}
