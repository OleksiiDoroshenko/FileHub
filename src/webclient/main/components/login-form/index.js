import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';
import Validator from '../../services/validator';
import UserData from '../../../models/user-data';

export default class LoginForm extends Component {
  /**
   * @inheritdoc.
   * */
  _markup() {
    return `<form class="form-horizontal"></form>`;
  }

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

    this.actions = new FormActions(formRoot, {
      linkText: 'Don\'t have an account yet?',
      linkHref: '#/registration',
      btnText: 'Log in',
      btnType: 'Submit',
    });

    this.actions.addEventListener('click', (event) => {
      this.usernameInput.hideWarning();
      this.passwordInput.hideWarning();

      const login = this.usernameInput.value;
      const password = this.passwordInput.value;

      if (this._validateForm(login, password)) {
        this._service.login(new UserData(login, password)).then(() => {
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
   * Validates login-form inputs.
   * <p> If some of the inputs are invalid renders warning message with explanations what is not ok.
   * @param {string} login - users username.
   * @param {string} password - users password.
   * @returns {boolean} returns true if login-form is valid / false if it is not;
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
