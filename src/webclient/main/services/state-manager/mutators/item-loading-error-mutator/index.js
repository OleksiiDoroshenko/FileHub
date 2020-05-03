import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isLoadingError field changing.
 */
export default class ItemLoadingErrorMutator extends Mutator {
  /**
   * Returns instance of {@link ItemLoadingErrorMutator}.
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
    state.error = this.error;
  }
}
