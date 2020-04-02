/**
 * Contains errors array that server returns in response.
 */
export default class ServerValidationErrors extends Error {
  /**
   *
   * @type {VerificationError[]}
   */
  errors = [];

  /**
   *
   * @param errors
   */
  constructor(errors) {
    super();
    this.errors = errors;
  }
}
