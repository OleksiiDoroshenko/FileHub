import Action from '../action.js';
import FilesMutator from '../../mutators/get-item-mutator';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

export default class GetItemsAction extends Action {
  /**
   *
   * @param {StateManager} stateManager
   * @param {AuthenticationService} apiService
   */
  apply(stateManager, apiService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    apiService.getItems()
      .then(items => {
        stateManager.mutate(new FilesMutator(items));
      }).catch(e => {
      stateManager.mutate(new ItemLoadingErrorMutator(e));
    });
    stateManager.mutate(new ItemLoadingMutator(false));

  }
}
