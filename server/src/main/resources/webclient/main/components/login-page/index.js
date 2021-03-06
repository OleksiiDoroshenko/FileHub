import Component from '../component.js';
import LoginForm from '../login-form/index.js';
import TitleService from '../../services/change-title/index.js';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {
  /**
   * Class constructor.
   * @param {HTMLElement} container - root container for element rendering.
   * @param {ApiService} service - instance of {@link ApiService}.
   * @param {Object} componentConfig - empty object.
   */
  constructor(container, service, componentConfig) {
    super(container, componentConfig);
    this._service = service;
    new TitleService().changeTitle('Login');
  }

  /**
   * @inheritdoc.
   */
  _markup() {
    return `
            <section class="container base-form login-form" data-render="login-page">
                 <header class="header">
                    <img class="logo" alt="logo" src="./static/images/teamdev.png" width="150">
                    <i class="glyphicon glyphicon-user user-icon"></i>
                    <h1>Login</h1>
                </header>
            </section>
        `;
  }

  /**
   * @inheritdoc.
   * */
  _initInnerComponents() {
    const formRoot = this.container.querySelector('[data-render="login-page"]');
    const form = new LoginForm(formRoot, {});
    form.onSubmit((userData) => {
      this._service.logIn(userData)
        .then(() => {
          window.location.hash = `#/file-explorer/folder/root`;
        }).catch((error) => {
        alert(error.message);
      });
    });
  }
}
