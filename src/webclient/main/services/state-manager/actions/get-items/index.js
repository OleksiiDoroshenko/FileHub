import Action from '../action.js';
import ItemsMutator from '../../mutators/get-item-mutator';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

export default class GetItemsAction extends Action {
  /**
   * @inheritdoc
   */
  apply(stateManager, appService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    appService.getItems()
      .then(items => {
        console.log('Server response ' + items.length);
        stateManager.mutate(new ItemsMutator(items));
      }).catch(e => {
      stateManager.mutate(new ItemLoadingErrorMutator(e));
    });
    stateManager.mutate(new ItemLoadingMutator(false));
  }
}
