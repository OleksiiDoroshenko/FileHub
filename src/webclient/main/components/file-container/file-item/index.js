import Component from '../../component.js';

/**
 * Table file item.
 */
export default class FileItem extends Component {
  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return `
       <tr>
            <td class="item-icon file-icon"><i class="glyphicon ${this.fileIcon}"></i></td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.name}>
                <span>${this.name}</span></td>
            <td class="items">${this.size}</td>
            <td class="clickable"><i class="glyphicon glyphicon-download"></i>
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
    const element = elementList[elementList.length - 1];
    element.addEventListener('dblclick', (element) => handler);
  }
}
