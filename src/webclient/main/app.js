import Component from './components/component.js';
import Router from './router.js';
import LoginPage from './components/login-page';
import RegistrationPage from './components/registration-page';
import ErrorPage from './components/error-page';

/**
 * Implements entry point for rendering every application page.
 */
export default class Application extends Component {
  /**
   * @inheritDoc
   */
  markup() {
    return `<div class="app"></div>`;
  }

  /**
   * @inheritDoc
   */
  initInnerComponents() {
    const root = this.container.querySelector('.app');
    new Router(root, window, {
      '/login': () => new LoginPage(root, {}),
      '/registration': () => new RegistrationPage(root, {}),
      'default': '/login',
      'error': () => new ErrorPage(root, {}),
    });
  }
}
