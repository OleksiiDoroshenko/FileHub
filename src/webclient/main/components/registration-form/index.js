import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';
import Validator from '../../services/validator';
import UserData from '../../../models/user-data';


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
      btnType: 'Submit',
    });


    this.actions.addEventListener('click', (event) => {
      const login = this.usernameInput.value;
      const password = this.passwordInput.value;
      const confirmPassword = this.confirmPasswordInput.value;

      if (this._validateForm(login, password, confirmPassword)) {
        this._executeHandlers(new UserData(login, password));
      }
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
   * @return {boolean} returns true if login-form is valid / false if it is not;
   * @private
   */
  _validateForm(login, password, confirmPassword) {
    const validator = new Validator();
    let loginValid = false;
    let passwordValid = false;
    let confirmPasswordValid = false;

    this.usernameInput.hideWarning();
    this.passwordInput.hideWarning();
    this.confirmPasswordInput.hideWarning();

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
   */
  handleError(error) {
    if (error.field === 'password') {
      this.passwordInput.showWarning(error.message);
    } else if (error.field === 'login') {
      this.usernameInput.showWarning(error.message);
    }
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
}
