import Component from '../component.js';
import RegistrationForm from '../registration-form';
import AuthorizationError from '../../../models/errors/authorization-error';

/**
 * Implements html page that allows user to register.
 */
export default class RegistrationPage extends Component {
  /**
   * Class constructor.
   * @param {HTMLElement} container - root container for element rendering.
   * @param {AuthenticationService} service - instance of {@link AuthenticationService}.
   * @param {Object} componentConfig - empty object.
   */
  constructor(container, service, componentConfig) {
    super(container, componentConfig);
    this._service = service;
  }

  /**
   * @inheritdoc.
   */
  _markup() {
    return `
            <section class="container base-form login-form">
                 <header class="header">
                    <img class="logo" alt="logo" src="./static/images/teamdev.png" width="150">
                    <i class="glyphicon glyphicon-user user-icon"></i>
                    <h1>Registration</h1>
                </header>
            </section>
        `;
  }

  /**
   @inheritdoc.
   */
  _initInnerComponents() {
    const formRoot = this.container.querySelector('.login-form');
    const form = new RegistrationForm(formRoot, {});
    form.onSubmit((userData) => {
      this._service.register(userData).then(() => {
        window.location.hash = '#/login';
      }).catch((error) => {
        if (!Array.isArray(error)) {
          alert(error.message);
        } else {
          form.handleError(error);
        }
      });
    });
  }
}
