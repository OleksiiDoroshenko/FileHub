import Component from '../component.js';

/**
 * Implements group of elements such as labels and inputs,
 * that can collect user information.
 */
export default class FormInput extends Component {

  /**
   * @inheritdoc.
   */
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.root = this.container.lastChild;
  }

  /**
   * @inheritdoc.
   */
  markup() {
    return `
         <div class="row">
            <label class="control-label col-sm-4" for=${this.id}>${this.labelText}</label>
            <div class="col-sm-8 input-filed">
                <input ${this.inputType} class="form-control" ${this.id} 
                placeholder=${this.placeHolder} name=${this.id}>
                <p class="text-danger">${this.warning}</p>
            </div>
        </div>
        `;
  }

  get value() {
    return this.root.querySelector('.form-control').value;
  }

  showWarning(text) {
    this.root.querySelector('.text-danger').innerText = text;
  }

  hideWarning() {
    this.root.querySelector('.text-danger').innerText = '';
  }

}
