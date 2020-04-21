import Action from '../action.js';
import SetEditingItemMutator from '../../mutators/set-editing-item';
import IsItemEditingMutator from '../../mutators/item-editing';

export default class SetEditingItemAction extends Action {
  /**
   * Returns instance of {@link SetEditingItemAction}.
   * @param {string} id - item id that will be renamed.
   * @param {Object} editingItem - item that will be renamed.
   */
  constructor(id, editingItem) {
    super();
    this.id = id;
    this.editingItem = editingItem;
  }

  /**
   * @inheritdoc
   */
  apply(stateManager, appService) {
    stateManager.mutate(new IsItemEditingMutator(true));
    stateManager.mutate(new SetEditingItemMutator({id: this.id, editingItem: this.editingItem}));
  }
}
