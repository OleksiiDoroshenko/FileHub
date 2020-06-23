import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isUserLoading field changing.
 */
export default class UserLoadingMutator extends Mutator {
  /**
   * Returns instance of {@link ItemLoadingMutator}.
   * @param {boolean} isLoading - isLoading that describes {@link FileExplorerPage} user loading state.
   */
  constructor(isLoading) {
    super();
    this.isLoading = isLoading;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.isUserLoading = this.isLoading;
  }
}
