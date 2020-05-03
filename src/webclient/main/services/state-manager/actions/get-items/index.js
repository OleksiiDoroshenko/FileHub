import Action from '../action.js';
import ItemsMutator from '../../mutators/get-item-mutator';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

export default class GetItemsAction extends Action {
  constructor(id) {
    super();
    this.id = id;
  }

  /**
   * @inheritdoc
   */
  apply(stateManager, appService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    appService.getItems(this.id)
        .then((response) => {
          stateManager.mutate(new ItemsMutator(response.items));
        }).catch((e) => {
          stateManager.mutate(new ItemLoadingErrorMutator(e));
        }).finally(() => {
          stateManager.mutate(new ItemLoadingMutator(false));
        });
  }
}
