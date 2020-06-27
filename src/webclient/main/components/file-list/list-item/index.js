import Component from '../../component.js';

/**
 * Main list item class.
 */
export default class ListItem extends Component {
  editingClass = 'editing';

  /**
   * Returns instance of {@link ListItem}.
   * @param {HTMLElement} container - container for rendering.
   * @param {Object} componentConfig - object with list item params.
   * @param {Object} componentConfig.model - list item model.
   * @param {string} componentConfig.model.name - item name.
   * @param {string} componentConfig.model.mimeType - item mimeType.
   * @param {string} componentConfig.model.size - file size.
   * @param {string} componentConfig.model.type - item type.
   * @param {string} componentConfig.model.id - item id.
   * @param {string} componentConfig.model.itemsAmount - item amount of items in folder.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this._itemClicked = 0;
  }

  /**
   * @inheritdoc
   * @private
   */
  _render() {
    const row = this.container.insertRow(-1);
    row.innerHTML = this._markup();
    this.rootElement = row;
  }

  /**
   * Changes root element class list if item is uploading.
   * @param {boolean} value - process state.
   * @param {string} toggleClass - class for toggling.
   */
  isProcessing(value, toggleClass) {
    this.rootElement.classList.toggle(toggleClass, value);
  }

  /**
   * Adds listener for deleting icon.
   * @param {function} handler - function to be called.
   */
  addDeleteHandler(handler) {
    this._deleteIcon.addEventListener('click', () => {
      handler(this.model);
    });
  }

}
