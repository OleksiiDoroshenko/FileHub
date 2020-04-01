import Component from './components/component.js';
import LoginPage from './components/login-page';

/**
 * Implements entry point for rendering every application page.
 */
export default class Application extends Component {

  /**
   * @inheritdoc.
   */
  markup() {
    return `<div class="app"></div>`;
  }

  /**
   * @inheritdoc.
   */
  initInnerComponents() {
    const root = this.container.querySelector('.app');
    new LoginPage(root, {});
  }
}
