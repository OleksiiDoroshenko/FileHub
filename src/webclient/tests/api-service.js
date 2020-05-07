import ApiService from '../main/services/api-service';
import UserData from '../models/user-data';
import fetchMock from '../../../node_modules/fetch-mock/esm/client.js';
import AuthorizationError from '../models/errors/authorization-error';
import ServerValidationErrors from '../models/errors/server-validation-errors';
import VerificationError from '../models/errors/verification-error';

const {module, test} = QUnit;
const service = new ApiService(false);

export default module('API service test', function(hook) {

  hook.beforeEach(() => {
    fetchMock.reset();
  });

  test('Register method should send proper request with correct data.', async (assert) => {
    assert.expect(3);
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    fetchMock.post('/register', (((url, request) => {
      assert.strictEqual(userData.login, request.body.login, 'Should send correct login.');
      assert.strictEqual(userData.password, request.body.password, 'Should send correct password.');
      return 200;
    })));
    await service.register(userData);
    assert.ok(fetchMock.called('/register'), 'Should send registration request.');
  });

  test('Register method should handle 401 error.', async (assert) => {
    assert.expect(2);
    fetchMock.post('/register', (((url) => {
      return {
        status: 401,
        body: {
          error: new AuthorizationError(''),
        },
      };
    })));
    await assert.rejects(service.register({}), new AuthorizationError(''),
      'Should handle 401 error.');
    assert.ok(fetchMock.called('/register'), 'Should send registration request.');
  });

  test('Register method should handle 422 error.', async (assert) => {
    assert.expect(2);
    fetchMock.post('/register', (((url) => {
      return {
        status: 422,
        body: {
          errors: [new VerificationError('', '')],
        },
      };
    })));
    await assert.rejects(service.register({}), new ServerValidationErrors(''),
      'Should handle 422 error.');
    assert.ok(fetchMock.called('/register'), 'Should send registration request.');
  });

  test('Register method should handle 500 error.', async (assert) => {
    assert.expect(2);
    fetchMock.post('/register', (((url) => {
      return {
        status: 500,
        body: {
          error: new Error(''),
        },
      };
    })));
    assert.rejects(service.register({}), new Error('')
      , 'Should handle 500 error.');
    assert.ok(fetchMock.called('/register'), 'Should send register request.');
  });

  test('Login method should handle 401 error.', async (assert) => {
    assert.expect(2);
    fetchMock.post('/login', (((url, opts) => {
      return {
        status: 401,
        body: {
          error: new AuthorizationError(''),
        },
      };
    })));
    assert.rejects(service.logIn({}), new AuthorizationError('')
      , 'Should handle 401 error.');
    assert.ok(fetchMock.called('/login'), 'Should send login request.');
  });

  test('Login method should handle 422 error.', async (assert) => {
    assert.expect(2);
    fetchMock.post('/login', (((url) => {
      return {
        status: 422,
        body: {
          errors: [new VerificationError('', '')],
        },
      };
    })));
    assert.rejects(service.logIn({}), new ServerValidationErrors()
      , 'Should handle 422 error.');
    assert.ok(fetchMock.called('/login'), 'Should send login request.');
  });

  test('Login method should handle 500 error.', async (assert) => {
    assert.expect(2);
    fetchMock.post('/login', (((url) => {
      return {
        status: 500,
        body: {
          error: new Error(''),
        },
      };
    })));
    assert.rejects(service.logIn({}), new Error('')
      , 'Should handle 500 error.');
    assert.ok(fetchMock.called('/login'), 'Should send login request.');
  });

  test('Login method should send proper request with correct data.', async (assert) => {
    assert.expect(4);
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    fetchMock.post('/login', (((url, request) => {
      assert.strictEqual(userData.login, request.body.login, 'Should send correct login.');
      assert.strictEqual(userData.password, request.body.password, 'Should send correct password.');
      return {token: 'test'};
    })));
    await service.logIn(userData).then(token => {
      assert.strictEqual('test', token, 'Should accept correct token.');
    });
    assert.ok(fetchMock.called('/login'), 'Should send login request.');
  });
});
