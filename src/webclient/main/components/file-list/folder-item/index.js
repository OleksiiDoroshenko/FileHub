import ListItem from '../list-item';
import FileBrowserService from '../../../services/file-browser-service';

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
                <i class="glyphicon glyphicon-folder-close"></i>
            </td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.model.name}>
                <span><a href="#">${this.model.name}</a></span>
            </td>
            <td class="items">${this.model.itemsAmount} items</td>
            <td class="clickable">
            <div data-render="clickable">
                <i class="glyphicon glyphicon-upload" data-render="upload"></i>
                <div class="loading-state" data-render="loading-state"><div></div></div>
                <i class="glyphicon glyphicon-remove-circle" data-render="delete"></i>
            </div>
            </td>
    `;
  }

  _initInnerComponents() {
    this._deleteIcon = this.rootElement.querySelector('[data-render="delete"]');
  }


  /**
   * Calls method that returns file from user's device and calls handler with proper parameters.
   * @param {function} handler - function to be called.
   */
  addUploadFileHandler(handler) {
    const icon = this.rootElement.querySelector('[data-render="upload"]');
    icon.addEventListener('click', () => {
      new FileBrowserService().selectFile().then((file) => {
        handler(this.model, file);
      });
    });
  }
}

