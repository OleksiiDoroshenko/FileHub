import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state renamingItemIds field changing.
 */
export default class AddItemToRenamingListMutator extends Mutator {
  /**
   * Returns instance of {@link AddItemToRenamingListMutator}.
   * @param {string} itemId - item id that should be renamed.
   */
  constructor(itemId) {
    super();
    this.itemId = itemId;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    const set = new Set(state.renamingItemIds);
    set.add(this.itemId);
    state.renamingItemIds = set;
  }
}
