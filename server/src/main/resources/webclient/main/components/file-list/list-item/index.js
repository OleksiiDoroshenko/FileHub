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
    this._selected = false;
    this._editing = false;
    this._initInnerComponents();
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

  /**
   * Returns value if of current selected state.
   * @return {boolean} - current value of item selected state.
   */
  get selected() {
    return this._selected;
  }

  /**
   * Marks item to be in selected state or not.
   * @param {boolean} value - shows if current item should be shifted to the selected state.
   */
  set selected(value) {
    this._selected = value;
  }

  /**
   * Returns value if of current editing state.
   * @return {boolean} - current value of item editing state.
   */
  get editing() {
    return this._editing;
  }

  /**
   * Shifts item to the editing state.
   * @param {boolean} value - shows if current item should be shifted to the editing state.
   */
  set editing(value) {
    this._editing = value;
    this.rootElement.classList.toggle(this.editingClass, value);
    if (value) {
      this.rootElement.querySelector('[data-render="name-input"]').focus();
    }
  }

  /**
   * Sets handler that should be called when item was clicked.
   * @param {function} handler - function that should be called.
   */
  onClickHandler(handler) {
    this.rootElement.addEventListener('click', (event) => {
        handler(this, event);
      },
    );
    this.rootElement.addEventListener('blur', () => {
        this.selected = false;
      },
    );
  }

  /**
   * Sets handler that should be called when item should be renamed.
   * @param {function} handler - function that should be called.
   */
  onRename(handler) {
    const input = this.rootElement.querySelector('[data-render="name-input"]');
    input.addEventListener('change', () => {
      const newName = input.value;
      if (newName.length > 0) {
        const model = this.model;
        model.name = newName;
        handler(model);
      }
    });
    input.addEventListener('blur', () => {
      this.editing = false;
    });
  }
}
