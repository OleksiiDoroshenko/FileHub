import ApiService from '../main/services/api-service';
import UserData from '../models/user-data';
import fetchMock from '../../../node_modules/fetch-mock/esm/client.js';
import AuthorizationError from '../models/errors/authorization-error';
import ServerValidationErrors from '../models/errors/server-validation-errors';
import VerificationError from '../models/errors/verification-error';
import NotFoundError from '../models/errors/not-found-error';

const {module, test} = QUnit;
const service = new ApiService(false);

export default module('API service test', function(hook) {
  const token = 'test';
  localStorage.setItem(token, token);
  hook.beforeEach(() => {
    fetchMock.reset();
  });

  test('Register method should send proper request with correct data.', async (assert) => {
    assert.expect(5);
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    const matcher = '/register';
    fetchMock.post(matcher, (((url, request) => {
      assert.strictEqual(userData.login, request.body.login, 'Should send correct login.');
      assert.strictEqual(userData.password, request.body.password, 'Should send correct password.');
      return 200;
    })));
    await service.register(userData);
    assert.ok(fetchMock.called(matcher), 'Should send registration request.');
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request.');
  });

  test('Register method should handle 422 error.', async (assert) => {
    _testVerificationServerError('post', assert, '/register', service, 'register');
  });

  test('Register method should handle 500 error.', async (assert) => {
    _testInternalServerError('post', assert, '/register', service, 'register');
  });

  test('Register method should handle 404 error.', async (assert) => {
    _testNotFoundError('post', assert, '/register', service, 'register');
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

  test('Login method should handle 404 error.', async (assert) => {
    _testNotFoundError('post', assert, '/login', service, 'logIn');
  });

  test('Login method should send proper request with correct data.', async (assert) => {
    assert.expect(6);
    const userData = new UserData('Alex', 'Mdaskjdsdasa1543');
    const matcher = '/login';
    fetchMock.post(matcher, (((url, request) => {
      assert.strictEqual(userData.login, request.body.login, 'Should send correct login.');
      assert.strictEqual(userData.password, request.body.password, 'Should send correct password.');
      return {token: 'test'};
    })));
    await service.logIn(userData).then(() => {
      assert.strictEqual('test', localStorage.getItem('token'), 'Should accept correct token.');
    });
    assert.ok(fetchMock.called(matcher), 'Should send login request.');
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  });

  test('Get root method should send proper request with correct data.', async (assert) => {
    assert.expect(5);
    const matcher = '/folder/root';
    fetchMock.get(matcher, (((url, request) => {
      assert.strictEqual(token, request.headers.token, 'Should send correct token.');
      return {
        body: {
          folder: {id: '0', parentId: '', name: 'Root', itemsAmount: '4', type: 'folder'},
        },
      };
    })));
    await service.getRoot().then((response) => {
      assert.strictEqual('0', response.folder.id, 'Should accept correct token.');
    });
    assert.ok(fetchMock.called(matcher), 'Should send folder/root request.');
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  });

  test('Get root method should handle 401 error', async (assert) => {
    _testAuthorizationServerError('get', assert, '/folder/root', service, 'getRoot');
  });

  test('Get root method should handle 500 error', async (assert) => {
    _testInternalServerError('get', assert, '/folder/root', service, 'getRoot');
  });

  test('Get root method should handle 404 error', async (assert) => {
    _testNotFoundError('get', assert, '/folder/root', service, 'getRoot');
  });

  test('Upload file method should send proper request with correct data.', async (assert) => {
    assert.expect(5);
    const matcher = 'express:/folder/:id/file';
    fetchMock.post(matcher, (((url, request) => {
      const file = request.body.file;
      assert.strictEqual(file.name, fileName, 'Should send proper file.');
      return 200;
    })));
    const fileName = 'test';
    await service.uploadFile('0', new File([], fileName))
      .then((response) => {
        assert.strictEqual(response, 200, 'Should return 200 if everything is ok.');
      });
    assert.ok(fetchMock.called(matcher), 'Should send upload file request.');
    assert.ok(fetchMock.calls().length === 1, `Should send upload file request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  });

  test('Upload file method should handle 404 error', async (assert) => {
    _testNotFoundError('post', assert, 'express:/folder/:id/file', service, 'uploadFile');
  });

  test('Upload file method should handle 500 error', async (assert) => {
    _testInternalServerError('post', assert,
      'express:/folder/:id/file', service, 'uploadFile');
  });

  test('Upload file method should handle 401 error', async (assert) => {
    _testAuthorizationServerError('post', assert,
      'express:/folder/:id/file', service, 'uploadFile');
  });

  test('Get items method should send proper request with correct data.', async (assert) => {
    assert.expect(5);
    const matcher = 'express:/folder/:id/content';
    const parentId = '0';
    const items = [{id: '1', parentId: parentId, name: 'Documents', itemsAmount: '2', type: 'folder'}];
    fetchMock.get(matcher, (((url) => {
      const id = url.split('/')[2];
      assert.strictEqual(id, parentId, 'Should send proper id.');
      return items;
    })));
    await service.getItems(parentId)
      .then((response) => {
        assert.deepEqual(response, items, 'Should return proper files..');
      });
    assert.ok(fetchMock.called(matcher), 'Should send get items request.');
    assert.ok(fetchMock.calls().length === 1, `Should send get items request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  });

  test('Get items method should handle 404 error', async (assert) => {
    _testNotFoundError('get', assert,
      'express:/folder/:id/content', service, 'getItems');
  });

  test('Get items method should handle 500 error', async (assert) => {
    _testInternalServerError('get', assert,
      'express:/folder/:id/content', service, 'getItems');
  });

  test('Get items method should handle 401 error', async (assert) => {
    _testAuthorizationServerError('get', assert,
      'express:/folder/:id/content', service, 'getItems');
  });

  test('Log out method should handle 500 error', async (assert) => {
    assert.expect(5);
    localStorage.clear();
    localStorage.setItem('token', 'testToken');
    const matcher = '/logout';
    fetchMock.post(matcher, (((url) => {
      return 500;
    })));
    await service.logOut().catch((error) => {
      assert.strictEqual(error.message, 'Internal Server Error', 'Should handle 500 error.');
    });
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
    assert.notOk(localStorage.getItem('token'),
      'After log out method is triggered, local storage should not contain token.');
  });

  test('Log out method should handle 401 error', async (assert) => {
    assert.expect(5);
    localStorage.clear();
    localStorage.setItem('token', 'testToken');
    const matcher = '/logout';
    fetchMock.post(matcher, (((url) => {
      return 401;
    })));
    await service.logOut().catch((error) => {
      assert.ok(error instanceof AuthorizationError, 'Should handle 401 error.');
    });
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
    assert.notOk(localStorage.getItem('token'),
      'After log out method is triggered, local storage should not contain token.');
  });

  test('Log out method should send proper request with correct data.', async (assert) => {
    assert.expect(5);
    localStorage.clear();
    const token = 'testToken';
    localStorage.setItem('token', token);
    const matcher = '/logout';
    fetchMock.post(matcher, (((url, request) => {
      assert.strictEqual(token, request.headers.token, 'Should send correct token.');
      return 200;
    })));
    await service.logOut();
    assert.notOk(localStorage.getItem('token'),
      'After log out method is triggered in the local storage should not exist token.');
    assert.ok(fetchMock.called(matcher), 'Should send folder/root request.');
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  });

  async function _testInternalServerError(fetchMethod, assert, matcher, service, method) {
    assert.expect(4);
    fetchMock[fetchMethod](matcher, (((url) => {
      return 500;
    })));
    assert.rejects(service[method]({}), new Error('Internal Server Error')
      , 'Should handle 500 error.');
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  }

  function _testVerificationServerError(fetchMethod, assert, matcher, service, method) {
    assert.expect(4);
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
    assert.ok(fetchMock.calls(matcher).length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  }

  function _testNotFoundError(fetchMethod, assert, matcher, service, method) {
    assert.expect(4);
    fetchMock[fetchMethod](matcher, (((url) => {
      return {
        status: 404,
        body: '',
      };
    })));
    assert.rejects(service[method]({}), new NotFoundError('')
      , 'Should handle 404 error.');
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls(matcher).length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  }

  function _testAuthorizationServerError(fetchMethod, assert, matcher, service, method) {
    assert.expect(4);
    fetchMock[fetchMethod](matcher, (((url) => {
      return {
        status: 401,
        body: '',
      };
    })));
    assert.rejects(service[method]({}), new AuthorizationError('')
      , 'Should handle 401 error.');
    assert.ok(fetchMock.called(matcher), `Should send ${matcher} request.`);
    assert.ok(fetchMock.calls().length === 1, `Should send ${matcher} request.`);
    assert.ok(fetchMock.done(matcher), 'Should send only one request');
  }
});


