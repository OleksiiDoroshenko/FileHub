import Component from '../component.js';

/**
 * Implements html button for file upload rendering into another html element(container).
 */
export default class FileInputButton extends Component {
  /**
   * @typedef {Object} ButtonConfig.
   * @param {string} text - html button inner text value.
   */

  /**
   * Class constructor.
   * @param {HTMLElement} container - container for element rendering.
   * @param {ButtonConfig} componentConfig - button configuration.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.addEventListener();
  }

  /**
   * @inheritdoc.
   */
  _markup() {
    return `
            <button class="btn btn-primary">${this.text}</button>
        `;
  }

  /**
   * @inheritdoc.
   */
  addEventListener() {
    this.container.lastChild.addEventListener('click', () => this._openFileBrowser());
  }

  /**
   * Opens standard browser for uploading files from driver.
   * @private
   */
  _openFileBrowser() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.click();
    input.addEventListener('change', () => {
      this._handler(input.files[0]);
    });
  }

  /**
   * Sets handler for uploading file event.
   * @param handler
   */
  set uploadFileHandler(handler) {
    this._handler = handler;
  }
}
