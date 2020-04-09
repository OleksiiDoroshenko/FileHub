import Mutator from '../mutator.js';

export default class ItemsMutator extends Mutator {
  /**
   * Returns instance of {@link ItemsMutator}.
   * @param {[object]} items - {@link FileExplorerPage} content items.
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
