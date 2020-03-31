import Component from '../component.js';
import Button from '../button';

/**
 * Implements form row that contains button and link,
 * that can navigate user to another page.
 */
export default class FormActions extends Component {
  /**
   * @inheritdoc.
   */
  markup() {
    return `
            <div class="form-group nav-panel">
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
  initInnerComponents() {
    const btnRoot = this.container.querySelector('.nav-panel').firstChild;
    this.btn = new Button(btnRoot, {
      text: this.btnText,
      type: this.btnType,
    });
  }

  /**
   * @inheritdoc.
   */
  addEventListener(event, handler) {
    this.btn.addEventListener(event, handler);
  }
}
