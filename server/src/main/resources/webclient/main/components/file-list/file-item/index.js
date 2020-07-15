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
                <input data-render="name-input" value=${this.model.name}>
            </td>
            <td class="items">${this.model.size}</td>
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
  }

  /**
   * Sets correct file icon depends on its mime type.
   * @param {string} mimeType - files mimeType.
   * @return {string} bootstrap icon class.
   * @private
   */
  _getFileIcon(mimeType) {
    const icons = {
      img: 'glyphicon-picture',
      text: 'glyphicon-book',
      video: 'glyphicon-film',
      audio: 'glyphicon-music',
    };
    return icons[mimeType] ? icons[mimeType] : 'glyphicon-file';
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
