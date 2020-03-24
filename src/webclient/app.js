import Component from "./components/component.js";
import LoginPage from "./components/login-page";

/**
 * Implements entry point for rendering every application page.
 */
export default class Application extends Component {

    /**
     * Returns html application frame.
     * @returns {string}
     */
    markup() {
        return `<div class="app"></div>`;
    }

    /**
     * Renders necessary page;
     */
    initInnerComponents() {
        const root = this.container.querySelector(".app");
        new LoginPage(root, {})
    }
}
