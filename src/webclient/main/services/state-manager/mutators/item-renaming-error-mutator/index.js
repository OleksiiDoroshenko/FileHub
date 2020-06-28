import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state renamingError field changing.
 */
export default class ItemsRenamingErrorMutator extends Mutator {
  /**
   * Returns instance of {@link ItemsRenamingErrorMutator}.
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
    state.renamingError = this.error;
  }
}
