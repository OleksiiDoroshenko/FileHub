import Action from '../action.js';
import ItemLoadingMutator from '../../mutators/items-loading-mutator';
import ItemsMutator from '../../mutators/items-mutator';
import ItemLoadingErrorMutator from '../../mutators/items-loading-error-mutator';

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
        apiService.getItems(this.parentId)
          .then((response) => {
            stateManager.mutate(new ItemsMutator(response.items));
          }).catch((e) => {
          stateManager.mutate(new ItemLoadingErrorMutator(e));
        });
      }).finally(() => {
      stateManager.mutate(new ItemLoadingMutator(false));
    });
  }
}
