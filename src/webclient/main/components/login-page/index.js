import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';
import Validator from '../../services/validator';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {
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
                    <h1>Login</h1>
                </header>
                <form class="form-horizontal">
                </form>
            </section>
        `;
  }

  /**
   * @inheritdoc.
   * */
  _initInnerComponents() {
    const formRoot = this.container.querySelector('.form-horizontal');
    const usernameInput = new FormInput(formRoot, {
      id: 'email',
      labelText: 'Username',
      inputType: 'text',
      placeHolder: 'Email',
      warning: '',
    });

    const passwordInput = new FormInput(formRoot, {
      id: 'pwd',
      labelText: 'Password',
      inputType: 'password',
      placeHolder: 'Password',
      warning: '',
    });

    const actions = new FormActions(formRoot, {
      linkText: 'Don\'t have an account yet?',
      linkHref: '#/registration',
      btnText: 'Log in',
      btnType: 'Submit',
    });

    actions.addEventListener('click', (event) => {
      usernameInput.hideWarning();
      passwordInput.hideWarning();

      const login = usernameInput.value;
      const password = passwordInput.value;

      if (this._validateForm(login, password)) {
        this._service.login(login, password).then(() => {
          window.location.hash = '/#fileHub';
        }).catch((error) => {
          alert(error.message);
        });
      }
      event.preventDefault();
      event.stopPropagation();
    });
  }

  /**
   * Validates form inputs.
   * <p> If some of the inputs are invalid renders warning message with explanations what is not ok.
   * @param {string} login - users username.
   * @param {string} password - users password.
   * @returns {boolean} returns true if form is valid / false if it is not;
   * @private
   */
  _validateForm(login, password) {
    const validator = new Validator();
    let loginValid = false;
    let passwordValid = false;

    validator.validateLogin(login).then(() => {
      loginValid = true;
    }).catch((error) => {
      this.usernameInput.showWarning(error.message);
    });
    validator.validatePassword(password).then(() => {
      passwordValid = true;
    }).catch((error) => {
      this.passwordInput.showWarning(error.message);
    });

    return loginValid && passwordValid;
  }
}
