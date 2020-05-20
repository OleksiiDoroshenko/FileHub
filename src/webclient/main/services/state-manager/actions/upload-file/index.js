import Action from '../action.js';
import GetItemsAction from '../get-items';
import ItemsLoadingErrorMutator from '../../mutators/items-loading-error-mutator';
import AddItemToUploadingListMutator from '../../mutators/add-item-to-uploading-list-mutator';
import RemoveItemToUploadingListMutator from '../../mutators/remove-item-to-uploading-list-mutator';

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
    stateManager.mutate(new AddItemToUploadingListMutator(this.parentId));
    apiService.uploadFile(this.parentId, this.file)
      .then(() => {
        stateManager.dispatch(new GetItemsAction(this.parentId));
      }).catch(error => {
      stateManager.mutate(new ItemsLoadingErrorMutator(error));
    }).finally(() => {
      stateManager.mutate(new RemoveItemToUploadingListMutator(this.parentId));
    });
  }
}
