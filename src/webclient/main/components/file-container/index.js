import createItem from '../file-explorer/itemFactory';
import Component from '../component.js';

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
    this.itemsRoot = this.container.querySelector('.table');
  }

  renderItems(items) {
    this.items = items;
    if (items.length > 0) {
      for (let item in items) {
        createItem(this.itemsRoot, item);
      }
    }
  }
}
