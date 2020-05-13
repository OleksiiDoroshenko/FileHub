import Action from '../action.js';

/**
 * Provides functionality for getting users root folder id;
 */
export default class GetRootIdAction extends Action {
  /*  /!**
     * Returns instance of {@link GetRootIdAction}.
     * @param {function} handler - function that would be executed.
     *!/
    constructor(handler) {
      super();
      this.handler = handler;
    }*/

  /**
   * Calls {@link ApiService} method to get users root folder id and then executes handler.
   * @param {StateManager} stateManager - instance of {@link StateManager}.
   * @param {ApiService} apiService - instance of {@link ApiService}.
   */
  async apply(stateManager, apiService) {
    return await apiService.getRoot().then(response => {
      const id = response.folder.id;
      return id;
    }).catch(error => {
      alert(error.message);
    });
  }
}
