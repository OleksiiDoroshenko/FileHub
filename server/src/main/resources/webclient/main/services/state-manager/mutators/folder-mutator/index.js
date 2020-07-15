import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state user field changing.
 */
export default class FolderMutator extends Mutator {

  /**
   * Returns instance of {@link FolderMutator} class.
   * @param {Object} folder - folder model.
   */
  constructor(folder) {
    super();
    this.folder = folder;
  }

  /**
   * @inheritoc
   */
  apply(state) {
    state.folder = this.folder;
  }
}
