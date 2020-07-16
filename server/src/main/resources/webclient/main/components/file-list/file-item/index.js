import ListItem from '../list-item/index.js';

/**
 * Table file item.
 */
export default class FileItem extends ListItem {
  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return `
            <td class="item-icon file-icon">
            <i class='glyphicon ${this._getFileIcon(this.model.mimeType)}'></i>
            </td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.model.name}>
                <span>${this.model.name}</span>
                <input data-render="name-input">
            </td>
            <td class="items">${this._calculateSize()}</td>
            <td class="clickable">
                <div data-render="clickable">
                    <i class="glyphicon glyphicon-download" data-render="download"></i>
                    <div class="loading-state" data-render="loading-state"><div></div></div>
                    <i class="glyphicon glyphicon-remove-circle" data-render="delete"></i>
                </div>   
            </td>
            `;
  }

  /**
   * @inheritoc
   * @private
   */
  _initInnerComponents() {
    this.rootElement.setAttribute('tabindex', 1);
    this._deleteIcon = this.rootElement.querySelector('[data-render="delete"]');

    this._input = this.rootElement.querySelector('[data-render="name-input"]');
    this._input.setAttribute('value', this.model.name);
  }

  /**
   * Sets correct file icon depends on its mime type.
   * @param {string} mimeType - files mimeType.
   * @return {string} bootstrap icon class.
   * @private
   */
  _getFileIcon(mimeType) {
    const type = mimeType.split('/')[0];
    const icons = {
      img: 'glyphicon-picture',
      text: 'glyphicon-book',
      video: 'glyphicon-film',
      audio: 'glyphicon-music',
    };
    return icons[type] ? icons[type] : 'glyphicon-file';
  }

  /**
     * Converts file size to general view.
     * @param {number} size - file seze.
     * @return {string} converted string.
     * @private
     */
    _calculateSize() {
      const size = this.model.size;
      if (size === 0) return '0 Bytes';
      const k = 1024;
      const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
      const i = Math.floor(Math.log(size) / Math.log(k));
      return parseFloat((size / Math.pow(k, i)).toFixed(0)) + ' ' + sizes[i];
    }

  /**
   * Adds listener for clicking downloading icon.
   * @param {function} handler - function to be called.
   */
  addDownloadHandler(handler) {
    const icon = this.rootElement.querySelector('[data-render="download"]');
    icon.addEventListener('click', () => {
      handler(this.model);
    });
  }
}
