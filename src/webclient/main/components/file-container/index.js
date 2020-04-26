import Component from '../component.js';
import FolderItem from './folder-item';
import FileItem from './file-item';

/**
 * Class for files table rendering.
 */
export default class FileContainer extends Component {
  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return `<table class="table" id="file-container"></table>`;
  }

  /**
   * @inheritdoc
   * @private
   */
  _initInnerComponents() {
    const root = this.container.querySelector('.table');
    this._createItem('folder', root, {name: 'Documents', itemsAmount: '2'});
    this._createItem('folder', root, {name: 'Images', itemsAmount: '2'});
    this._createItem('folder', root, {name: 'Videos', itemsAmount: '1'});
    this._createItem('file', root, {name: 'test.txt', mimeType: 'text', size: '20KB'});
  }

  /**
   * Returns instance of {@link FileItem} or {@link FolderItem}.
   * @param {string} type - item type, folder or file.
   * @param {HTMLElement} container - container for element rendering.
   * @param {FileConfig|FolderConfig} config - configuration for render item.
   * @return {FileItem|FolderItem}
   */
  _createItem(type, container, config) {
    switch (type) {
      case 'folder': {
        return new FolderItem(container, config);
      }
      case 'file': {
        return new FileItem(container, config);
      }
    }
  }
}
