import Action from '../action.js';
import ItemsMutator from '../../mutators/items-mutator';
import ItemLoadingMutator from '../../mutators/items-loading-mutator';
import ItemsLoadingErrorMutator from '../../mutators/items-loading-error-mutator';

export default class GetItemsAction extends Action {
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
        stateManager.mutate(new ItemsMutator(response.items));
      }).catch((e) => {
      stateManager.mutate(new ItemsLoadingErrorMutator(e));
    }).finally(() => {
      stateManager.mutate(new ItemLoadingMutator(false));
    });
  }
}
