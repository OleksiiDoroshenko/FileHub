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
    const list = [];
    list.push(state.deletingItems);
    if (!list.includes(this.itemId)) {
      list.push(this.itemId);
    }
    state.deletingItems = list;
  }
}
