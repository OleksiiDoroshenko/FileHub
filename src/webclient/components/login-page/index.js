import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';
import {isNotEmpty} from '../../validator.js';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {

  /**
   * @inheritdoc.
   */
  markup() {
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
   */
  initInnerComponents() {
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

      const username = usernameInput.value;
      const password = passwordInput.value;

      const usernameNotEmpty = isNotEmpty(username);
      const passwordNotEmpty = isNotEmpty(password);
      if (usernameNotEmpty && passwordNotEmpty) {
        alert('You have been successfully logged in.');
      } else {
        if (!usernameNotEmpty) {
          username.showWarning('User name should not be empty.');
        }
        if (!passwordNotEmpty) {
          passwordInput.showWarning('Password should not be empty.');
        }
      }
      event.preventDefault();
      event.stopPropagation();
    });

  }
}
