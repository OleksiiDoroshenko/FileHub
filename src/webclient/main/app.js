import Component from './components/component.js';
import Router from './router.js';
import LoginPage from './components/login-page';
import RegistrationPage from './components/registration-page';
import ErrorPage from './components/error-page';
import AuthenticationService from './services/authentication';

/**
 * Implements entry point for rendering every application page.
 */
export default class Application extends Component {
  /**
   * @inheritdoc
   */
  _markup() {
    return `<div class="app"></div>`;
  }

  /**
   * @inheritdoc
   */
  _initInnerComponents() {
    const root = this.container.querySelector('.app');
    const service = new AuthenticationService();
    new Router(root, window, {
      '/login': () => new LoginPage(root, service, {}),
      '/registration': () => new RegistrationPage(root, service, {}),
      'default': '/login',
      'error': () => new ErrorPage(root, {}),
    });
  }
}
