import Action from '../action.js';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemsMutator from '../../mutators/get-item-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

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
  apply(stateManager, appService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    appService.uploadFile(this.parentId, this.file).then(() => {
      appService.getItems(this.parentId).then((files) => {
        stateManager.mutate(new ItemsMutator(files));
      }).catch((error) => {
        stateManager.mutate(new ItemLoadingErrorMutator(error));
      });
    });
    stateManager.mutate(new ItemLoadingMutator(false));
  }
}
