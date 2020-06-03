import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state deletingItems field changing.
 */
export default class RemoveItemFromDeletingListMutator extends Mutator {
  /**
   * Returns instance of {@link RemoveItemFromDeletingListMutator}.
   * @param {string} itemId - item id where another item is uploading.
   */
  constructor(itemId) {
    super();
    this.itemId = itemId;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    let set = new Set(state.deletingItemIds);
    if (set.has(this.itemId)) {
      set.delete(this.itemId);
    }
    state.deletingItemIds = set;
  }
}
