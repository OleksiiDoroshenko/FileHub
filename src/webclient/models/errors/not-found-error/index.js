/**
 * Contains description of not found error with code '404'.
 */
export default class NotFoundError extends Error {
  /**
   * Initial class constructor.
   * @param {string} message - error message.
   * @param {string} requestedItem - item that was requested from server.
   */
  constructor(message, requestedItem) {
    super();
    this.message = message;
    this.requestedItem = requestedItem;
  }
}
