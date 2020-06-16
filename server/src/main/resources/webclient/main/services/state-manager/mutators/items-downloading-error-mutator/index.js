import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state downloadingError field changing.
 */
export default class ItemsDownloadingErrorMutator extends Mutator {
  /**
   * Returns instance of {@link ItemsDownloadingErrorMutator}.
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
    state.downloadingError = this.error;
  }
}
