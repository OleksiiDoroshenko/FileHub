import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isLoadingError field changing.
 */
export default class RemoveItemToUploadingListMutator extends Mutator {
  /**
   * Returns instance of {@link ItemsLoadingErrorMutator}.
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
    let list = state.uploadingItems;
    const index = list.findIndex(id => id === this.itemId);
    delete list[index];
    state.uploadingItems = list;
  }
}
