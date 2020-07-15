import LoginPage from '../main/components/login-page/index.js';
import ErrorPage from '../main/components/error-page/index.js';
import Router from '../main/router.js';
import ApiService from '../main/services/api-service/index.js';
import StateManager from '../main/services/state-manager/index.js';

const {module, test} = QUnit;

export default module('Router test', function(hook) {
  let fixture;
  let router;
  let window;
  const apiService = new ApiService(false);
  const pageMapping = {
    '/login': () => new LoginPage(fixture, apiService, {}),
    'error': () => new ErrorPage(fixture, {}),
    'default': '/login',
  };
  const stateManager = new StateManager({items: []}, apiService);

  hook.beforeEach(() => {
    fixture = document.getElementById('qunit-fixture');
    window = {
      location: {
        hash: '',
      },
      addEventListener: () => {
      },
    };
  });

  test('should generate correct page from valid hash', (assert) => {
    window.location.hash = '#/login';
    router = new Router(fixture, window, pageMapping);
    const page = fixture.querySelector('[data-render="login-page"]');
    assert.ok(page, 'Should show correct page from valid hash.');
  });

  test('should generate error page from invalid hash', (assert) => {
    window.location.hash = '#/dadawdwads';
    router = new Router(fixture, window, pageMapping);
    const page = fixture.querySelector('[data-render="error-page"]');
    assert.ok(page, 'Should show correct page from valid hash.');
  });

  test('should generate default page from empty hash', (assert) => {
    window.location.hash = '#/';
    router = new Router(fixture, window, pageMapping);
    const page = fixture.querySelector('[data-render="login-page"]');
    assert.ok(page, 'Should show correct page from valid hash.');
  });
});
