import FileList from '../file-list';
import Button from '../button';
import StateAwareComponent from '../state-aware-component.js';
import GetItemsAction from '../../services/state-manager/actions/get-items';
import TitleService from '../../services/change-title';
import GetRootIdAction from '../../services/state-manager/actions/get-root-id';
import AuthorizationError from '../../../models/errors/authorization-error';
import UploadFileAction from '../../services/state-manager/actions/upload-file';
import FileInputButton from '../file-input';

/**
 * Renders file explorer page.
 */
export default class FileExplorerPage extends StateAwareComponent {
  /**
   * Returns instance of {@link FileExplorerPage}.
   * @param {HTMLElement} container - container for rendering.
   * @param {Object} config - configuration object.
   * @param {StateManager}stateManager
   */
  constructor(container, config, stateManager) {
    super(container, config, stateManager);
    if (this.id === 'root') {
      this._getRootFolder();
    } else {
      this.stateManager.dispatch(new GetItemsAction(this.id));
    }
    new TitleService().changeTitle('File Explorer');
  }

  /**
   * @inheritdoc.
   * @private
   */
  _markup() {
    return `
      <section class="container base-form" data-render="file-explorer">
             <header class="header">
              <img class="logo" alt="logo" src="./static/images/teamdev.png" width="150">
              <ul class="list-inline logout-menu">
                  <li class="username" data-toggle="tooltip" data-placement="top" title="Current user">
                      <i class="glyphicon glyphicon-user"></i> ${this.username}
                  </li>
                  <li class="logout" data-toggle="tooltip" data-placement="top" title="Log out">
                      <a href="#">Log out <i class="glyphicon glyphicon-log-out"></i></a></li>
              </ul>
              <a href="file-explorer-index.html" data-toggle="tooltip" data-placement="top" title="Root page">
                  <h1 class="file-explorer">File Explorer</h1></a>
          </header>
          <div class="content">
              <header class="path-manager">
                  <div class="form-group">
                      <ul class="list-inline col-sm-4 current-position-menu">
                          <li><a href="#" data-toggle="tooltip" data-placement="top" title="Root page">
                              <i class="glyphicon glyphicon-folder-open"></i></a></li>
                          <li>/</li>
                          <li data-toggle="tooltip" data-placement="top" title="Current path">Root</li>
                      </ul>
                  </div>
                  <div class="btn-menu" data-render="btn-menu">
                      <input type="file" id="upload-file">
                  </div>
              </header>
              <div class="file-container" data-toggle="tooltip" data-render="file-list"
               data-placement="top" title="File storage" data-render="file-list">
              </div>
          </div>
          <footer class="footer">
              Copyright © 2020
              <a class="copyright-link" target="_blank" href="https://www.teamdev.com/" data-toggle="tooltip"
                 data-placement="top" title="TeamDev site">TeamDev.</a>
              All rights reserved.
          </footer>
       </section>
    `;
  }

  /**
   * @inheritdoc
   * @private
   */
  _initInnerComponents() {
    const btnMenuRoot = this.container.querySelector('[data-render="btn-menu"]');
    const createDirBtn = new Button(btnMenuRoot, {
      text: 'Create directory',
      icon: 'glyphicon-plus',
    });
    const uploadFileBtn = new FileInputButton(btnMenuRoot, {
      text: 'Upload File',
      icon: 'glyphicon-upload',
    });
    const fileContainerRoot = this.container.querySelector('[data-render="file-list"]');
    this.fileContainer = new FileList(fileContainerRoot, {items: []});

    uploadFileBtn.uploadFileHandler = (file) => {
      this.stateManager.dispatch(new UploadFileAction(this.id, file));
    };
  }

  /**
   * @inheritdoc
   */
  initState() {
    this.stateManager.onStateChanged('items', (state) => {
      this.fileContainer.items = state.items;
    });
    this.stateManager.onStateChanged('isLoading', (state) => {
      if (state.isLoading) {
        this.fileContainer.showLoadingMessage();
      }
    });
    this.stateManager.onStateChanged('error', (state) => {
      const error = state.error;
      if (error instanceof AuthorizationError) {
        window.location.hash = '#/login';
      } else {
        this.fileContainer.showError(state.error);
      }
    });
  }

  /**
   * Gets users folder id and calls method to update hash.
   * @returns {Promise<void>}
   * @private
   */
  async _getRootFolder() {
    const rootId = await this.stateManager.dispatch(new GetRootIdAction());
    this._changeHashId(rootId);
  }

  /**
   * Changes id in current url hash according to parameters id.
   * @param {string} newId - new id to change.
   * @private
   */
  _changeHashId(newId) {
    const hash = window.location.hash.slice(1);
    const newHash = hash.split('/').reduce((acc, item) => {
      if (item === this.id) {
        acc += `/${newId}`;
      } else {
        acc += `/${item}`;
      }
      return acc;
    }, '');
    window.location.hash = newHash.slice(1);
  }
}
