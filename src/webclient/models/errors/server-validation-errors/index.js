/**
 * Contains errors array that server returns in response.
 */
export default class ServerValidationErrors extends Error {
  /**
   * List of server errors.
   * @type {[Error]}
   */
  errors = [];

  /**
   * Returns instance of {@link ServerValidationErrors}
   * @param {[Error]} errors - list of errors that have arisen on the server.
   */
  constructor(errors) {
    super();
    this.errors = errors;
  }
}
