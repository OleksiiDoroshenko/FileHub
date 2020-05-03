import Component from '../../main/components/component.js';

/**
 * Main list item class.
 */
export default class ListItem extends Component {

  /**
   * Returns instance of {@link ListItem}.
   * @param {HTMLElement} container - container for rendering.
   * @param {Object} componentConfig - object with list item params.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);

    this.name = componentConfig.name;
    this.mimeType = componentConfig.mimeType;
    this.size = componentConfig.size;
    this.type = componentConfig.type;
    this.id = componentConfig.id;
    this.itemsAmount = componentConfig.itemsAmount;
  }

  /**
   * @inheritdoc
   * @private
   */
  _render() {
    const row = this.container.insertRow(-1);
    row.innerHTML = this._markup();
  }
}
