import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state userLoadingError field changing.
 */
export default class UserLoadingError extends Mutator {
  /**
   * Returns instance of {@link UserLoadingError}.
   * @param {Error} error - error that appeared in the server response.
   */
  constructor(error) {
    super();
    this.error = error;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.userLoadingError = this.error;
  }
}
