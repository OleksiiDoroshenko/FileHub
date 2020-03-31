import AuthenticationService from '../main/services/authentication';

const {module, test} = QUnit;
const service = new AuthenticationService();

export default module('Authentication service test', function(hook) {
  test('Register method should returns exception when password is invalid.', (assert) => {
    const login = 'Alex';
    const password = 'maksdlo21';
    const response = service.register(login, password);
    response.catch((error) => {
      assert.strictEqual(error.code, 401, 'Should throw error with code 401.');
    });
  });

  test('Register method should register and redirect user to the login page.', (assert) => {
    const login = 'Alex';
    const password = 'Markizon4ik160998';
    const response = service.register(login, password);
    response.then((code) => {
      assert.strictEqual(code, 200, 'Should register user.');
    });
  });

  test('Register method should return exception if user with this login already registered.', (assert) => {
    const login = 'Alex1';
    const password = 'Markizon4ik160998';
    service.register(login, password);

    const response = service.register(login, password);
    response.catch((error) => {
      assert.strictEqual(error.code, 422, 'Should throw error with code 422.');
    });
  });

  test('Login method should return exception if user with this login is not registered.', (assert) => {
    const login = 'Vas9';
    const password = 'Markizon4ik160998';
    const response = service.login(login, password);
    response.catch((error) => {
      assert.strictEqual(error.code, 422, 'Should throw error with code 422.');
    });
  });

  test('Login method should redirect user to the fileHub page if everything is ok.', (assert) => {
    const login = 'Pet9';
    const password = 'Markizon4ik160998';
    service.register(login, password);
    const response = service.login(login, password);
    response.then((code) => {
      assert.strictEqual(code, 200, 'Should register and redirect user.');
    });
  });
});
