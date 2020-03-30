import Button from '../main/components/button';
import FormRow from '../main/components/form-row';
import FormActions from '../main/components/form-actions';
import LoginPage from '../main/components/login-page';
import RegistrationPage from '../main/components/registration-page';

const {module, test} = QUnit;

export default module('Components rendering test', function(hook) {
  let fixture;

  hook.beforeEach(() => {
    fixture = document.getElementById('qunit-fixture');
  });

  test('Button should be rendering correctly', (assert) => {
    new Button(fixture, {
      type: 'submit',
      text: 'Test',
    });
    const currentState = fixture.querySelector('.btn');
    assert.strictEqual(currentState.innerText, 'Test', 'Button should be rendered.');
  });

  test('FormRow should be rendering correctly', (assert) => {
    new FormRow(fixture, {
      id: 'Test',
      labelText: 'Test',
      inputType: 'text',
      placeHolder: 'Test',
      warning: '',
    });
    const currentState = fixture.querySelector('.control-label');
    assert.strictEqual(currentState.innerText, 'Test', 'FormRow should be rendered.');
  });

  test('FormAction should be rendering correctly', (assert) => {
    new FormActions(fixture, {
      btnText: '',
      btnType: '',
      linkText: 'Test',
      linkHref: '',
    });
    const currentState = fixture.querySelector('a');
    assert.strictEqual(currentState.innerText, 'Test', 'FormAction should be rendered.');
  });

  test('LoginPage should be rendering correctly', (assert) => {
    new LoginPage(fixture, {});
    const currentState = fixture.querySelector('h1');
    assert.strictEqual(currentState.innerText, 'Login', 'LoginPage should be rendered.');
  });

  test('RegistrationPage should be rendering correctly', (assert) => {
    new RegistrationPage(fixture, {});
    const currentState = fixture.querySelector('h1');
    assert.strictEqual(currentState.innerText, 'Registration', 'RegistrationPage should be rendered.');
  });
});
