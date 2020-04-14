import Action from '../action.js';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemsMutator from '../../mutators/get-item-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

/**
 * Deletes item and gets up-to-date user files from {@link AppService} and writes it into {@link StateManager} state
 * by using {@link Mutator}.
 */
export default class DeleteItemAction extends Action {
  /**
   * Returns instance of {@link GetItemsAction} class.
   * @param {Object} item - item to be deleted.
   */
  constructor(item) {
    super();
    this.item = item;
  }

  /**
   * @inheritdoc
   */
  apply(stateManager, appService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    appService.deleteItem(this.item.id, this.item.type).then(() => {
      appService.getItems(this.item.parentId)
        .then((items) => {
          console.log('Server response ' + items.length);
          stateManager.mutate(new ItemsMutator(items));
        }).catch((e) => {
        stateManager.mutate(new ItemLoadingErrorMutator(e));
      });
    }).catch();
    stateManager.mutate(new ItemLoadingMutator(false));
  }
}
