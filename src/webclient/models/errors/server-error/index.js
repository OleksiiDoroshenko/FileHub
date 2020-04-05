/**
 * Contains errors array that server returns in response.
 */
export default class ServerValidationErrors extends Error {
  /**
   * @type {VerificationError[]} server validation errors array.
   */
  errors = [];

  /**
   *  Returns class instance.
   * @param {[VerificationError]} errors - server validation errors array.
   */
  constructor(errors) {
    super();
    this.errors = errors;
  }
}
