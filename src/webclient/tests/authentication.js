import AuthenticationService from '../main/services/authentication';
import UserData from '../models/user-data';
import VerificationError from '../models/errors/verification-error';
import AuthorizationError from '../models/errors/authorization-error';
import ServerValidationError from '../models/errors/server-validation-error';

const {module, test} = QUnit;
const service = new AuthenticationService();

export default module('Authentication service test', function(hook) {
  test('Register method should returns exception when password is invalid.', (assert) => {
    const userData = new UserData('Alex', 'maksdlo21');
    const response = service.register(userData);
    response.catch((errors) => {
      assert.ok(errors[0] instanceof ServerValidationError, 'Should throw Verification error');
    });
  });

  test('Register method should register and redirect user to the login page.', (assert) => {
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    const response = service.register(userData);
    response.then((code) => {
      assert.strictEqual(code, 200, 'Should register user.');
    });
  });

  test('Register method should return exception if user with this login already registered.', (assert) => {
    const userData = new UserData('Alex1', 'Mdaskjdsdasa1543');
    service.register(userData);
    const response = service.register(userData);
    response.catch((error) => {
      assert.ok(error instanceof AuthorizationError, 'Should throw AuthorizationError.');
    });
  });

  test('Login method should return exception if user with this login is not registered.', (assert) => {
    const userData = new UserData('Vas9', 'Mdaskjdsdasa1543');
    const response = service.logIn(userData);
    response.catch((error) => {
      assert.ok(error instanceof AuthorizationError, 'Should throw AuthorizationError.');
    });
  });

  test('Login method should redirect user to the fileHub page if everything is ok.', (assert) => {
    const userData = new UserData('Pet9', 'Mdaskjdsdasa1543');
    service.register(userData);
    const response = service.logIn(userData);
    response.then((code) => {
      assert.strictEqual(code, 200, 'Should register and redirect user.');
    });
  });
});
