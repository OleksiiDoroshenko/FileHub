import Validator from '../main/services/validator';

const {module, test} = QUnit;

export default module('Validator test', function(hook) {
  test('should validate correct login', (assert) => {
    const validator = new Validator();
    const login = 'Admin';
    assert.ok(validator.validateLogin(login), 'Should correctly validate login.');
  });

  test('should validate correct password', (assert) => {
    const validator = new Validator();
    const password = 'SrkizonD4512';
    assert.ok(validator.validatePassword(password), 'Should correctly validate password.');
  });

  test('should not validate to short login', (assert) => {
    const validator = new Validator();
    const login = 'Adn';
    const promise = validator.validateLogin(login);
    promise.catch((error) => {
      assert.ok(error.message.startsWith('Login should be longer than'), 'Should not validate to short login');
    });
  });

  test('should not validate login with special symbols', (assert) => {
    const validator = new Validator();
    const login = '!@_)#';
    const promise = validator.validateLogin(login);
    promise.catch((error) => {
      assert.strictEqual(error.message, 'Login should contains only latin letters or numbers.',
        'Should not validate login with special symbols.');
    });
  });

  test('should not validate to short password', (assert) => {
    const validator = new Validator();
    const password = 'rki2';
    const promise = validator.validatePassword(password);
    promise.catch((error) => {
      assert.ok(error.message.startsWith('Password should be longer than'),
        'Should not validate to short password');
    });
  });

  test('should not validate password without uppercase letter', (assert) => {
    const validator = new Validator();
    const password = 'rkizon4512';
    const promise = validator.validatePassword(password);
    promise.catch((error) => {
      assert.strictEqual(error.message,
        'Password should contain at least 1 uppercase and lowercase letters and 1 digit.',
        'Should not validate password without uppercase letter.');
    });
  });

  test('should not validate password without digits', (assert) => {
    const validator = new Validator();
    const password = 'SrkizonDdasdasd';
    const promise = validator.validatePassword(password);
    promise.catch((error) => {
      assert.strictEqual(error.message,
        'Password should contain at least 1 uppercase and lowercase letters and 1 digit.',
        'Should not validate password without uppercase letter.');
    });
  });

  test('should not validate password without lowercase letter', (assert) => {
    const validator = new Validator();
    const password = 'SSSASHKFS5456122';
    const promise = validator.validatePassword(password);
    promise.catch((error) => {
      assert.strictEqual(error.message,
        'Password should contain at least 1 uppercase and lowercase letters and 1 digit.',
        'Should not validate password without uppercase letter.');
    });
  });
});
