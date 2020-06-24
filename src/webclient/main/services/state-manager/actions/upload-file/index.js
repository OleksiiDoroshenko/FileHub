import Action from '../action.js';
import GetItemsAction from '../get-items';
import AddItemToUploadingListMutator from '../../mutators/add-item-to-uploading-list-mutator';
import RemoveItemToUploadingListMutator from '../../mutators/remove-item-from-uploading-list-mutator';
import ItemUploadingErrorMutator from '../../mutators/item-uploading-error-mutator';

/**
 * Sends request for uploading file to {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class UploadFileAction extends Action {
  /**
   * Returns instance of {@link UploadFileAction}.
   * @param {Object} model - model of folder where file should be loaded.
   * @param {File} file - file to be loaded.
   */
  constructor(model, file) {
    super();
    this.model = model;
    this.file = file;
  }

  /**
   * @inheritdoc
   */
  async apply(stateManager, apiService) {
    const id = this.model.id;
    stateManager.mutate(new AddItemToUploadingListMutator(id));
    apiService.uploadFile(this.model, this.file)
      .then(() => {
        if (stateManager.state.folderId === id) {
          stateManager.dispatch(new GetItemsAction({id: id, type: 'folder'}));
        }
      }).catch((error) => {
      stateManager.mutate(new ItemUploadingErrorMutator(error));
    }).finally(() => {
      stateManager.mutate(new RemoveItemToUploadingListMutator(id));
    });
  }
}
