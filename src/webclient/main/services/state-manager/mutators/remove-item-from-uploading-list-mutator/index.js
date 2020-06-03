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
    let set = new Set(state.uploadingItemIds);
    if (set.has(this.itemId)) {
      set.delete(this.itemId);
    }
    state.uploadingItemIds = set;
  }
}
