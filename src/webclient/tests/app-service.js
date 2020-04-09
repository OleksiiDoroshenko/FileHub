import AppService from '../main/services/app-secrvice';
import UserData from '../models/user-data';
import AuthorizationError from '../models/errors/authorization-error';

const {module, test} = QUnit;
const service = new AppService(true);

export default module('Authentication service test', function(hook) {
  test('Register method should returns exception when password is invalid.', async (assert) => {
    const userData = new UserData('Alex', 'maksdlo21');
    const response = service.register(userData);
    await response.catch((errors) => {
      let message = errors[0].message;
      assert.strictEqual(message, 'Password should be longer than 10 characters.',
        'Should throw Verification error');
    });
  });

  test('Register method should register user if data is valid.', async (assert) => {
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    const response = service.register(userData);
    response.then(() => {
      assert.ok('ok', 'Register method should register user if data is valid.');
    });
  });

  test('Register method should return exception if user with this login already registered.', async (assert) => {
    const userData = new UserData('Alex1', 'Mdaskjdsdasa1543');
    const promise = service.register(userData);
    promise.then(() => {
      const response = service.register(userData);
      response.catch((errors) => {
        assert.strictEqual(errors.message, 'User with this login already exists.',
          'Should throw exception that user already registered.');
      });
    });
  });

  test('Login method should return exception if user with this login is not registered.', async (assert) => {
    const userData = new UserData('Vas9', 'Mdaskjdsdasa1543');
    const response = service.login(userData);
    await response.catch((error) => {
      assert.ok(error instanceof AuthorizationError, 'Should throw AuthorizationError.');
    });
  });

  test('Login method should redirect user to the fileHub page if everything is ok.', async (assert) => {
    const userData = new UserData('admin', 'Admin123456');
    const response = service.login(userData);
    response.then((message) => {
      console.log(message);
      assert.ok(`${userData.login}-token`, 'Should register and redirect user.');
    });
  });
});
