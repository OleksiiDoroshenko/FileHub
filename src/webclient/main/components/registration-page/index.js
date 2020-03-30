import Component from '../component.js';
import FormRow from '../form-row';
import FormActions from '../form-actions';
import Validator from '../../valiator.js';

/**
 * Implements html page that allows user to register.
 */
export default class RegistrationPage extends Component {
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
                    <h1>Registration</h1>
                </header>
                <form class="form-horizontal">
                </form>
            </section>
        `;
  }

  /**
   @inheritdoc
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
        const response = this.service.register(usernameRow.value, pwdRow.value);
        response.then((callback) => {
          callback();
        }).catch((error) => {
          if (error.code === 401) {
            this.handleVerificationError(error, usernameRow, pwdRow);
          } else {
            alert(error.message);
          }
        });
      }

      event.preventDefault();
      event.stopPropagation();
    });
  }

  /**
   * Handles verification errors with code '401' from {@link AuthenticationService}
   * @param {VerificationError} error - error that throws by server when login or password are invalid.
   * @param {FormRow} usernameRow - registration form row that contains data about users login.
   * @param {FormRow} pwdRow -registration form row that contains data about users password.
   */
  handleVerificationError(error, usernameRow, pwdRow) {
    if (error.field === 'pwd') {
      pwdRow.showWarning(error.message);
    } else if (error.field === 'login') {
      usernameRow.showWarning(error.message);
    } else {
      alert('Unknown validation error.');
    }
  }
}
