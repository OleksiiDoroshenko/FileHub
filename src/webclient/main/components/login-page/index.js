import Component from '../component.js';
import FormRow from '../form-row';
import FormActions from '../form-actions';
import {isNotEmpty} from '../../valiator.js';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {
  constructor(container, componentConfig) {
    super(container, componentConfig);
  }

  /**
   * Returns html frame for login page.
   * @return {string} html code.
   */
  markup() {
    return `
            <section class="container base-form login-form">
                 <header class="header">
<!--                    <img class="logo" alt="logo" src="../../images/teamdev.png" width="150">-->
                    <i class="glyphicon glyphicon-user user-icon"></i>
                    <h1>Login</h1>
                </header>
                <form class="form-horizontal">
                </form>
            </section>
        `;
  }

  /**
   * Fills login html frame with :
   * 1) row for collecting username
   * 2) row for collecting password
   * 3) row with button for log in and link for navigation to registration page.
   */
  initInnerComponents() {
    const formRoot = this.container.querySelector('.form-horizontal');
    const usernameRow = new FormRow(formRoot, {
      id: 'email',
      labelText: 'Username',
      inputType: 'text',
      placeHolder: 'Email',
      warning: '',
    });

    const pwdRow = new FormRow(formRoot, {
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
      usernameRow.hideWarning();
      pwdRow.hideWarning();

      const username = usernameRow.value;
      const pwd = pwdRow.value;

      if (isNotEmpty(username) && isNotEmpty(pwd)) {
        alert('You have been successfully logged in.');
      } else {
        if (!isNotEmpty(usernameRow.value)) {
          usernameRow.showWarning('User name should not be empty.');
        }
        if (!isNotEmpty(pwdRow.value)) {
          pwdRow.showWarning('Password should not be empty.');
        }
      }
      event.preventDefault();
      event.stopPropagation();
    });
  }
}
