import createItem from './itemFactory';
import Component from '../component.js';

/**
 * Class for files table rendering.
 */
export default class FileContainer extends Component {
  /**
   * @inheritdoc
   */
  constructor(container, componentConfig) {
    super(container, {});
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
  }

  /**
   * Sets handler for item delete event.
   * @param {function} handler - function that should be called when item is deleted.
   */
  onItemDelete(handler) {
    this.onitemDeleteHandler = handler;
  }

  /**
   * Sets items filed value and calls render method.
   * @param {[Object]}items
   */
  set items(items) {
    this._items = items;
    this.renderItems();
  }

  /**
   * Returns user items.
   * @return {[Object]}
   */
  get items() {
    return this._items;
  }

  /**
   * Renders items into table.
   */
  renderItems() {
    this.itemsRoot.innerHTML = '';
    if (this.items.length > 0) {
      for (const item of this.items) {
        const listItem = createItem(this.itemsRoot, item);
        listItem.onDelete(this.onitemDeleteHandler);
      }
    }
  }
}
