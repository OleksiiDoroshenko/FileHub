import Mutator from '../mutator.js';

/**
 * Class that can change 'isLoading' filed in the {@link StateManager} state.
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
