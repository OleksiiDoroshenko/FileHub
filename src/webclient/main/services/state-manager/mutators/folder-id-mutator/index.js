import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state folderId field changing.
 */
export default class FolderIdMutator extends Mutator {

  /**
   * Returns instance of {@link FolderIdMutator}.
   * @param {string} folderId - folder id.
   */
  constructor(folderId) {
    super();
    this.folderId = folderId;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.folderId = this.folderId;
  }
}
