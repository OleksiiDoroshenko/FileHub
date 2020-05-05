import ListItem from '../../../../models/list-item';

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
            <td class="item-icon file-icon"><i class='glyphicon ${this._getFileIcon(this.model.mimeType)}'></i></td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.model.name}>
                <span>${this.model.name}</span></td>
            <td class="items">${this.model.size}</td>
            <td class="clickable"><i class="glyphicon glyphicon-download"></i>
                <i class="glyphicon glyphicon-remove-circle"></i></td>
    `;
  }

  /**
   * Sets correct file icon depends on its mime type.
   * @param {string} mimeType - files mimeType.
   * @returns {string} bootstrap icon class.
   * @private
   */
  _getFileIcon(mimeType) {
    const icons = {
      img: 'glyphicon-picture',
      text: 'glyphicon-book',
      video: 'glyphicon-film',
      audio: 'glyphicon-music',
    };
    return icons[mimeType];
  }
}
