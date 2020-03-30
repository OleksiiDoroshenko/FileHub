import Component from '../component.js';
import FormRow from '../form-row';
import FormActions from '../form-actions';
import Validator from '../../valiator.js';

/**
 * Implements html page that allows user to log in.
 */
export default class LoginPage extends Component {
  /**
   * Class constructor.
   * @param {HTMLElement} container - root container for element rendering.
   * @param {AuthenticationService} service - handles login and registration operations logic.
   * @param {Object} componentConfig - empty object.
   */
  constructor(container, service, componentConfig) {
    super(container, componentConfig);
    this.service = service;
  }


  /**
   * @inheritdoc
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
   * @inheritdoc
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
        const response = this.service.login(usernameRow.value, pwdRow.value);
        response.then((callback) => {
          callback();
        }).catch((error) => {
          alert(error.message);
        });
      }

      event.preventDefault();
      event.stopPropagation();
    });
  }
}
