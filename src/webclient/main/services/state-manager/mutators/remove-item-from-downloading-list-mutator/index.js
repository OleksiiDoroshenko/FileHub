import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state downloadItems field changing.
 */
export default class RemoveItemFromDownloadingListMutator extends Mutator {
  /**
   * Returns instance of {@link RemoveItemFromDownloadingListMutator}.
   * @param {string} itemId - item id where another item is downloading.
   */
  constructor(itemId) {
    super();
    this.itemId = itemId;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    let set = new Set(state.downloadingItems);
    if (set.has(this.itemId)) {
      set.delete(this.itemId);
    }
    state.downloadingItems = set;
  }
}
