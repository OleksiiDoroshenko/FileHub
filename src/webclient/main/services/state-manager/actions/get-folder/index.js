import Action from '../action.js';
import FolderMutator from '../../mutators/folder-mutator';
import FolderLoadingErrorMutator from '../../mutators/folder-loading-error-mutator';
import FolderLoadingMutator from '../../mutators/folder-loading-mutator';

/**
 * Gets folder by id from {@link ApiService} and writes it into {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class GetFolderAction extends Action {
  /**
   * Returns instance of {@link GetFolderAction} class.
   * @param {string} id - folder id.
   */
  constructor(id) {
    super();
    this.id = id;
  }

  /**
   * @inheritdoc
   */
  async apply(stateManager, apiService) {
    stateManager.mutate(new FolderLoadingMutator(true));
    return apiService.getFolder(this.id)
      .then((response) => {
        stateManager.mutate(new FolderMutator(response.folder));
      }).catch((e) => {
        stateManager.mutate(new FolderLoadingErrorMutator(e));
      }).finally(() => {
        stateManager.mutate(new FolderLoadingMutator(false));
      });
  }
}
