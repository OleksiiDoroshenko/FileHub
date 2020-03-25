import Component from '../component.js';

/**
 * Implements html button rendering into another html element(container).
 */
export default class Button extends Component {
  constructor(container, componentConfig) {
    super(container, componentConfig);
  }

  /**
   * Returns representation of button in html.
   * @return {string} Button html code.
   */
  markup() {
    return `
            <button class="btn btn-primary" ${this.type}>${this.text}</button>
        `;
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
