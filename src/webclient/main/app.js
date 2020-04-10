import Component from './components/component.js';
import Router from './router.js';
import LoginPage from './components/login-page';
import RegistrationPage from './components/registration-page';
import ErrorPage from './components/error-page';
import AppService from './services/app-secrvice';
import FileExplorerPage from './components/file-explorer';
import StateManager from './services/state-manager';

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
    const service = new AppService(true);
    const stateManager = new StateManager({items: []}, service);
    new Router(root, window, {
      '/login': () => new LoginPage(root, service, {}),
      '/registration': () => new RegistrationPage(root, service, {}),
      '/file-explorer/:username/folder/:id': ({username, id}) => new FileExplorerPage(root, {username,id}, stateManager),
      'default': '/login',
      'error': () => new ErrorPage(root, {}),
    });
  }
}
