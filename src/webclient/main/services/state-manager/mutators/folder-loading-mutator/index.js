import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isFolderLoading field changing.
 */
export default class FolderLoadingMutator extends Mutator {
  /**
   * Returns instance of {@link FolderLoadingMutator}.
   * @param {boolean} isLoading - folder loading state.
   */
  constructor(isLoading) {
    super();
    this.isLoading = isLoading;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.isFolderLoading = this.isLoading;
  }
}
