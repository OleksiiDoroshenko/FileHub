import Component from '../component.js';

/**
 * Implements html button rendering into another html element(container).
 */
export default class Button extends Component {
  /**
   * Returns instance of {@link Button};
   * @param {HTMLElement} container - container to render.
   * @param {Object} componentConfig - initial configuration.
   * @param {string} componentConfig.text - button inner text;
   * @param {string} componentConfig.icon - inner button icon.
   * @param {string} componentConfig.dataParam - data render param for getting element from code.
   * @param {string} componentConfig.innerContent - additional inner content.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
  }

  /**
   * @inheritdoc.
   */
  _markup() {
    return `<button data-render=${this.dataParam} class="btn btn-primary">
            <i class="glyphicon ${this._iconClass}"></i> ${this.text}
            </button>`;
  }

  /**
   * @inheritdoc.
   */
  addEventListener(event, handler) {
    this.rootElement.addEventListener(event, handler);
  }

  /**
   * Inserts bootstrap icon class into btn text.
   * @param {string} icon - bootstrap icon class.
   */
  set iconClass(iconClass) {
    this._iconClass = iconClass;
  }

  set innerContent(html) {
    this.text = html + this.text;
  }

  set isLoading(value) {
    if (value) {
      this.rootElement.classList.add(this._isLoadingClass);
    } else {
      this.rootElement.classList.remove(this._isLoadingClass);
    }
  }

  set isLoadingClass(cssClass) {
    this._isLoadingClass = cssClass;
  }
}
