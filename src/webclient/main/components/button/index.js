import Component from '../component.js';

/**
 * Implements html button rendering into another html element(container).
 */
export default class Button extends Component {
  /**
   * @typedef {Object} ButtonConfig.
   * @param {string} type - html button attribute 'type' value.
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
   * @inheritDoc
   */
  markup() {
    return `
            <button class="btn btn-primary" ${this.type}>${this.text}</button>
        `;
  }
}
