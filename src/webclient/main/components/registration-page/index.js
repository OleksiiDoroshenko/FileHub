import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';
import Validator from '../../services/validator';

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
                <form class="form-horizontal">
                </form>
            </section>
        `;
  }

  /**
   @inheritdoc.
   */
  _initInnerComponents() {
    const formRoot = this.container.querySelector('.form-horizontal');

    this.usernameInput = new FormInput(formRoot, {
      id: 'email',
      labelText: 'Username',
      inputType: 'text',
      placeHolder: 'Email',
      warning: '',
    });

    this.passwordInput = new FormInput(formRoot, {
      id: 'pwd',
      labelText: 'Password',
      inputType: 'password',
      placeHolder: 'Password',
      warning: '',
    });

    this.confirmPasswordInput = new FormInput(formRoot, {
      id: 'cnfPwd',
      labelText: 'Confirm Password',
      inputType: 'password',
      placeHolder: 'Confirm Password',
      warning: '',
    });

    this.actions = new FormActions(formRoot, {
      linkText: 'Already have an account?',
      linkHref: '#/login',
      btnText: 'Register',
      btnType: 'Submit',
    });


    this.actions.addEventListener('click', (event) => {
      this.usernameInput.hideWarning();
      this.passwordInput.hideWarning();
      this.confirmPasswordInput.hideWarning();

      const login = this.usernameInput.value;
      const password = this.passwordInput.value;
      const confirmPassword = this.confirmPasswordInput.value;

      if (this._validateForm(login, password, confirmPassword)) {
        this._service.register(login, password).then(() => {
          window.location.hash = '#/login';
        }).catch((error) => {
          if (error.code === 422) {
            alert(error.message);
          } else if (error.code === 401) {
            this._verificationErrorHandler(error);
          }
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
   * @param {string} confirmPassword - users repeated password.
   * @returns {boolean} returns true if form is valid / false if it is not;
   * @private
   */
  _validateForm(login, password, confirmPassword) {
    const validator = new Validator();
    let loginValid = false;
    let passwordValid = false;
    let confirmPasswordValid = false;

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
    validator.comparePasswords(confirmPassword, password).then(() => {
      confirmPasswordValid = true;
    }).catch((error) => {
      this.confirmPasswordInput.showWarning(error.message);
    });

    return loginValid && passwordValid && confirmPasswordValid;
  }

  /**
   * Handles authentication errors.
   * <p> renders warning messages below invalid inputs.
   * @param {VerificationError} error - instance of {@link VerificationError} .
   * @private
   */
  _verificationErrorHandler(error) {
    if (error.field === 'password') {
      this.passwordInput.showWarning(error.message);
    } else if (error.field === 'login') {
      this.usernameInput.showWarning(error.message);
    }
  }
}
