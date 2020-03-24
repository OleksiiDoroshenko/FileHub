import Component from "../component.js";
import Button from "../button";

/**
 * Implements form row that contains button and link,
 * that can navigate user to another page.
 */
export default class FormActions extends Component {

    constructor(container, componentConfig) {
        super(container, componentConfig);
    }

    /**
     * Returns form row that contains button and link in html.
     * @returns {string} row html code.
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
     * Renders button into existed form and adds 'onclick' listener to it.
     */
    initInnerComponents() {
        const btnRoot = this.container.querySelector(".col-sm-2");
        new Button(btnRoot, {
            text: this.btnText,
            type: this.btnType
        });
    }

    addEventListener(event, handler) {
        const btn = this.container.querySelector(".btn");
        btn.addEventListener(event, handler);
    }

    set btnType(value) {
        this._btnType = value;
    }

    get btnType() {
        return this._btnType;
    }

    set btnText(value) {
        this._btnText = value;
    }

    get btnText() {
        return this._btnText;
    }

    set linkText(value) {
        this._linkText = value;
    }

    get linkText() {
        return this._linkText;
    }

    set linkHref(value) {
        this._linkHref = value;
    }

    get linkHref() {
        return this._linkHref;
    }
}
