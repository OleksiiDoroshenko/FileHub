import Action from '../action.js';
import GetItemsAction from '../get-items';
import AddItemToDeletingListMutator from '../../mutators/add-item-to-deleting-list-mutator';
import RemoveItemFromDeletingListMutator from '../../mutators/remove-item-from-deleting-list-mutator';
import ItemsDeletingErrorMutator from '../../mutators/items-deleting-error-mutator';

export default class DeleteItemAction extends Action {

  constructor(model) {
    super();
    this.itemModel = model;
  }

  async apply(stateManager, apiService) {
    const id = this.itemModel.id;
    stateManager.mutate(new AddItemToDeletingListMutator(id));
    const method = this.itemModel.type === 'folder' ? 'deleteFolder' : 'deleteFile';
    apiService[method](id).then(() => {
        stateManager.dispatch(new GetItemsAction(this.itemModel.parentId));
      }).catch(error => {
      stateManager.mutate(new ItemsDeletingErrorMutator(error));
    }).finally(() => {
      stateManager.mutate(new RemoveItemFromDeletingListMutator(id));
    });
  }
}
