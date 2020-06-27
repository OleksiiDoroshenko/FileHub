import Button from '../main/components/button';
import FormInput from '../main/components/form-input';
import FormActions from '../main/components/form-actions';
import LoginPage from '../main/components/login-page';
import RegistrationPage from '../main/components/registration-page';
import ErrorPage from '../main/components/error-page';
import FileList from '../main/components/file-list';

const {module, test} = QUnit;

export default module('Components rendering test: ', function(hook) {
  let fixture;

  hook.beforeEach(() => {
    fixture = document.getElementById('qunit-fixture');
  });

  module('Button rendering module:', function(hook) {
    test('Button should be rendered correctly', (assert) => {
      new Button(fixture, {
        type: 'submit',
        text: 'Test',
      });
      const currentState = fixture.querySelector('.btn');
      assert.strictEqual(currentState.innerText, 'Test', 'Button should be rendered.');
    });
  });

  module('Form fields rendering module:', function(hook) {
    test('FormInput should be rendered correctly', (assert) => {
      new FormInput(fixture, {
        id: 'Test',
        labelText: 'Test',
        inputType: 'text',
        placeHolder: 'Test',
        warning: '',
      });
      const currentState = fixture.querySelector('.control-label');
      assert.strictEqual(currentState.innerText, 'Test', 'FormRow should be rendered.');
    });

    test('FormAction should be rendered correctly', (assert) => {
      new FormActions(fixture, {
        btnText: '',
        btnType: '',
        linkText: 'Test',
        linkHref: '',
      });
      const currentState = fixture.querySelector('a');
      assert.strictEqual(currentState.innerText, 'Test', 'FormAction should be rendered.');
    });
  });

  module('Page rendering module:', function(hook) {
    test('Login page should be rendered correctly.', (assert) => {
      new LoginPage(fixture, {});
      const page = fixture.querySelector('[data-render="login-page"]');
      assert.ok(page, 'Login page should be rendered.');
    });

    test('Registration page should be rendered correctly.', (assert) => {
      new RegistrationPage(fixture, {});
      const page = fixture.querySelector('[data-render="registration-page"]');
      assert.ok(page, 'Registration page should be rendered.');
    });

    test('Error page should be rendered correctly.', (assert) => {
      new ErrorPage(fixture, {});
      const page = fixture.querySelector('[data-render="error-page"]');
      assert.ok(page, 'Error page should be rendered.');
    });
  });
  module('File list rendering module:', function(hook) {
    test('File list should render files that were sent by constructor.', (assert) => {
      const name = 'Test';
      const items = [{id: '1', parentId: '0', name: 'Test', itemsAmount: '0', type: 'folder'}];
      new FileList(fixture, {items});
      const result = fixture.querySelector('[data-render="table"] a').innerText;
      assert.strictEqual(result, name, 'Should render files that were sent by constructor.');
    });

    test('File list should render files that were sent by setter.', (assert) => {
      const name = 'Test';
      const items = [{id: '1', parentId: '0', name: 'Test', itemsAmount: '0', type: 'folder'}];
      const fileList = new FileList(fixture, {});
      fileList.items = items;
      const result = fixture.querySelector('[data-render="table"] a').innerText;
      assert.strictEqual(result, name, 'Should render files that were sent by constructor.');
    });
  });
});
