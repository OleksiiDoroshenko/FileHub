import Component from '../component.js';
import FolderItem from './folder-item';
import FileItem from './file-item';

/**
 * Class for files table rendering.
 */
export default class FileList extends Component {
  _uploadingItems = new Set();
  _deletingItems = new Set();
  _downloadingItems = new Set();
  _renamingItems = new Set();
  _editingItemId = null;

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

  /**
   * Sets user items, and calls method for their rendering.
   * @param {[ListItem]} items - user items.
   */
  set uploadingItems(items) {
    this._uploadingItems = items;
    this._renderItems();
  }

  /**
   * Sets deleting items, and calls method for their rendering.
   * @param {[ListItem]} items - user items.
   */
  set deletingItems(items) {
    this._deletingItems = items;
    this._renderItems();
  }

  /**
   * Sets downloading items, and calls method for their rendering.
   * @param {[ListItem]} items - user items.
   */
  set downloadingItems(items) {
    this._downloadingItems = items;
    this._renderItems();
  }

  /**
   * Sets renaming items, and calls method for their rendering.
   * @param {[ListItem]} items - items to be renamed.
   */
  set renamingItems(items) {
    this._renamingItems = items;
    this._renderItems();
  }

  set editingItemId(value) {
    this._editingItemId = value;
    this._renderItems();
  }

  /**
   * Returns user items.
   * @return {ListItem[]}
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
    let item;
    switch (model.type) {
      case
      'folder': {
        item = new FolderItem(container, {model});
        item.addUploadFileHandler(this._onUploadClickHandler);
        item.onNameDoubleClick(this._onFolderNameDoubleClickHandler);
        break;
      }
      case
      'file': {
        item = new FileItem(container, {model});
        item.addDownloadHandler(this._onDownloadHandler);
        break;
      }
    }
    if (this._uploadingItems.has(model.id)) {
      item.isProcessing(true, 'file-uploading');
    }
    if (this._deletingItems.has(model.id)) {
      item.isProcessing(true, 'file-deleting');
    }
    if (this._downloadingItems.has(model.id)) {
      item.isProcessing(true, 'file-downloading');
    }
    if (this._editingItemId === model.id) {
      item.editing = true;
      this._editingItemId = null;
    }
    item.addDeleteHandler(this._onDeleteHandler);
    item.onClickHandler(this._onItemClickHandler);
    item.onRename(this._onRenameHandler);
    return item;
  }

  /**
   * Renders loading message into the table.
   */
  showLoadingMessage() {
    this.itemsRoot.innerHTML = 'Loading...';
  }

  /**
   * Sets upload file handler.
   * @param {function} handler - handler;
   */
  onUploadClick(handler) {
    this._onUploadClickHandler = handler;
  }

  /**
   * Sets delete item handler.
   * @param {function} handler - handler;
   */
  onDelete(handler) {
    this._onDeleteHandler = handler;
  }

  /**
   * Sets download file handler.
   * @param {function} handler - handler;
   */
  onDownload(handler) {
    this._onDownloadHandler = handler;
  }

  /**
   * Sets double click folder name  handler.
   * @param {function} handler - handler;
   */
  onFolderNameDoubleClick(handler) {
    this._onFolderNameDoubleClickHandler = handler;
  }

  /**
   * Sets item click handler.
   * @param {function} handler - handler;
   */
  onItemClick(handler) {
    this._onItemClickHandler = handler;
  }

  /**
   * Sets rename item handler.
   * @param {function} handler - handler;
   */
  onRename(handler) {
    this._onRenameHandler = handler;
  }
}
