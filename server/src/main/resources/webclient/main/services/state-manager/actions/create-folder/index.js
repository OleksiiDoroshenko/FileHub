import Action from '../action.js';
import FolderCreatingErrorMutator from '../../mutators/folder-creating-error-mutator/index.js';
import FolderCreatingMutator from '../../mutators/folder-creating-mutator/index.js';
import GetItemsAction from '../get-items/index.js';

/**
 * Sends request for creating new folder to the {@link ApiService}.
 */
export default class CreateFolderAction extends Action {
  /**
   * Returns instance of {@link CreateFolderAction} class.
   * @param {Object} model - {@link ListItem} model.
   *
   */
  constructor(model) {
    super();
    this.model = model;
  }

  /**
   * @inheritdoc
   * @return {Promise<string>} - server response.
   */
  async apply(stateManager, apiService) {
    stateManager.mutate(new FolderCreatingMutator(true));
    return apiService.createFolder(this.model)
      .then((response) => {
        const id = this.model.id;
        if (stateManager.state.folderId === id) {
          stateManager.dispatch(new GetItemsAction(id));
        }
        return response.id;
      }).catch((e) => {
        stateManager.mutate(new FolderCreatingErrorMutator(e));
      }).finally(() => {
        stateManager.mutate(new FolderCreatingMutator(false));
      });
  }
}
