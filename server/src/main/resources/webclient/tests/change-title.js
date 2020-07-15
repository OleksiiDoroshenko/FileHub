import TitleService from '../main/services/change-title/index.js';

const {module, test} = QUnit;

export default module('Change title test', function(hook) {
  test('Should change document title.', (assert) => {
    const title = 'Test';
    new TitleService().changeTitle(title);
    assert.strictEqual(document.title, `${title} - FileHub`, 'Should correctly change document title.');
  });
});
