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
    let list = [];
    list.push(state.deletingItems);
    if (list.includes(this.itemId)) {
      const index = list.findIndex(id => id === this.itemId);
      delete list[index];
    }
    state.deletingItems = list;
  }
}
