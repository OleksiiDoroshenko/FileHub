import Action from '../action.js';
import GetItemsAction from '../get-items/index.js';
import AddItemToRenamingListMutator from '../../mutators/add-item-to-renaming-list-mutator/index.js';
import RemoveItemFromRenamingListMutator from '../../mutators/remove-item-from-renaming-list-mutator/index.js';
import ItemsRenamingErrorMutator from '../../mutators/item-renaming-error-mutator/index.js';

/**
 * Sends request for renaming item to {@link ApiService}
 */
export default class RenameItemAction extends Action {
  /**
   * Returns instance of {@link RenameItemAction} class.
   * @param {Object} model - model of list item {@link ListItem}.
   * @param {string} model.id - model id.
   * @param {string} model.parentId - model parentId.
   */
  constructor(model) {
    super();
    this.model = model;
  }

  /**
   * @inheritdoc
   */
  async apply(stateManager, apiService) {
    const id = this.model.id;
    const parentId = this.model.parentId;
    stateManager.mutate(new AddItemToRenamingListMutator(id));
    return apiService.renameItem(this.model).then(() => {
      if (stateManager.state.folderId === parentId) {
        stateManager.dispatch(new GetItemsAction(parentId));
      }
    }).catch((error) => {
      stateManager.mutate(new ItemsRenamingErrorMutator(error));
    }).finally(() => {
      stateManager.mutate(new RemoveItemFromRenamingListMutator(id));
    });
  }
}
