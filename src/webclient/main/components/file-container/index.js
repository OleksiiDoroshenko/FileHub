import Component from '../component.js';
import FolderItem from './folder-item';
import FileItem from './file-item';

/**
 * Class for files table rendering.
 */
export default class FileContainer extends Component {

  /**
   * Returns instance of {@link FileContainer}.
   * @param {HTMLElement} container - container for element rendering.
   * @param {Object} componentConfig - object that contains user items.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.items = componentConfig.items;
    this._renderItems();
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
  }

  /**
   * Sets user items, and calls method for their rendering.
   * @param items
   */
  set items(items) {
    this._items = items;
    this._renderItems();
  }

  /**
   * Returns user items.
   * @returns {Item}
   */
  get items() {
    return this._items;
  }

  /**
   * Renders user items into the table.
   */
  _renderItems() {
    if (this.itemsRoot !== undefined) {
      this.itemsRoot.innerHTML = '';
      if (this._items !== undefined) {
        this._items.forEach(item => {
            this._createItem(this.itemsRoot, item);
          },
        );
      }
    }
  }

  /**
   * Returns instance of {@link FileItem} or {@link FolderItem}.
   * @param {HTMLElement} container - container for element rendering.
   * @param {Item} item - configuration for render item.
   * @return {FileItem|FolderItem}
   */
  _createItem( container, item) {
    switch (item.type) {
      case
      'folder': {
        return new FolderItem(container, item.config);
      }
      case
      'file': {
        return new FileItem(container, item.config);
      }
    }
  }
}
