import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state isLoadingError field changing.
 */
export default class AddItemToUploadingListMutator extends Mutator {
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
    const set = new Set(state.uploadingItemIds);
    set.add(this.itemId);
    state.uploadingItemIds = set;
  }
}
