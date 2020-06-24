import Action from '../action.js';
import GetItemsAction from '../get-items';
import AddItemToDeletingListMutator from '../../mutators/add-item-to-deleting-list-mutator';
import RemoveItemFromDeletingListMutator from '../../mutators/remove-item-from-deleting-list-mutator';
import ItemsDeletingErrorMutator from '../../mutators/items-deleting-error-mutator';

/**
 * Sends request for deleting file to {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class DeleteItemAction extends Action {
  /**
   * Returns instance of {@link DeleteItemAction}.
   * @param {Object} model - model if list item {@link ListItem}.
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
    stateManager.mutate(new AddItemToDeletingListMutator(id));
    const method = this.model.type === 'folder' ? 'deleteFolder' : 'deleteFile';
    apiService[method](this.model).then(() => {
      if (stateManager.state.folderId === parentId) {
        stateManager.dispatch(new GetItemsAction({id: parentId, type: 'folder'}));
      }
    }).catch((error) => {
      stateManager.mutate(new ItemsDeletingErrorMutator(error));
    }).finally(() => {
      stateManager.mutate(new RemoveItemFromDeletingListMutator(id));
    });
  }
}
