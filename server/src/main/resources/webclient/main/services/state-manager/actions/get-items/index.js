import Action from '../action.js';
import ItemsMutator from '../../mutators/items-mutator/index.js';
import ItemLoadingMutator from '../../mutators/items-loading-mutator/index.js';
import ItemsLoadingErrorMutator from '../../mutators/items-loading-error-mutator/index.js';
import FolderIdMutator from '../../mutators/folder-id-mutator/index.js';

/**
 * Gets up-to-date user files from {@link AppService} and writes it into {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class GetItemsAction extends Action {
  /**
   * Returns instance of {@link GetItemsAction} class.
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
    stateManager.mutate(new ItemLoadingMutator(true));
    apiService.getItems(this.id)
      .then((response) => {
        stateManager.mutate(new FolderIdMutator(this.id));
        const folders = response.folders;
        const files = response.files;
        stateManager.mutate(new ItemsMutator(folders.concat(files)));
      }).catch((e) => {
      stateManager.mutate(new ItemsLoadingErrorMutator(e));
    }).finally(() => {
      stateManager.mutate(new ItemLoadingMutator(false));
    });
  }
}
