import Component from '../component.js';
import createItem from '../file-explorer/itemFactory';

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
    createItem('folder', root, {name: 'Documents', itemsAmount: '2'});
    createItem('folder', root, {name: 'Images', itemsAmount: '2'});
    createItem('folder', root, {name: 'Videos', itemsAmount: '1'});
    createItem('file', root, {name: 'test.txt', mimeType: 'text', size: '20KB'});
  }
}
