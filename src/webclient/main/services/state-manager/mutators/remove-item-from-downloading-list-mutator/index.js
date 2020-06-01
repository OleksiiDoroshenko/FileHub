import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state downloadingList field changing.
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
    let list = [];
    list.push(state.downloadingList);
    if (list.includes(this.itemId)) {
      const index = list.findIndex(id => id === this.itemId);
      delete list[index];
    }
    state.downloadingList = list;
  }
}
