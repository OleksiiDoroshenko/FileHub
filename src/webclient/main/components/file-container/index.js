import Component from '../component.js';
import FolderItem from './folder-item';
import FileItem from './file-item';

/**
 * Class for files table rendering.
 */
export default class FileContainer extends Component {


  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.items = componentConfig.items;
    this._render();
  }

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
    this.itemsRoot = this.container.querySelector('.table');
    this.renderItems();
  }

  set items(items) {
    this._items = items;
    this.renderItems();

  }

  get items() {
    return this._items;
  }

  renderItems() {
    if (this.items.length > 0) {
      for (let item of this.items) {
        this._createItem(this.itemsRoot, item);
      }
    }
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
}
