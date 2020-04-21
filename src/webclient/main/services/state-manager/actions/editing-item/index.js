import Action from '../action.js';
import IsItemEditingMutator from '../../mutators/item-editing';

export default class EditItemAction extends Action {
  constructor(id, changeFiled) {
    super();
    this.id = id;
    this.changeFiled = changeFiled;
  }

  /**
   * @inheritdoc
   */
  apply(stateManager, appService) {
    stateManager.mutate(new IsItemEditingMutator(true));
    const newItemName = this.changeFiled.querySelector('.input').value;
    appService.renameItem(this.id, newItemName).then(() => {
      this.changeFiled.field.querySelector('.span').innerText = newItemName;
      this.changeFiled.querySelector('span').classList.remove('editing');
      this.changeFiled.querySelector('.input').classList.add('editing');
    });
    stateManager.mutate(new IsItemEditingMutator(false));
  }
}
