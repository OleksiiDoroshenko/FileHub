import Button from '../button';

/**
 * Implements html button for file upload rendering into another html element(container).
 */
export default class FileInputButton extends Button {

  /**
   * Class constructor.
   * @param {HTMLElement} container - container for element rendering.
   * @param {Object} componentConfig - initial configuration.
   * @param {string} componentConfig.text - button inner text;
   * @param {string} componentConfig.icon - inner button icon.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.addEventListener();
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
