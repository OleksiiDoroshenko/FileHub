import Component from '../../component.js';
import ActionIcon from '../action-icon';

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
            <td class="clickable"></td>
        </tr>
    `;
  }

  /**
   * @inheritdoc
   * @private
   */
  _initInnerComponents() {
    const lastChild = this.container.lastChild.lastChild;
    const actionIconsRoot = lastChild.querySelector('.clickable');
    this.downloadIcon = new ActionIcon(actionIconsRoot, {type: 'download'});
    this.deleteIcon = new ActionIcon(actionIconsRoot, {type: 'delete'});
  }

  /**
   * Adds handler for item delete event.
   * @param {function} handler - function that should be called when item delete event comes.
   */
  onDelete(handler) {
    this.deleteIcon.addEventListener('click', () => {
      const self = {
        id: this.id,
        parentId: this.parentId,
        type: 'file',
      };
      handler(self);
    });
  }

  /**
   * @inheritdoc
   * @private
   */
  _render() {
    const row = this.container.insertRow(-1);
    row.innerHTML = this._markup();
  }
}
