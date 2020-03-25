import Component from './components/component.js';
import Router from './roter.js';
import LoginPage from './components/login-page';
import RegistrationPage from './components/registration-page';
import ErrorPage from './components/error-page';

/**
 * Implements entry point for rendering every application page.
 */
export default class Application extends Component {
  /**
   * Returns html application frame.
   * @return {string}
   */
  markup() {
    return `<div class="app"></div>`;
  }

  /**
   * Renders necessary page;
   */
  initInnerComponents() {
    const root = this.container.querySelector('.app');
    new Router(root, {
      '/login': LoginPage,
      '/registration': RegistrationPage,
      'default': '/login',
      'error': ErrorPage,
    });
  }
}
