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

  test('Register method should handle 422 error.', async (assert) => {
    _testVerificationServerError('post', assert, '/register', service, 'register');
  });

  test('Register method should handle 500 error.', async (assert) => {
    _testInternalServerError('post', assert, '/register', service, 'register');
  });

  test('Login method should handle 401 error.', async (assert) => {
    _testAuthorizationServerError('post', assert, '/login', service, 'logIn');
  });

  test('Login method should handle 422 error.', async (assert) => {
    _testVerificationServerError('post', assert, '/login', service, 'logIn');
  });

  test('Login method should handle 500 error.', async (assert) => {
    _testInternalServerError('post', assert, '/login', service, 'logIn');
  });

  test('Login method should send proper request with correct data.', async (assert) => {
    assert.expect(5);
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    fetchMock.post('/login', (((url, request) => {
      assert.strictEqual(userData.login, request.body.login, 'Should send correct login.');
      assert.strictEqual(userData.password, request.body.password, 'Should send correct password.');
      return {token: 'test'};
    })));
    await service.logIn(userData).then(() => {
      assert.strictEqual('test', localStorage.getItem('token'), 'Should accept correct token.');
    });
    assert.ok(fetchMock.called('/login'), 'Should send login request.');
    assert.ok(fetchMock.calls('/login').length === 1, 'Should send only one request');
  });

  test('Get root method should send proper request with correct data.', async (assert) => {
    assert.expect(4);
    const token = 'test-token';
    localStorage.setItem('token', token);
    fetchMock.get('/folder/root', (((url, request) => {
      assert.strictEqual(token, request.headers.token, 'Should send correct token.');
      return {
        body: {
          folder: {id: '0', parentId: '', name: 'Root', itemsAmount: '4', type: 'folder'},
        },
      };
    })));
    await service.getRoot().then(response => {
      assert.strictEqual('0', response.folder.id, 'Should accept correct token.');
    });
    assert.ok(fetchMock.called('/folder/root'), 'Should send folder/root request.');
    assert.ok(fetchMock.calls('/folder/root').length === 1, 'Should send only one request');
  });

  test('Get root method should handle 401 error', async (assert) => {
    _testAuthorizationServerError('get', assert, '/folder/root', service, 'getRoot');
  });

  test('Get root method should handle 500 error', async (assert) => {
    _testInternalServerError('get', assert, '/folder/root', service, 'getRoot');
  });

  function _testInternalServerError(fetchMethod, assert, matcher, service, method) {
    assert.expect(3);
    fetchMock[fetchMethod](matcher, (((url) => {
      return {
        status: 500,
        body: {
          error: new Error(''),
        },
      };
    })));
    assert.rejects(service[method]({}), new Error('')
      , 'Should handle 500 error.');
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls(matcher).length === 1, 'Should send only one request');
  }

  function _testVerificationServerError(fetchMethod, assert, matcher, service, method) {
    assert.expect(3);
    fetchMock[fetchMethod](matcher, (((url) => {
      return {
        status: 422,
        body: {
          errors: [new VerificationError('', '')],
        },
      };
    })));
    assert.rejects(service[method]({}), new ServerValidationErrors()
      , 'Should handle 422 error.');
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls(matcher).length === 1, 'Should send only one request');
  }

  function _testAuthorizationServerError(fetchMethod, assert, matcher, service, method) {
    assert.expect(3);
    fetchMock[fetchMethod](matcher, (((url) => {
      return {
        status: 401,
        body: {
          error: new AuthorizationError(''),
        },
      };
    })));
    assert.rejects(service[method]({}), new AuthorizationError('')
      , 'Should handle 401 error.');
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls(matcher).length === 1, 'Should send only one request');
  }
});


