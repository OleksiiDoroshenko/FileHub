import Action from '../action.js';
import AddItemToDownloadingListMutator from '../../mutators/add-item-to-download-list-mutator';
import RemoveItemFromDownloadingListMutator from '../../mutators/remove-item-from-downloading-list-mutator';
import ItemsDownloadingErrorMutator from '../../mutators/items-downloading-error-mutator';

export default class GetFileAction extends Action {

  constructor(fileId, downloadService) {
    super();
    this.fileId = fileId;
    this.downloadService = downloadService;
  }

  async apply(stateManager, apiService) {
    stateManager.mutate(new AddItemToDownloadingListMutator(this.fileId));
    apiService.getItems(this.id)
      .then((response) => {
        this.downloadService().download(response.file);
      }).catch((e) => {
      stateManager.mutate(new ItemsDownloadingErrorMutator(e));
    }).finally(() => {
      stateManager.mutate(new RemoveItemFromDownloadingListMutator(this.fileId));
    });
  }
}
