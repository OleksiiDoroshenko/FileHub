/**
 * Contains description of authorization errors with code '422'.
 */
export default class AuthorizationError extends Error {
  /**
   * Initial class constructor.
   * @param {string} message - error message.
   */
  constructor(message) {
    super();
    this.message = message;
  }
}
