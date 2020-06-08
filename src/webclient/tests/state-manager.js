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
import RemoveItemFromDeletingListMutator
  from '../main/services/state-manager/mutators/remove-item-from-deleting-list-mutator';
import AddItemToDeletingListMutator from '../main/services/state-manager/mutators/add-item-to-deleting-list-mutator';
import ItemsDeletingErrorMutator from '../main/services/state-manager/mutators/items-deleting-error-mutator';
import ItemUploadingErrorMutator from '../main/services/state-manager/mutators/item-uploading-error-mutator';
import FolderLoadingMutator from '../main/services/state-manager/mutators/folder-loading-mutator';
import FolderLoadingErrorMutator from '../main/services/state-manager/mutators/folder-loading-error-mutator';
import FolderMutator from '../main/services/state-manager/mutators/folder-mutator';
import GetFolderAction from '../main/services/state-manager/actions/get-folder';
import NotFoundError from '../models/errors/not-found-error';

const {module, test} = QUnit;

export default module('State manager test: ', function(hook) {
  let stateManager = new StateManager({}, new ApiService());

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
      stateManager = new StateManager({uploadingItemIds: new Set()}, new ApiService());
      const itemId = '1';
      const resultList = new Set(itemId);
      const mutator = new AddItemToUploadingListMutator(itemId);
      _testMutatorWithDeepEqual(assert, mutator, 'uploadingItemIds', resultList);
    });

    test('Remove from uploading list mutator should change state\'s uploading list', async (assert) => {
      const itemId = '1';
      stateManager = new StateManager({uploadingItemIds: new Set(itemId)}, new ApiService());
      const resultList = new Set();
      const mutator = new RemoveItemToUploadingListMutator(itemId);
      _testMutatorWithDeepEqual(assert, mutator, 'uploadingItemIds', resultList);
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


    test('Add to deleting list mutator should change state\'s deleting list', async (assert) => {
      stateManager = new StateManager({deletingItemIds: new Set()}, new ApiService(false));
      const itemId = '1';
      const resultList = new Set(itemId);
      const mutator = new AddItemToDeletingListMutator(itemId);
      _testMutatorWithDeepEqual(assert, mutator, 'deletingItemIds', resultList);
    });

    test('Remove from deleting list mutator should change state\'s deleting list', async (assert) => {
      const itemId = '1';
      stateManager = new StateManager({deletingItemIds: new Set(itemId)}, new ApiService(false));
      const resultList = new Set();
      const mutator = new RemoveItemFromDeletingListMutator(itemId);
      _testMutatorWithDeepEqual(assert, mutator, 'deletingItemIds', resultList);
    });

    test('Items deleting error mutator should change state\'s deletingError field', async (assert) => {
      const error = new Error('test');
      const mutator = new ItemsDeletingErrorMutator(error);
      _testMutator(assert, mutator, 'deletingError', error);
    });

    test('Item uploading error mutator should change state\'s uploadingError field', async (assert) => {
      const error = new Error('test');
      const mutator = new ItemUploadingErrorMutator(error);
      _testMutator(assert, mutator, 'uploadingError', error);
    });

    test('Folder loading mutator should change state\'s isFolderLoading state', async (assert) => {
      const mutator = new FolderLoadingMutator(true);
      _testMutator(assert, mutator, 'isFolderLoading', true);
    });

    test('Folder loading error mutator should change state\'s folderLoadingError field.', async (assert) => {
      const error = new Error('test');
      const mutator = new FolderLoadingErrorMutator(error);
      _testMutator(assert, mutator, 'folderLoadingError', error);
    });

    test('Folder mutator should change state\'s folder field.', async (assert) => {
      const folder = {name: 'test', id: '0', parentId: '1', type: 'folder'};
      const mutator = new FolderMutator(folder);
      _testMutatorWithDeepEqual(assert, mutator, 'folder', folder);
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

  module('Action test: ', function(hook) {
    test('Get folder action should call specific steps', async (assert) => {
      assert.expect(6);
      const folderId = '0';
      const folder = {name: 'test', id: '0', parentId: '1', type: 'folder'};

      let apiService = new ApiService();
      apiService.getFolder = async (id) => {
        assert.strictEqual(id, folderId, 'Api service method should be called with proper id.');
        return {folder};
      };

      let stateManager = new StateManager({}, apiService);
      stateManager.mutate = (mutator) => {
        if (mutator instanceof FolderLoadingMutator) {
          assert.step('FolderLoadingMutator');
        } else if (mutator instanceof FolderMutator) {
          assert.deepEqual(mutator.folder, folder, 'Mutator should be created with proper params.');
          assert.step('FolderMutator');
        }
      };

      const action = new GetFolderAction(folderId);
      await action.apply(stateManager, apiService);
      assert.verifySteps(['FolderLoadingMutator', 'FolderMutator', 'FolderLoadingMutator']);
    });

    test('Get folder action test when error was raised.', async (assert) => {
      assert.expect(5);
      const folderId = '0';

      let apiService = new ApiService();
      apiService.getFolder = async (id) => {
        assert.strictEqual(id, folderId, 'Api service method should be called with proper id.');
        throw new NotFoundError('');
      };

      let stateManager = new StateManager({}, apiService);
      stateManager.mutate = (mutator) => {
        if (mutator instanceof FolderLoadingMutator) {
          assert.step('FolderLoadingMutator');
        } else if (mutator instanceof FolderLoadingErrorMutator) {
          assert.step('FolderLoadingErrorMutator');
        }
      };

      const action = new GetFolderAction(folderId);
      await action.apply(stateManager, apiService);
      assert.verifySteps(['FolderLoadingMutator', 'FolderLoadingErrorMutator',
        'FolderLoadingMutator']);
    });
  });

});
