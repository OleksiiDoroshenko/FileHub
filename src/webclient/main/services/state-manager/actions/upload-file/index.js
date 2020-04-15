import Action from '../action.js';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemsMutator from '../../mutators/get-item-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

export default class UploadFileAction extends Action {

  constructor(parentId, file) {
    super();
    this.parentId = parentId;
    this.file = file;
  }

  /**
   * @inheritDoc
   */
  apply(stateManager, appService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    appService.uploadFile(this.parentId, this.file).then(() => {
      appService.getItems(this.parentId).then(files => {
        stateManager.mutate(new ItemsMutator(files));
      }).catch(error => {
        stateManager.mutate(new ItemLoadingErrorMutator(error));
      });
    });
    stateManager.mutate(new ItemLoadingMutator(false));
  }
}
