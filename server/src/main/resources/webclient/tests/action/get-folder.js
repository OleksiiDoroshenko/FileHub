import ApiService from '../../main/services/api-service/index.js';
import StateManager from '../../main/services/state-manager/index.js';
import FolderLoadingMutator from '../../main/services/state-manager/mutators/folder-loading-mutator/index.js';
import FolderMutator from '../../main/services/state-manager/mutators/folder-mutator/index.js';
import GetFolderAction from '../../main/services/state-manager/actions/get-folder/index.js';
import NotFoundError from '../../models/errors/not-found-error/index.js';
import FolderLoadingErrorMutator
  from '../../main/services/state-manager/mutators/folder-loading-error-mutator/index.js';

const {module, test} = QUnit;

export default module('Get folder action test: ', function(hook) {

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
