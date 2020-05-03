import ListItem from '../../../../models/list-item';

/**
 * Table folder item.
 */
export default class FolderItem extends ListItem {
  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return `
            <td class="item-icon folder-icon"><i class="glyphicon glyphicon-triangle-right"></i>
                <i class="glyphicon glyphicon-folder-close"></i></td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.name}>
                <span><a href="#">${this.name}</a></span></td>
            <td class="items">${this.itemsAmount} items</td>
            <td class="clickable"><i onclick="uploadFile()" class="glyphicon glyphicon-upload"></i>
                <i class="glyphicon glyphicon-remove-circle"></i></td>
    `;
  }
}
