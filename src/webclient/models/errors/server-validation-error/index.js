/**
 * Contains error that have arisen on the server.
 */
export default class ServerValidationError extends Error {
  /**
   * Returns instance of {@link ServerValidationErrors}
   * @param {string} field - form input filed where data is invalid.
   * @param {string} message - server validation error message.
   */
  constructor(error) {
    super();
    this.field = error.field;
    this.message = error.message;
  }
}
