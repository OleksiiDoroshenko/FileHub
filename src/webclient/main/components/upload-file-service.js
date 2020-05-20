export default class UploadFileService {


  constructor(handler) {
    this.uploadFileHandler = handler;
  }

  addUploadFunctionality(id, component) {
    component.addEventListener('click', () => this._openFileBrowser(id));
  }

  /**
   * Opens standard browser for uploading files from driver.
   * @private
   */
  _openFileBrowser(id) {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.click();
    input.addEventListener('change', () => {
      this._handler(id, input.files[0]);
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
