import Component from '../component.js';

/**
 * Implements group of elements such as labels and inputs,
 * that can collect user information.
 */
export default class FormRow extends Component {
  /**
   * @typedef {[string]} FormRowConfig.
   * @param {string} id - html input 'id' attribute value.
   * @param {string} labelText - html label text.
   * @param {string} inputType - html input 'type' attribute value.
   * @param {string} placeHolder - html input 'placeHolder' attribute value.
   * @param {string} warning - html 'p' tag inner text .
   */

  /**
   * Class constructor.
   * @param {HTMLElement} container - container for element rendering.
   * @param {FormRowConfig} componentConfig - initial configuration of row form.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.root = this.container.lastChild;
  }

  /**
   * @inheritDoc
   */
  markup() {
    return `
         <div class="row">
            <label class="control-label col-sm-4" for=${this.id}>${this.labelText}</label>
            <div class="col-sm-8 input-filed">
                <input type=${this.inputType} class="form-control" ${this.id} 
                placeholder=${this.placeHolder} name=${this.id}>
                <p class="text-danger">${this.warning}</p>
            </div>
        </div>
        `;
  }

  /**
   * Returns current html input value.
   * @return {string} input value.
   */
  get value() {
    return this.root.querySelector('.form-control').value;
  }

  /**
   * Renders waring text in the current row.
   * <p> Text is placed under html input.
   * @param {string} text - text to render.
   */
  showWarning(text) {
    this.root.querySelector('.text-danger').innerText = text;
  }

  /**
   * Deletes warning text from current row.
   */
  hideWarning() {
    this.root.querySelector('.text-danger').innerText = '';
  }
}
