import FileList from '../file-list/index.js';
import Button from '../button/index.js';
import StateAwareComponent from '../state-aware-component.js';
import GetItemsAction from '../../services/state-manager/actions/get-items/index.js';
import TitleService from '../../services/change-title/index.js';
import GetRootIdAction from '../../services/state-manager/actions/get-root-id/index.js';
import AuthorizationError from '../../../models/errors/authorization-error/index.js';
import UploadFileAction from '../../services/state-manager/actions/upload-file/index.js';
import FileBrowserService from '../../services/file-browser-service/index.js';
import NotFoundError from '../../../models/errors/not-found-error/index.js';
import LogOutAction from '../../services/state-manager/actions/log-out/index.js';
import DeleteItemAction from '../../services/state-manager/actions/delete-item/index.js';
import GetUserAction from '../../services/state-manager/actions/get-user/index.js';
import ClearErrorAction from '../../services/state-manager/actions/clear-error/index.js';
import DownloadFileAction from '../../services/state-manager/actions/download-file/index.js';
import DownloadFileService from '../../services/download-file-service/index.js';

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
    this.stateManager.dispatch(new GetUserAction());
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
                  <li class="username" data-render="username-box" data-toggle="tooltip"
                   data-placement="top" title="Current user">
                      <i class="glyphicon glyphicon-user"></i>
                      <span data-render="username"></span>
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

    const deleteFolderHandler = (model) => {
      this.stateManager.dispatch(new DeleteItemAction(model));
    };
    this.fileList.onDelete(deleteFolderHandler);

    const downloadHandler = (model) => {
      this.stateManager.dispatch(new DownloadFileAction(model, new DownloadFileService()));
    };

    this.fileList.onDownload(downloadHandler);
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
      if (error) {
        this._standardErrorHandler(error);
        this.stateManager.dispatch(new ClearErrorAction('error'));
      }
    });
    this.stateManager.onStateChanged('uploadingItemIds', (state) => {
      this._uploadFileBtn.isLoadingClass = 'file-uploading';
      this._uploadFileBtn.isLoading = state.uploadingItemIds.has(this.id);
      this.fileList.uploadingItems = state.uploadingItemIds;
    });

    this.stateManager.onStateChanged('uploadingError', (state) => {
      const error = state.uploadingError;
      if (error) {
        this._standardErrorHandler(error);
        this.stateManager.dispatch(new ClearErrorAction('uploadingError'));
      }
    });

    this.stateManager.onStateChanged('deletingItemIds', (state) => {
      this.fileList.deletingItems = state.deletingItemIds;
    });

    this.stateManager.onStateChanged('deletingError', (state) => {
      const error = state.deletingError;
      if (error) {
        this._standardErrorHandler(error);
        this.stateManager.dispatch(new ClearErrorAction('deletingError'));
      }
    });

    this.stateManager.onStateChanged('uploadingItemIds', (state) => {
      this._uploadFileBtn.isLoadingClass = 'file-uploading';
      this._uploadFileBtn.isLoading = state.uploadingItemIds.has(this.id);
      this.fileList.uploadingItems = state.uploadingItemIds;
    });
    this.stateManager.onStateChanged('userLoadingError', (state) => {
      const error = state.userLoadingError;
      if (error instanceof AuthorizationError) {
        alert('Your session has expired. Please log in.');
        window.location.hash = '#/login';
      } else {
        alert(`User can not be loaded.\n${error.message}`);
      }
      this.stateManager.dispatch(new ClearErrorAction('userLoadingError'));
    });
    this.stateManager.onStateChanged('user', (state) => {
      this.username = state.user.name;
    });
    this.stateManager.onStateChanged('isUserLoading', (state) => {
      const usernameBox = this.rootElement.querySelector('[data-render="username-box"]');
      usernameBox.classList.toggle('blink', state.isUserLoading);
    });

    this.stateManager.onStateChanged('downloadingItemIds', (state) => {
      this.fileList.downloadingItems = state.downloadingItemIds;
    });
    this.stateManager.onStateChanged('downloadingError', (state) => {
      const error = state.downloadingError;
      if (error) {
        this._standardErrorHandler(error);
        this.stateManager.dispatch(new ClearErrorAction('downloadingError'));
      }
    });
  }

  /**
   * Handles error by common flow.
   * @param {Error} error - error.
   * @private
   */
  _standardErrorHandler(error) {
    if (error instanceof AuthorizationError) {
      alert('Your session has expired. Please log in.');
      window.location.hash = '#/login';
    } else if (error instanceof NotFoundError) {
      let message = `${error.requestedItem} not found.`;
      if (error.message) {
        message = error.message;
      }
      alert(message);
    } else {
      alert('Sorry something went wrong, please try later');
    }
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

  /**
   * Sets user name in the markup.
   * @param {string} name - user name.
   */
  set username(name) {
    const username = this.rootElement.querySelector('[data-render="username"]');
    username.innerText = name;
  }
}
