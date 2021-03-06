import Component from '../component.js';
import RegistrationForm from '../registration-form/index.js';
import TitleService from '../../services/change-title/index.js';
import ServerValidationErrors from '../../../models/errors/server-validation-errors/index.js';

/**
 * Implements html page that allows user to register.
 */
export default class RegistrationPage extends Component {
  /**
   * Class constructor.
   * @param {HTMLElement} container - root container for element rendering.
   * @param {AppService} service - instance of {@link AppService}.
   * @param {Object} componentConfig - empty object.
   */
  constructor(container, service, componentConfig) {
    super(container, componentConfig);
    this._service = service;
    new TitleService().changeTitle('Registration');
  }

  /**
   * @inheritdoc.
   */
  _markup() {
    return `
            <section class="container base-form login-form" data-render="registration-page">
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
    const formRoot = this.container.querySelector('[data-render="registration-page"]');
    const form = new RegistrationForm(formRoot, {});

    form.onSubmit((userData) => {
      this._service.register(userData)
        .then((response) => {
          window.location.hash = '#/login';
        }).catch((error) => {
        if (error instanceof ServerValidationErrors) {
          form.handleError(error);
        } else {
          alert(error.message);
        }
      });
    });
  }
}
