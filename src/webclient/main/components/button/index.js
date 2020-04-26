import Component from '../component.js';

/**
 * Implements html button rendering into another html element(container).
 */
export default class Button extends Component {
  /**
   * @typedef {Object} ButtonConfig.
   * @param {string} text - html button inner text value.
   */

  /**
   * Class constructor.
   * @param {HTMLElement} container - container for element rendering.
   * @param {{text: string, type: string}} componentConfig - button configuration.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
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
  addEventListener(event, handler) {
    this.container.querySelector('.btn').addEventListener(event, handler);
  }

  set icon(value) {

    this.text = `<i class="glyphicon ${this._getIcon(value)}"></i> ${this.text}`;
  }

  _getIcon(value) {
    const icons = {
      upload: 'glyphicon-upload',
      create: 'glyphicon-plus',
    };
    return icons[value];
  }
}
