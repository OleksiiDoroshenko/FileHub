import Component from '../component.js';
import FormRow from '../form-row';
import FormActions from '../form-actions';
import Validator from '../../valiator.js';

/**
 * Implements html page that allows user to register.
 */
export default class RegistrationPage extends Component {
  /**
   * @inheritDoc
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
   @inheritDoc
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

    const cnfPwdRow = new FormRow(formRoot, {
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
      usernameRow.hideWarning();
      pwdRow.hideWarning();
      cnfPwdRow.hideWarning();

      const validator = new Validator();
      const loginValidation = validator.validateLoginRow(usernameRow);
      const pwdValidation = validator.validatePasswordRow(pwdRow);
      const cnfPwdValidation = validator.validateCnfPasswordRow(cnfPwdRow, pwdRow);

      if (loginValidation && pwdValidation && cnfPwdValidation) {
        alert('Success');
      }

      event.preventDefault();
      event.stopPropagation();
    });
  }
}
