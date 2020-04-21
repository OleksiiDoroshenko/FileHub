import Component from '../../component.js';

/**
 * Table folder item.
 */
export default class FolderItem extends Component {
  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return `
        <tr>
            <td class="item-icon folder-icon"><i class="glyphicon glyphicon-triangle-right"></i>
                <i class="glyphicon glyphicon-folder-close"></i></td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.name}>
                <span><a href="#">${this.name}</a></span>
                <input class="input editing" value=${this.name}>
            </td>
            <td class="items">${this.itemsAmount} items</td>
            <td class="clickable"><i onclick="uploadFile()" class="glyphicon glyphicon-upload"></i>
                <i class="glyphicon glyphicon-remove-circle"></i></td>
        </tr>
    `;
  }

  /**
   * @inheritdoc
   * @private
   */
  _render() {
    const row = this.container.insertRow(-1);
    row.innerHTML = this._markup();
  }

  onRename(handler) {
    const elementList = this.container.getElementsByClassName('name');
    const nameField = elementList[elementList.length - 1];
    nameField.addEventListener('dblclick', () => handler(this.id, nameField));
    nameField.addEventListener('change', () => handler(this.id, nameField));
  }
}
