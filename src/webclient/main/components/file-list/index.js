import Component from '../component.js';
import FolderItem from './folder-item';
import FileItem from './file-item';

/**
 * Class for files table rendering.
 */
export default class FileList extends Component {
  _uploadingItems = [];

  /**
   * Returns instance of {@link FileList}.
   * @param {HTMLElement} container - container for element rendering.
   * @param {Object} componentConfig - object that contains user items.
   * @param {[ListItem]} componentConfig.items = user items to render.
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
    return `<table class="table" id="file-container" data-render="table"></table>`;
  }

  /**
   * @inheritdoc
   * @private
   */
  _initInnerComponents() {
    this.itemsRoot = this.container.querySelector('[data-render="table"]');
  }

  /**
   * Sets user items, and calls method for their rendering.
   * @param {[ListItem]} items - user items.
   */
  set items(items) {
    this._items = items;
    this._renderItems();
  }

  set uploadingItems(items) {
    this._uploadingItems = items;
    this._renderItems();
  }

  /**
   * Returns user items.
   * @return {ListItem}
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
      if (Array.isArray(this._items)) {
        this._items.forEach((item) => {
            this._createItem(this.itemsRoot, item);
          },
        );
      }
    }
  }

  /**
   * Returns instance of {@link FileItem} or {@link FolderItem}.
   * @param {HTMLElement} container - container for element rendering.
   * @param {ListItem} model - list item.
   * @return {FileItem|FolderItem}
   */
  _createItem(container, model) {
    switch (model.type) {
      case
      'folder': {
        const folderItem = new FolderItem(container, {model});
        folderItem.addUploadFileHandler(this._onUploadClickHandler);
        if (this._uploadingItems.includes(model.id)) {
          folderItem.isUploading = true;
        }
        return folderItem;
      }
      case
      'file': {
        return new FileItem(container, {model});
      }
    }
  }

  /**
   * Renders loading message into the table.
   */
  showLoadingMessage() {
    this.itemsRoot.innerHTML = 'Loading...';
  }

  /**
   * Renders error message into the table.
   */
  showError(error) {
    this.itemsRoot.innerHTML = 'Sorry something went wrong, please try later';
  }

  set onUploadClick(handler) {
    this._onUploadClickHandler = handler;
  }
}
