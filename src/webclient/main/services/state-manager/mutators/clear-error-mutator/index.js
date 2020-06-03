import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state passed to the constructor error field changing.
 */
export default class ClearErrorMutator extends Mutator {
  /**
   * Returns instance of {@link ClearErrorMutator}.
   * @param {string} errorField - error field that that should be cleared.
   */
  constructor(errorField) {
    super();
    this.errorField = errorField;
  }

  /**
   * @inheritdoc
   */
  apply(state) {
    state[this.errorField] = null;
  }
}
