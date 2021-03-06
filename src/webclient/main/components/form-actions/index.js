import Component from '../component.js';
import Button from '../button';

/**
 * Implements login-form row that contains button and link,
 * that can navigate user to another page.
 */
export default class FormActions extends Component {
  /**
   * Returns instance of {@link FormActions} class.
   * @param {HTMLElement} container - container for element rendering.
   * @param {Object} componentConfig - initial configuration.
   * @param {string} componentConfig.linkHref - link to another page.
   * @param {string} componentConfig.linkText - text of the link.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
  }

  /**
   * @inheritdoc.
   */
  _markup() {
    return `
            <div class="form-group nav-panel" data-render="nav-panel">
                <div class="col-sm-offset-4 col-sm-2">
                </div>
                <div class="col-sm-offset-1 col-sm-5 nav-link">
                    <a href=${this.linkHref}>${this.linkText}</a>
                </div>
            </div>
        `;
  }

  /**
   * @inheritdoc.
   */
  _initInnerComponents() {
    const btnRoot = this.container.querySelector('[data-render="nav-panel"]').querySelector('div');
    this._btn = new Button(btnRoot, {
      text: this.btnText,
    });
  }

  /**
   * @param {string} event - event to handle.
   * @param {Function} handler - function which should be called whet event comes.
   */
  addEventListener(event, handler) {
    this._btn.addEventListener(event, handler);
  }
}
