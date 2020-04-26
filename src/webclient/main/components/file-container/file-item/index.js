import Component from '../../component.js';

/**
 * Table file item.
 */
export default class FileItem extends Component {

  /**
   * Returns instance of {@link FileItem}.
   * @param container
   * @param componentConfig
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this._setFileIcon(componentConfig.mimeType);
  }

  /**
   * @inheritdoc
   * @private
   */
  _markup() {
    return `
            <td class="item-icon file-icon"><i class='glyphicon '></i></td>
            <td class="name" data-toggle="tooltip" data-placement="top" title=${this.name}>
                <span>${this.name}</span></td>
            <td class="items">${this.size}</td>
            <td class="clickable"><i class="glyphicon glyphicon-download"></i>
                <i class="glyphicon glyphicon-remove-circle"></i></td>
    `;
  }

  /**
   * @inheritdoc
   * @private
   */
  _render() {
    const row = this.container.insertRow(-1);
    row.innerHTML = this._markup();
  }

  /**
   * Sets correct file icon depends on its mime type.
   * @param {string} mimeType - files mimeType.
   * @private
   */
  _setFileIcon(mimeType) {
    const icons = {
      img: 'glyphicon-picture',
      text: 'glyphicon-book',
      video: 'glyphicon-film',
      audio: 'glyphicon-music',
    };
     this.container.querySelector('.file-icon .glyphicon').classList.add(icons[mimeType]);
  }
}
