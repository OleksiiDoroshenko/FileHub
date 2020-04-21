import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';
import Validator from '../../services/validator';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {
  /**
   * @inheritdoc.
   */
  _markup() {
    return `
            <section class="container base-form login-form" data-test="login-page-rendered">
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

      const validator = new Validator();
      let loginValid = false;
      let passwordValid = false;

      validator.validateLogin(usernameInput.value).then(() => {
        loginValid = true;
      }).catch((message) => {
        usernameInput.showWarning(message);
      });
      validator.validatePassword(passwordInput.value).then(() => {
        passwordValid = true;
      }).catch((message) => {
        passwordInput.showWarning(message);
      });

      if (loginValid && passwordValid) {
        alert('Success');
      }

      event.preventDefault();
      event.stopPropagation();
    });
  }
}
