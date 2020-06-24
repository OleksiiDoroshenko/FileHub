import Action from '../action.js';
import ClearErrorMutator from '../../mutators/clear-error-mutator/index.js';

/**
 * Creates specific mutator that clears error field.
 */
export default class ClearErrorAction extends Action {

  /**
   * Returns instance of {@link ClearErrorAction} class.
   * @param {string} errorField - error field that should be cleared
   */
  constructor(errorField) {
    super();
    this.errorField = errorField;
  }

  /**
   * @inheritdoc
   */
  async apply(stateManager, apiService) {
    stateManager.mutate(new ClearErrorMutator(this.errorField));
  }
}
