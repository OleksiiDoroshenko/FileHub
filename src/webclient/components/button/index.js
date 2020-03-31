import Component from '../component.js';

/**
 * Implements html button rendering into another html element(container).
 */
export default class Button extends Component {
  /**
   * @inheritdoc
   */
  markup() {
    return `
            <button class="btn btn-primary" ${this.type}>${this.text}</button>
        `;
  }

  /**
   * @inheritdoc.
   * */
  addEventListener(event, handler) {
    this.container.querySelector('.btn').addEventListener(event, handler);
  }

  set type(value) {
    this._type = value;
  }

  set text(value) {
    this._text = value;
  }

  get type() {
    return this._type;
  }

  get text() {
    return this._text;
  }
}
