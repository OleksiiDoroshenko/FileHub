import Component from '../../component.js';
import ActionIcon from '../action-icon';

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
                <span><a href="#">${this.name}</a></span></td>
            <td class="items">${this.itemsAmount} items</td>
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
    this.uploadIcon = new ActionIcon(actionIconsRoot, {type: 'upload'});
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
        type: 'folder',
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
