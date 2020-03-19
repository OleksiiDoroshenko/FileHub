import Component from './component/index.js'

export default class FormRow extends Component {
    constructor(container) {
        super(container);
        this.rootelement.classList.add("row");
    }

    markup() {
        return `
        <label class="control-label col-sm-4" for =${this.inputId}>${this.labelText}</label>
            <div class="col-sm-8 input-filed">
                <input class="form-control" id=${this.inputId} type=${this.inputType}
                 placeholder=${this.inputPlaceHolder} name="${this.inputId}">
                <p class="text-danger">${this.warningText}</p>
            </div>
        `;
    }

    set labelText(text) {
        this._labelText = text;
        this.render();
    }

    get labelText() {
        return this._labelText;
    }

    set warningText(text) {
        this._warningText = text;
        this.render();
    }

    get warningText() {
        return this._warningText;
    }

    inputAttributes(inputId, inputType, inputPlaceHolder) {
        this.inputId = inputId;
        this.inputType = inputType;
        this.inputPlaceHolder = inputPlaceHolder;
        this.render();
    }

}