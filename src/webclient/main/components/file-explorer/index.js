import FileContainer from '../file-container';
import Button from '../button';
import StateAwareComponent from '../state-aware-component.js';
import GetItemsAction from '../../services/state-manager/actions/get-items';
import DeleteItemAction from '../../services/state-manager/actions/delete-item';
import TitleService from '../../services/change-title';
import FileInputButton from '../file-input';
import UploadFileAction from '../../services/state-manager/actions/upload-file';

/**
 * Renders file explorer page.
 */
export default class FileExplorerPage extends StateAwareComponent {
  /**
   * Returns instance of {@link FileExplorerPage}.
   * @param {HTMLElement} container - element for data rendering.
   * @param {Object} config - initial configuration.
   * @param {StateManager} stateManager - instance of {@link StateManager}.
   */
  constructor(container, config, stateManager) {
    super(container, config, stateManager);
    this.stateManager.dispatch(new GetItemsAction(this.id));
    new TitleService().changeTitle('File Explorer');
  }

  /**
   * @inheritdoc.
   * @private
   */
  _markup() {
    return `
<section class="container base-form">
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
            <div class="btn-menu">
                <input type="file" id="upload-file">
            </div>
        </header>
        <div class="file-container" data-toggle="tooltip" data-placement="top" title="File storage">
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
    const btnMenuRoot = this.container.querySelector('.btn-menu');

    const createDirBtn = new Button(btnMenuRoot, {
      text: '<i class="glyphicon glyphicon-plus"></i>Create directory',
      type: '',
    });
    const uploadFileBtn = new FileInputButton(btnMenuRoot, {
      text: '<i class="glyphicon glyphicon-upload"></i>Upload File',
      type: '',
    });

    uploadFileBtn.uploadFileHandler((file) => {
      this.stateManager.dispatch(new UploadFileAction(this.id, file));
    });

    const fileContainerRoot = this.container.querySelector('.file-container');
    this.fileContainer = new FileContainer(fileContainerRoot, {items: []});

    this.fileContainer.onItemDelete((item) => {
      console.log('delete = ' + item.id);
      this.stateManager.dispatch(new DeleteItemAction(item));
    });
  }

  /**
   * @inheritdoc
   */
  initState() {
    this.stateManager.onStateChanged('items', (state) => {
      this.fileContainer.items = state.items;
    });
  }
}
