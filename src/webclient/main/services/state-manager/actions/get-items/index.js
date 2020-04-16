import Action from '../action.js';
import ItemsMutator from '../../mutators/get-item-mutator';
import ItemLoadingMutator from '../../mutators/item-loading-mutator';
import ItemLoadingErrorMutator from '../../mutators/item-loading-error-mutator';

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
  apply(stateManager, appService) {
    stateManager.mutate(new ItemLoadingMutator(true));
    appService.getItems(this.id)
        .then((items) => {
          console.log('Server response ' + items.length);
          stateManager.mutate(new ItemsMutator(items));
        }).catch((e) => {
          stateManager.mutate(new ItemLoadingErrorMutator(e));
        });
    stateManager.mutate(new ItemLoadingMutator(false));
  }
}
