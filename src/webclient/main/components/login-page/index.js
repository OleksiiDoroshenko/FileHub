import Component from '../component.js';
import FormRow from '../form-row';
import FormActions from '../form-actions';
import Validator from '../../valiator.js';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {
  /**
   * @inheritDoc
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
   * @inheritDoc
   * */
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

      const validator = new Validator();
      const loginValidation = validator.validateLoginRow(usernameRow);
      const passwordValidation = validator.validatePasswordRow(pwdRow);

      if (loginValidation && passwordValidation) {
        alert('Success');
      }

      event.preventDefault();
      event.stopPropagation();
    });
  }
}
