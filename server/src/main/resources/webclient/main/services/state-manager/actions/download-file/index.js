import Action from '../action.js';
import AddItemToDownloadingListMutator from '../../mutators/add-item-to-download-list-mutator/index.js';
import RemoveItemFromDownloadingListMutator from '../../mutators/remove-item-from-downloading-list-mutator/index.js';
import ItemsDownloadingErrorMutator from '../../mutators/items-downloading-error-mutator/index.js';

/**
 * Sends request for downloading file to {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class DownloadFileAction extends Action {
  /**
   * Returns instance of {@link DownloadFileAction}.
   * @param {Object} model - model of file to be downloaded.
   * @param {DownloadFileService} downloadFileService - instance of {@link DownloadFileService}.
   */
  constructor(model, downloadFileService) {
    super();
    this.model = model;
    this.downloadFileService = downloadFileService;
  }

  /**
   * @inheritdoc
   */
  async apply(stateManager, apiService) {
    const id = this.model.id;
    stateManager.mutate(new AddItemToDownloadingListMutator(id));
    return apiService.getFile(id)
      .then((file) => {
        this.downloadFileService.download(file, this.model.name);
      }).catch(error => {
        stateManager.mutate(new ItemsDownloadingErrorMutator(error));
      }).finally(() => {
        stateManager.mutate(new RemoveItemFromDownloadingListMutator(id));
      });
  }
}
