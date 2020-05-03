import Validator from '../main/services/validator';

const {module, test} = QUnit;

export default module('Validator test: ', function(hook) {

  test('should permit correct login.', (assert) => {
    const validator = new Validator();
    let login = 'Admin';
    assert.ok(validator.validateLogin(login), 'Should permit correct login.');
  });

  test('should permit correct password.', (assert) => {
    const validator = new Validator();
    let password = 'SrkizonD4512';
    assert.ok(validator.validatePassword(password), 'Should permit correct password.');
  });

  test('should throw exception when login is too short.', (assert) => {
    const validator = new Validator();
    let login = 'Adn';
    const promise = validator.validateLogin(login);
    promise.catch(error => {
      assert.ok(error.message.startsWith('Login should be longer than'),
        'Should throw exception when login is too short.');
    });
  });

  test('should throw exception when login contains special symbols.', (assert) => {
    const validator = new Validator();
    let login = '!@_)#';
    const promise = validator.validateLogin(login);
    promise.catch(error => {
      assert.strictEqual(error.message, 'Login should contains only latin letters or numbers.',
        'Should throw exception when login contains special symbols.');
    });
  });

  test('should throw exception when password is too short.', (assert) => {
    const validator = new Validator();
    let password = 'rki2';
    const promise = validator.validatePassword(password);
    promise.catch(error => {
      assert.ok(error.message.startsWith('Password should be longer than'),
        'Should throw exception when password is too short.');

    });
  });

  test('should throw exception when password does not contains uppercase letter.', (assert) => {
    const validator = new Validator();
    let password = 'rkizon4512';
    const promise = validator.validatePassword(password);
    promise.catch(error => {
      assert.strictEqual(error.message,
        'Password should contain at least 1 uppercase and lowercase letters and 1 digit.',
        'Should throw exception when password does not contains uppercase letter.');
    });
  });

  test('should throw exception when password does not contains digits.', (assert) => {
    const validator = new Validator();
    let password = 'SrkizonDdasdasd';
    const promise = validator.validatePassword(password);
    promise.catch(error => {
      assert.strictEqual(error.message,
        'Password should contain at least 1 uppercase and lowercase letters and 1 digit.',
        'Should throw exception when password does not contains digits.');
    });
  });

  test('should throw exception when password does not contains lowercase letter.', (assert) => {
    const validator = new Validator();
    let password = 'SSSASHKFS5456122';
    const promise = validator.validatePassword(password);
    promise.catch(error => {
      assert.strictEqual(error.message,
        'Password should contain at least 1 uppercase and lowercase letters and 1 digit.',
        'Should throw exception when password does not contains lowercase letter.');
    });
  });
});
