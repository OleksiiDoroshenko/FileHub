import Mutator from '../mutator.js';

/**
 * Class that can change 'items' filed in the {@link StateManager} state.
 */
export default class ItemsMutator extends Mutator {
  /**
   * Returns instance of {@link ItemsMutator}.
   * @param {[Object]} items - {@link FileExplorerPage} content items.
   */
  constructor(items) {
    super();
    this.items = items;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.items = this.items;
  }
}
