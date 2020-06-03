import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state uploadingError field changing.
 */
export default class ItemUploadingErrorMutator extends Mutator {
  /**
   * Returns instance of {@link ItemUploadingErrorMutator}.
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
    state.uploadingError = this.error;
  }
}
