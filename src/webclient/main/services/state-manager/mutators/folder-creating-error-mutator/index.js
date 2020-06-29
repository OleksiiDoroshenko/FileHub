import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state folderCreatingError field changing.
 */
export default class FolderCreatingErrorMutator extends Mutator {
  /**
   * Returns instance of {@link FolderCreatingErrorMutator}.
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
    state.folderCreatingError = this.error;
  }
}
