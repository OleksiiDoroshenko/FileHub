import ApiService from '../main/services/api-service';
import UserData from '../models/user-data';
import AuthorizationError from '../models/errors/authorization-error';
import fetchMock from '../../../node_modules/fetch-mock/esm/client.js';

const {module, test} = QUnit;
const service = new ApiService(false);
fetchMock.config.overwriteRoutes = true;

export default module('API service test', function(hook) {
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
        throw new AuthorizationError('');
      }
      return 200;
    })));
    const userData = new UserData('Alex1', 'Mdaskjdsdasa1543');
    assert.rejects(service.register(userData), new AuthorizationError(''),
      'Should return exception if user with this login already registered.');
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
    assert.rejects(service.logIn(userData), new AuthorizationError('')
      , 'Should return exception if user with this login is not registered.');
  });

  test('Login method should return user\'s root folder id when data is valid.', async (assert) => {
    assert.expect(1);
    fetchMock.post('/login', (((url, opts) => {
      const userData = new UserData(opts.body.login, opts.body.password);
      if (userData.login === 'Vas9' && userData.password == 'Mdaskjdsdasa1543') {
        return {
          status: 200,
          body: {
            rootId: '0',
          },
        };
      }
      return 200;
    })));
    const userData = new UserData('Vas9', 'Mdaskjdsdasa1543');
    service.logIn(userData).then(id => {
      assert.strictEqual(id, '0', 'Login method should return user\'s root folder id when data is valid.');
    });
  });
});
