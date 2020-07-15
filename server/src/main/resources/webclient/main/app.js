import Component from './components/component.js';
import Router from './router.js';
import LoginPage from './components/login-page/index.js';
import RegistrationPage from './components/registration-page/index.js';
import ErrorPage from './components/error-page/index.js';
import ApiService from './services/api-service/index.js';
import FileExplorerPage from './components/file-explorer/index.js';
import StateManager from './services/state-manager/index.js';
import MockServer from './services/mock-server/index.js';

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
    if (window.devMode) {
      new MockServer();
    }
    const service = new ApiService();
    const stateManager = new StateManager(
      {
        items: [], uploadingItemIds: new Set(), deletingItemIds: new Set(),
        downloadingItemIds: new Set(), renamingItemIds: new Set(),
      },
      service);
    new Router(root, window, {
      '/login': () => new LoginPage(root, service, {}),
      '/registration': () => new RegistrationPage(root, service, {}),
      '/file-explorer/folder/:id': ({id}) => new FileExplorerPage(root, {id}, stateManager),
      'default': '/login',
      'error': () => new ErrorPage(root, {}),
    });
  }
}
