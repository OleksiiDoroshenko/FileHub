import Action from '../action.js';
import UserLoadingMutator from '../../mutators/user-loading-mutator';
import UserLoadingError from '../../mutators/user-loading-error-mutator';
import UserDataMutator from '../../mutators/user-data-mutator';

/**
 * Provides functionality for getting users info;
 */
export default class GetUserAction extends Action {

  /**
   * Calls {@link ApiService} method to get user info than returns response.
   * @param {StateManager} stateManager - instance of {@link StateManager}.
   * @param {ApiService} apiService - instance of {@link ApiService}.
   */
  async apply(stateManager, apiService) {
    stateManager.mutate(new UserLoadingMutator(true));
    apiService.getUser().then(response => {
      stateManager.mutate(new UserDataMutator(response.user));
    }).catch(error => {
      stateManager.mutate(new UserLoadingError(error));
    }).finally(() => {
      stateManager.mutate(new UserLoadingMutator(false));
    });
  }
}
