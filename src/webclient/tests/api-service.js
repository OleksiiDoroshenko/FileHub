import ApiService from '../main/services/api-secrvice';
import UserData from '../models/user-data';
import AuthorizationError from '../models/errors/authorization-error';
import fetchMock from '../../../node_modules/fetch-mock/esm/client.js';

const {module, test} = QUnit;
const service = new ApiService(false);
fetchMock.config.overwriteRoutes = true;

export default module('App service test', function(hook) {
  test('Register method should register user if data is valid.', async (assert) => {
    assert.expect(1);
    fetchMock.post('/register', (((url) => {
      return 200;
    })));
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    service.register(userData)
      .then(() => {
        assert.ok('ok', 'Register method should register user if data is valid.');
      });
  });

  test('Register method should return exception if user with this login already registered.', async (assert) => {
    assert.expect(1);
    fetchMock.post('/register', (((url, opts) => {
      const userData = new UserData(opts.body.login, opts.body.password);
      if (userData.login === 'Alex1') {
        throw new Error();
      }
      return 200;
    })));
    const userData = new UserData('Alex1', 'Mdaskjdsdasa1543');
    service.register(userData).catch((error) => {
      assert.ok(true, 'Should return exception if user with this login already registered.');
    });
  });

  test('Login method should return exception if user with this login is not registered.', async (assert) => {
    assert.expect(1);
    fetchMock.post('/login', (((url, opts) => {
      const userData = new UserData(opts.body.login, opts.body.password);
      if (userData.login === 'Vas9') {
        throw new AuthorizationError('');
      }
      return 200;
    })));
    const userData = new UserData('Vas9', 'Mdaskjdsdasa1543');
    await service.logIn(userData).catch((error) => {
      assert.ok(error instanceof AuthorizationError, 'Should throw AuthorizationError.');
    });
  });
});
