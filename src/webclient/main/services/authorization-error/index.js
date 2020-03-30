/**
 * Contains description of authorization errors with code '422'.
 */
export default class AuthorizationError {
  /**
   * Initial class constructor.
   * @param {number} code - error code(422).
   * @param {string} message - error message.
   */
  constructor(code, message) {
    this.code = code;
    this.message = message;
  }
}
