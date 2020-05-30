import FileList from '../file-list';
import Button from '../button';
import StateAwareComponent from '../state-aware-component.js';
import GetItemsAction from '../../services/state-manager/actions/get-items';
import TitleService from '../../services/change-title';
import DeleteItemAction from '../../services/state-manager/actions/delete-item';
import GetRootIdAction from '../../services/state-manager/actions/get-root-id';
import AuthorizationError from '../../../models/errors/authorization-error';
import UploadFileAction from '../../services/state-manager/actions/upload-file';
import FileBrowserService from '../../services/file-browser-service';
import NotFoundError from '../../../models/errors/not-found-error';
import LogOutAction from '../../services/state-manager/actions/log-out';

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
                      <a href="#/login" data-render="log-out">Log out <i class="glyphicon glyphicon-log-out"></i></a></li>
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
      iconClass: 'glyphicon-plus',
      dataParam: 'create-dir-btn',
    });
    this._uploadFileBtn = new Button(btnMenuRoot, {
      text: 'Upload File',
      iconClass: 'glyphicon-upload',
      dataParam: 'upload-file-btn',
    });

    const logOut = this.rootElement.querySelector('[data-render="log-out"]');
    logOut.addEventListener('click', () => {
      this.stateManager.dispatch(new LogOutAction());
    });
    const fileContainerRoot = this.container.querySelector('[data-render="file-list"]');
    this.fileList = new FileList(fileContainerRoot, {items: []});

    const uploadHandler = (id, file) => {
      this.stateManager.dispatch(new UploadFileAction(id, file));
    };

    this.fileList.onUploadClick(uploadHandler);

    this._uploadFileBtn.addEventListener('click', () => {
      new FileBrowserService().selectFile().then(file => {
        uploadHandler(this.id, file);
      });
    });
  }

  /**
   * @inheritdoc
   */
  initState() {
    this.stateManager.onStateChanged('items', (state) => {
      this.fileList.items = state.items;
    });
    this.stateManager.onStateChanged('isLoading', (state) => {
      if (state.isLoading) {
        this.fileList.showLoadingMessage();
      }
    });
    this.stateManager.onStateChanged('error', (state) => {
      const error = state.error;
      if (error instanceof AuthorizationError) {
        window.location.hash = '#/login';
      }
      if (error instanceof NotFoundError) {
        let message = 'Folder not found.';
        if (error.message) {
          message = error.message;
        }
        alert(message);
      } else {
        alert('Sorry something went wrong, please try later');
      }
    });
    this.stateManager.onStateChanged('uploadingItems', (state) => {
      this._uploadFileBtn.isLoadingClass = 'file-uploading';
      this._uploadFileBtn.isLoading = state.uploadingItems.includes(this.id);
      this.fileList.uploadingItems = state.uploadingItems;
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
