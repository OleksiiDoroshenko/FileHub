import createItem from './itemFactory';
import Component from '../component.js';

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
        createItem(this.itemsRoot, item);
      }
    }
  }
}
