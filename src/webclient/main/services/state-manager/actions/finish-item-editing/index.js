import Action from '../action.js';
import IsItemEditingMutator from '../../mutators/item-editing';

export default class FinishItemEditingAction extends Action {
  /**
   * @inheritdoc
   */
  apply(stateManager, appService) {
    const editingField = stateManager.state.editingField;
    const newItemName = editingField.editingItem.querySelector('.input').value;
    const itemId = editingField.id;
    appService.renameItem(itemId, newItemName).then(() => {
      editingField.editingItem.querySelector('span').innerText = newItemName;
      editingField.editingItem.querySelector('span').classList.remove('editing');
      editingField.editingItem.querySelector('.input').classList.add('editing');
    });
    stateManager.mutate(new IsItemEditingMutator(false));
  }
}
