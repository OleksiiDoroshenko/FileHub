import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state folder field changing.
 */
export default class FolderLoadingErrorMutator extends Mutator {
  /**
   * Returns instance of {@link FolderLoadingErrorMutator}.
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
    state.folderLoadingError = this.error;
  }
}
