import Action from '../action.js';
import ItemLoadingMutator from '../../mutators/items-loading-mutator';
import GetItemsAction from '../get-items';

/**
 * Sends request for uploading file to {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class UploadFileAction extends Action {
  /**
   * Returns instance of {@link UploadFileAction}.
   * @param {string} parentId - folder id where file will be loaded.
   * @param {File} file - file to be loaded.
   */
  constructor(parentId, file) {
    super();
    this.parentId = parentId;
    this.file = file;
  }

  /**
   * @inheritdoc
   */
  async apply(stateManager, apiService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    apiService.uploadFile(this.parentId, this.file)
      .then(() => {
        stateManager.dispatch(new GetItemsAction(this.parentId));
      }).catch(error => {
      stateManager.mutate(new ItemUploadnigErrorMutator(error));
    }).finally(() => {
      stateManager.mutate(new ItemLoadingMutator(false));
    });
  }
}
