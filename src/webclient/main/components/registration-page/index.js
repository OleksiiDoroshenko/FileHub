import Component from '../component.js';
import FormRow from '../form-row';
import FormActions from '../form-actions';

/**
 * Implements html page that allows user to register.
 */
export default class RegistrationPage extends Component {
  constructor(container, componentConfig) {
    super(container, componentConfig);
  }

  /**
   * Returns html frame for registration page.
   * @return {string} html code.
   */
  markup() {
    return `
            <section class="container base-form login-form">
                 <header class="header">
<!--                    <img class="logo" alt="logo" src="../../images/teamdev.png" width="150">-->
                    <i class="glyphicon glyphicon-user user-icon"></i>
                    <h1>Registration</h1>
                </header>
                <form class="form-horizontal">
                </form>
            </section>
        `;
  }

  /**
   * Fills registration html frame with :
   * 1) row for collecting username
   * 2) row for collecting password
   * 3) row for collecting repeated password
   * 4) row with button for register and link for navigation to log in page.
   */
  initInnerComponents() {
    const formRoot = this.container.querySelector('.form-horizontal');
    new FormRow(formRoot, {
      id: 'email',
      labelText: 'Username',
      inputType: 'text',
      placeHolder: 'Email',
      warning: '',
    });

    new FormRow(formRoot, {
      id: 'pwd',
      labelText: 'Password',
      inputType: 'password',
      placeHolder: 'Password',
      warning: '',
    });

    new FormRow(formRoot, {
      id: 'cnfPwd',
      labelText: 'Confirm Password',
      inputType: 'password',
      placeHolder: 'Confirm Password',
      warning: '',
    });

    const actions = new FormActions(formRoot, {
      linkText: 'Already have an account?',
      linkHref: '#/login',
      btnText: 'Register',
      btnType: 'Submit',
    });

    actions.addEventListener('click', (event) => {
      event.preventDefault();
      event.stopPropagation();
    });
  }
}
