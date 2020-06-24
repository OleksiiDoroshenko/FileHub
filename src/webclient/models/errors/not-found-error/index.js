/**
 * Contains description of not found error with code '404'.
 */
export default class NotFoundError extends Error {
  /**
   * Initial class constructor.
   * @param {string} message - error message.
   * @param {Object} model - model of item that was requested from server.
   */
  constructor(message, model) {
    super();
    this.message = message;
    this.model = model;
  }
}
