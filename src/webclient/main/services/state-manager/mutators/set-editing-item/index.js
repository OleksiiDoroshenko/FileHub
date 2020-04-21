import Mutator from '../mutator.js';

/**
 * Provides assigning editing state to {@link StateManager} state.
 */
export default class SetEditingItemMutator extends Mutator {
  /**
   * Returns instant of {@link SetEditingItemMutator}.
   * @param editingField - item that is will be renamed.
   */
  constructor(editingField) {
    super();
    this.editingField = editingField;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.editingField = this.editingField;
  }
}
