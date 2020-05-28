/**
 * Contains description of not found error with code '404'.
 */
export default class NotFoundError extends Error {
  /**
   * Initial class constructor.
   * @param {string} message - error message.
   */
  constructor(message) {
    super();
    this.message = message;
  }
}
