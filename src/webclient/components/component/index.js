export default class Component {
    constructor(container) {
        this.rootelement = document.createElement("div");
        container.appendChild(this.rootelement);
        this.render();
    }

    render() {
        this.rootelement.innerHTML = this.markup();
        return this.rootelement;
    }

    markup() {
    }

    addEventListener() {
    }

    get rootElement() {
        return this.rootelement;
    }
}