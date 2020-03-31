import Component from '../component.js';
import FormInput from '../form-input';
import FormActions from '../form-actions';

/**
 * Implements html page that allows user to register.
 */
export default class RegistrationPage extends Component {

  /**
   * @inheritdoc.
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
   * @inheritdoc.
   */
  initInnerComponents() {
    const formRoot = this.container.querySelector('.form-horizontal');
    new FormInput(formRoot, {
      id: 'email',
      labelText: 'Username',
      inputType: 'text',
      placeHolder: 'Email',
      warning: '',
    });

    new FormInput(formRoot, {
      id: 'pwd',
      labelText: 'Password',
      inputType: 'password',
      placeHolder: 'Password',
      warning: '',
    });

    new FormInput(formRoot, {
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
