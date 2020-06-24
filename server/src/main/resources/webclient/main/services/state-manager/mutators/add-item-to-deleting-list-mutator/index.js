import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state deletingList field changing.
 */
export default class AddItemToDeletingListMutator extends Mutator {
  /**
   * Returns instance of {@link AddItemToDeletingListMutator}.
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
    const set = new Set(state.deletingItemIds);
    set.add(this.itemId);
    state.deletingItemIds = set;
  }
}
