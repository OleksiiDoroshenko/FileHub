import Mutator from '../mutator.js';

/**
 * Provides assigning flag that describes is one of the items is being renamed to {@link StateManager} state.
 */
export default class IsItemEditingMutator extends Mutator {
  /**
   * Returns instance of {@link IsItemEditingMutator}.
   * @param {boolean} isItemEditing - true if file is being edit, false if it is not.
   */
  constructor(isItemEditing) {
    super();
    this.isItemEditing = isItemEditing;
  }

  /**
   * @inheritdoc
   * @param state
   */
  apply(state) {
    state.isItemEditing = this.isItemEditing;
  }
}
