import Action from '../action.js';
import FilesMutator from '../../mutators/FilesMutattor';

export default class GetFilesAction extends Action {
  /**
   *
   * @param {StateManager} stateManager
   * @param {AuthenticationService} apiService
   */
  apply(stateManager, apiService) {
    apiService.getItems().then(items => {
      stateManager.mutate(new FilesMutator(items));
    });
  }
}
