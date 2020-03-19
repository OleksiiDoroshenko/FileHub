import Component from './component/index.js'

export default class NavPanel extends Component {
    constructor(container) {
        super(container);
        this.rootelement.classList.add("form-group");
        this.rootelement.classList.add("nav-panel");
    }

    markup() {
        return `
        <div class="col-sm-offset-4 col-sm-2">
                <button type="submit" class="btn btn-primary">${this.btnText}</button>
            </div>
            <div class="col-sm-offset-1 col-sm-5 nav-link">
                <a href=${this.linkHref}>${this.linkText}</a>
            </div>
        `;
    }


    set btnText(text) {
        this._btnText = text;
        this.render();
    }

    get btnText() {
        return this._btnText;
    }

    set linkText(text) {
        this._linkText = text;
        this.render();
    }

    get linkText() {
        return this._linkText;
    }

    set linkHref(href) {
        this._linkHref = href;
        this.render();
    }

    get linkHref() {
        return this._linkHref;
    }


}