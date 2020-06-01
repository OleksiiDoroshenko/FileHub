import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state downloadList field changing.
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
    const list = [];
    list.push(state.downloadList);
    if (!list.includes(this.itemId)) {
      list.push(this.itemId);
    }
    state.downloadList = list;
  }
}
