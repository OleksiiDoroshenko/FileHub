import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state id field changing.
 */
export default class IdMutator extends Mutator {
  /**
   * Returns instance of {@link IdMutator}.
   * @param {string} id - folder id.
   */
  constructor(id) {
    super();
    this.id = id;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state.id = this.id;
  }
}
