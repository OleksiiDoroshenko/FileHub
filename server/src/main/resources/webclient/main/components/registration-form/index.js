import Component from '../component.js';
import FormInput from '../form-input/index.js';
import FormActions from '../form-actions/index.js';
import Validator from '../../services/validator/index.js';
import UserData from '../../../models/user-data/index.js';


/**
 * Renders fields for user to register.
 */
export default class RegistrationForm extends Component {
  /**
   * Contains all handlers that should be executed when form passes validation.
   * @type {[function]}
   */
  submitHandlers = [];

  /**
   * @inheritdoc.
   * */
  _markup() {
    return `<form class="form-horizontal"></form>`;
  }

  /**
   * @inheritdoc
   * @private
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
    });


    this.actions.addEventListener('click', (event) => {
      const login = this.usernameInput.value;
      const password = this.passwordInput.value;
      const confirmPassword = this.confirmPasswordInput.value;

      this._validateForm(login, password, confirmPassword).then(() => {
        this._executeHandlers(new UserData(login, password));
      }).catch((error) => {
      });
      event.preventDefault();
      event.stopPropagation();
    });
  }

  /**
   * Validates login-form inputs.
   * <p> If some of the inputs are invalid renders warning message with explanations what is not ok.
   * @param {string} login - users username.
   * @param {string} password - users password.
   * @param {string} confirmPassword - users repeated password.
   * @return {Promise<boolean>} returns true if login-form is valid / false if it is not;
   * @private
   */
  _validateForm(login, password, confirmPassword) {
    return new Promise((resolve, reject) => {
      const validator = new Validator();
      const loginValid = validator.validateLogin(login);
      const passwordValid = validator.validatePassword(password);
      const confirmPasswordValid = validator.comparePasswords(confirmPassword, password);

      this.usernameInput.hideWarning();
      this.passwordInput.hideWarning();
      this.confirmPasswordInput.hideWarning();

      Promise.all([loginValid.catch((error) => {
        this._errorHandler(error);
        reject('login');
      }),
      passwordValid.catch((error) => {
        this._errorHandler(error);
        reject('password');
      }),
      confirmPasswordValid.catch((error) => {
        this._errorHandler(error);
        reject('confirm');
      })])
          .then(() => {
            resolve('general');
          }).catch((error) => {
            this._errorHandler(error);
            reject();
          });
    });
  }

  /**
   * Handles api-service errors.
   * <p> renders warning messages below invalid inputs.
   * @param {[VerificationError]} errors - instance of {@link VerificationError} .
   */
  handleError(errors) {
    errors.forEach((error) => {
      if (error.field === 'password') {
        this.passwordInput.showWarning(error.message);
      } else if (error.field === 'login') {
        this.usernameInput.showWarning(error.message);
      } else if (error.field === 'confirmPassword') {
        this.confirmPasswordInput.showWarning(error.message);
      }
    });
  }

  /**
   * Adds handler to the handler array.
   * @param {function} handler - function that should be executed when form passes validation.
   */
  onSubmit(handler) {
    this.submitHandlers.push(handler);
  }

  /**
   * Execute every handler.
   * @param {UserData} userData - instance of {@link UserData}.
   * @private
   */
  _executeHandlers(userData) {
    this.submitHandlers.forEach((handler) => {
      handler(userData);
    });
  }

  /**
   * Shows error message below input with invalid date.
   * @param {VerificationError} error - instance of {@link VerificationError}.
   * @private
   */
  _errorHandler(error) {
    switch (error.field) {
      case 'login': {
        this.usernameInput.showWarning(error.message);
        break;
      }
      case 'password': {
        this.passwordInput.showWarning(error.message);
        break;
      }
      case 'confirmPassword': {
        this.confirmPasswordInput.showWarning(error.message);
        break;
      }
    }
  }
}
