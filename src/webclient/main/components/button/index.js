import Component from '../component.js';

/**
 * Implements html button rendering into another html element(container).
 */
export default class Button extends Component {
  /**
   * @inheritdoc.
   */
  _markup() {
    return `<button class="btn btn-primary">${this.text}</button>`;
  }

  /**
   * @inheritdoc.
   */
  addEventListener(event, handler) {
    this.container.querySelector('.btn').addEventListener(event, handler);
  }

  /**
   * Inserts bootstrap icon class into btn text.
   * @param {string} icon - bootstrap icon class.
   */
  set icon(icon) {
    this.text = `<i class="glyphicon ${icon}"></i> ${this.text}`;
  }

}
