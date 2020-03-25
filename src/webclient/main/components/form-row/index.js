import Component from '../component.js';

/**
 * Implements group of elements such as labels and inputs,
 * that can collect user information.
 */
export default class FormRow extends Component {
  constructor(container, componentConfig) {
    super(container, componentConfig);
    this.root = this.container.lastChild;
  }

  /**
   * Returns form row that contains label, input and in text place(tag '<p>') in html.
   * @return {string} row html code.
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

  get id() {
    return this._id;
  }

  set id(value) {
    this._id = value;
  }

  get labelText() {
    return this._labelText;
  }

  set labelText(value) {
    this._labelText = value;
  }

  get inputType() {
    return this._inputType;
  }

  set inputType(value) {
    this._inputType = value;
  }

  get placeHolder() {
    return this._placeHolder;
  }

  set placeHolder(value) {
    this._placeHolder = value;
  }

  get warning() {
    return this._warning;
  }

  set warning(value) {
    this._warning = value;
  }
}
