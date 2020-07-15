import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isFolderCreating field changing.
 */
export default class FolderCreatingMutator extends Mutator {
  /**
   * Returns instance of {@link FolderCreatingMutator}.
   * @param {boolean} isCreating - folder creating state.
   */
  constructor(isCreating) {
    super();
    this.isCreating = isCreating;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.isFolderCreating = this.isCreating;
  }
}
