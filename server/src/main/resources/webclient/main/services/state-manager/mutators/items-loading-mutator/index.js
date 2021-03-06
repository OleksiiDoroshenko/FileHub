import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isLoading field changing.
 */
export default class ItemLoadingMutator extends Mutator {
  /**
   * Returns instance of {@link ItemLoadingMutator}.
   * @param {boolean} isLoading - flag that describes {@link FileExplorerPage} content items loading state.
   */
  constructor(isLoading) {
    super();
    this.isLoading = isLoading;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.isLoading = this.isLoading;
  }
}
