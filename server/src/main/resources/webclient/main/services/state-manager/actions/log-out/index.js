import Action from '../action.js';

/**
 * Sends request for logging out to {@link ApiService} state.
 */
export default class LogOutAction extends Action {

  /**
   * @inheritdoc.
   * */
  async apply(stateManager, apiService) {
    apiService.logOut();
  }
}
