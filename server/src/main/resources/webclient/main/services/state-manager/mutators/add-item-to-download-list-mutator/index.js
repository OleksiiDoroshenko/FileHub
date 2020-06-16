import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state downloadItems field changing.
 */
export default class AddItemToDownloadingListMutator extends Mutator {
  /**
   * Returns instance of {@link AddItemToDownloadingListMutator}.
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
    const set = new Set(state.downloadingItemIds);
    set.add(this.itemId);
    state.downloadingItemIds = set;
  }
}
