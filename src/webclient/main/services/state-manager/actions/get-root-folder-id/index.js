import Action from '../action.js';
import ItemsLoadingErrorMutator from '../../mutators/items-loading-error-mutator';
import IdMutator from '../../mutators/id-mutator';

/**
 * Provide functionality for requesting users root folder id.
 */
export default class GetRootIdAction extends Action {
  /**
   * Requests users root folder it and calls {@link IdMutator} to set it into {@link StateManager}.
   */
  apply(stateManager, apiService) {
    apiService.getRoot().then(response => {
      stateManager.mutate(new IdMutator(response.folder.id));
    }).catch(error => {
      stateManager.mutate(new ItemsLoadingErrorMutator(error));
    });
  }
}
