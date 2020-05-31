import Component from './components/component.js';
import Router from './router.js';
import LoginPage from './components/login-page';
import RegistrationPage from './components/registration-page';
import ErrorPage from './components/error-page';
import ApiService from './services/api-service';
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
    const service = new ApiService(true);
    const stateManager = new StateManager({items: [], uploadingItems: [], deletingItems: []}, service);
    new Router(root, window, {
      '/login': () => new LoginPage(root, service, {}),
      '/registration': () => new RegistrationPage(root, service, {}),
      '/file-explorer/folder/:id': ({id}) => new FileExplorerPage(root, {id}, stateManager),
      'default': '/login',
      'error': () => new ErrorPage(root, {}),
    });
  }
}
