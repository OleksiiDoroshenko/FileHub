import ApiService from '../main/services/api-service';
import StateManager from '../main/services/state-manager';
import Mutator from '../main/services/state-manager/mutators/mutator.js';
import Action from '../main/services/state-manager/actions/action.js';
import ItemsLoadingMutator from '../main/services/state-manager/mutators/items-loading-mutator';
import ItemsLoadingErrorMutator from '../main/services/state-manager/mutators/items-loading-error-mutator';
import ItemsMutator from '../main/services/state-manager/mutators/items-mutator';
import FolderIdMutator from '../main/services/state-manager/mutators/folder-id-mutator';
import AddItemToUploadingListMutator from '../main/services/state-manager/mutators/add-item-to-uploading-list-mutator';
import RemoveItemToUploadingListMutator
  from '../main/services/state-manager/mutators/remove-item-from-uploading-list-mutator';
import UserLoadingMutator from '../main/services/state-manager/mutators/user-loading-mutator';
import UserLoadingError from '../main/services/state-manager/mutators/user-loading-error-mutator';

const {module, test} = QUnit;

export default module('State manager test: ', function(hook) {
  let stateManager = new StateManager({}, new ApiService(false));

  test('should mutate its state', (assert) => {
    const mutator = new Mutator();
    mutator.apply = (state) => {
      assert.ok(true, 'Should apply mutator.');
    };
    stateManager.mutate(mutator);
  });

  test('should dispatch action', async (assert) => {
    const action = new Action();
    action.apply = (state) => {
      assert.ok(true, 'Should apply action.');
    };
    await stateManager.dispatch(action);
  });

  test('should handle events', async (assert) => {
    stateManager.onStateChanged('test', (state) => {
      assert.ok(state.test, 'Should handle events');
    });
    const mutator = new Mutator();
    mutator.apply = (state) => {
      state.test = true;
    };
    stateManager.mutate(mutator);
  });

  module('Mutator test: ', function(hook) {
    test('Items mutator should change state\'s items', async (assert) => {
      const items = ['test'];
      const mutator = new ItemsMutator(items);
      _testMutator(assert, mutator, 'items', items);
    });

    test('Items loading mutator should change state\'s loading state', async (assert) => {
      const mutator = new ItemsLoadingMutator(true);
      _testMutator(assert, mutator, 'isLoading', true);
    });

    test('Items loading error mutator should change state\'s id', async (assert) => {
      const error = new Error('test');
      const mutator = new ItemsLoadingErrorMutator(error);
      _testMutator(assert, mutator, 'error', error);
    });

    test('Folder id mutator should change state\'s folder id', async (assert) => {
      const folderId = '0';
      const mutator = new FolderIdMutator(folderId);
      _testMutator(assert, mutator, 'folderId', folderId);
    });

    test('Add to uploading list mutator should change state\'s uploading list', async (assert) => {
      stateManager = new StateManager({uploadingItems: []}, new ApiService(false));
      const itemId = '1';
      const resultList = [itemId];
      const mutator = new AddItemToUploadingListMutator(itemId);
      _testMutatorWithDeepEqual(assert, mutator, 'uploadingItems', resultList);
    });

    test('Remove from uploading list mutator should change state\'s uploading list', async (assert) => {
      stateManager = new StateManager({uploadingItems: ['1']}, new ApiService(false));
      const itemId = '1';
      const resultList = [undefined];
      const mutator = new RemoveItemToUploadingListMutator(itemId);
      _testMutatorWithDeepEqual(assert, mutator, 'uploadingItems', resultList);
    });

    test('User loading mutator should change state\'s isUserLoading state', async (assert) => {
      const mutator = new UserLoadingMutator(true);
      _testMutator(assert, mutator, 'isUserLoading', true);
    });

    test('User loading error mutator should change state\'s userLoadingError field.', async (assert) => {
      const error = new Error('test');
      const mutator = new UserLoadingError(error);
      _testMutator(assert, mutator, 'userLoadingError', error);
    });

    function _testMutator(assert, mutator, field, value) {
      assert.notStrictEqual(stateManager.state[field], value, `should not be equal future ${field}`);
      stateManager.mutate(mutator);
      assert.strictEqual(stateManager.state[field], value, `'should change state's ${field} field'`);
    }

    function _testMutatorWithDeepEqual(assert, mutator, field, value) {
      assert.notStrictEqual(stateManager.state[field], value, `should not be equal future ${field}`);
      stateManager.mutate(mutator);
      assert.deepEqual(stateManager.state[field], value, `'should change state's ${field} field'`);
    }
  });

});
