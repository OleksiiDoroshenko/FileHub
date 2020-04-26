import Component from '../component.js';
import FileContainer from '../file-container';
import Button from '../button';

/**
 * Renders file explorer page.
 */
export default class FileExplorer extends Component {
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
                <i class="glyphicon glyphicon-user"></i> Username
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
        <div class="file-container" data-render="file-container" data-toggle="tooltip" 
        data-placement="top" title="File storage">
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
      icon: 'create',
    });
    const uploadFileBtn = new Button(btnMenuRoot, {
      text: 'Upload File',
      icon: 'upload',
    });

    const fileContainerRoot = this.container.querySelector('[data-render="file-container"]');
    new FileContainer(fileContainerRoot, {});
  }
}
