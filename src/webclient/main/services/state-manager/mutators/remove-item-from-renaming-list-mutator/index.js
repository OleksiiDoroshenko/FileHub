import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state deletingItems field changing.
 */
export default class RemoveItemFromRenamingListMutator extends Mutator {
  /**
   * Returns instance of {@link RemoveItemFromRenamingListMutator}.
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
    if (set.has(this.itemId)) {
      set.delete(this.itemId);
    }
    state.renamingItemIds = set;
  }
}
