/**
 * Contains error that have arisen on the server.
 */
export default class ServerValidationError extends Error {
  /**
   * Returns instance of {@link ServerValidationErrors}
   * @param {Object} error - error that appeared in the server.
   * @param {string} error.field - form input filed where data is invalid.
   * @param {string} error.message - server validation error message.
   */
  constructor(error) {
    super();
    this.field = error.field;
    this.message = error.message;
  }
}
