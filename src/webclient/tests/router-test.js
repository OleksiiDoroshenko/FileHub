import LoginPage from '../main/components/login-page';
import ErrorPage from '../main/components/error-page';
import Router from '../main/roter.js';

const {module, test} = QUnit;

export default module('Router test', function(hook) {
  let fixture;
  let router;
  const pageMapping = {
    '/login': LoginPage,
    'error': ErrorPage,
    'default': LoginPage,
  };

  hook.beforeEach(() => {
    fixture = document.getElementById('qunit-fixture');
    router = new Router(fixture, pageMapping);
  });

  test('should generate correct page from valid hash', (assert) => {
    fixture.innerHTML = '';
    router.renderPage('/login');
    const currentPage = fixture.querySelector('h1').innerText;
    assert.strictEqual(currentPage, 'Login', 'Should show correct page from valid hash.');
  });

  test('should generate error page from invalid hash', (assert) => {
    fixture.innerHTML = '';
    router.renderPage('/dasdafsfasfa');
    const currentPage = fixture.querySelector('h1').innerText;
    assert.strictEqual(currentPage, 'Error 404. Page not found.', 'Should generate error page from invalid hash.');
  });

  test('should generate default page from empty hash', (assert) => {
    fixture.innerHTML = '';
    router.renderPage('#/');
    const currentPage = fixture.querySelector('h1').innerText;
    assert.strictEqual(currentPage, 'Login', 'Should generate default page from empty hash.');
  });
});
