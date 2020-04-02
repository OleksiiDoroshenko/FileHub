import LoginPage from '../main/components/login-page';
import ErrorPage from '../main/components/error-page';
import Router from '../main/router.js';

const {module, test} = QUnit;

export default module('Router test', function(hook) {
  let fixture;
  let router;
  let window;

  const pageMapping = {
    '/login': () => new LoginPage(fixture, {}),
    'error': () => new ErrorPage(fixture, {}),
    'default': '/login',
  };

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
    assert.strictEqual(window.location.hash, '#/login', 'Should show correct page from valid hash.');
  });

  test('should generate error page from invalid hash', (assert) => {
    window.location.hash = '#/dadawdwads';
    router = new Router(fixture, window, pageMapping);
    assert.strictEqual(window.location.hash, '#/error', 'Should generate error page from invalid hash.');
  });

  test('should generate default page from empty hash', (assert) => {
    window.location.hash = '#/';
    router = new Router(fixture, window, pageMapping);
    assert.strictEqual(window.location.hash, '#/login', 'Should generate default page from empty hash.');
  });
});
