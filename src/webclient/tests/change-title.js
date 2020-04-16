import LoginPage from '../main/components/login-page';
import ErrorPage from '../main/components/error-page';
import Router from '../main/router.js';
import {changeTitle} from '../main/services/change-title';

const {module, test} = QUnit;

export default module('Change title test', function(hook) {
  test('Should change document title.', (assert) => {
    const title = 'Test title';
    changeTitle(title);
    assert.strictEqual(document.title, title, 'Should correctly change document title.');
  });

});
