import ApiService from '../../main/services/api-service/index.js';
import StateManager from '../../main/services/state-manager/index.js';
import NotFoundError from '../../models/errors/not-found-error/index.js';
import FolderCreatingMutator from '../../main/services/state-manager/mutators/folder-creating-mutator/index.js';
import FolderCreatingErrorMutator
  from '../../main/services/state-manager/mutators/folder-creating-error-mutator/index.js';
import CreateFolderAction from '../../main/services/state-manager/actions/create-folder/index.js';

const {module, test} = QUnit;

export default module('Create folder action test: ', function(hook) {

  test('Create folder action should call specific steps', async (assert) => {
    assert.expect(4);
    const folderId = '0';

    const apiService = new ApiService();
    apiService.createFolder = async (model) => {
      assert.strictEqual(model.id, folderId, 'Api service method should be called with proper id.');
      return 200;
    };

    const stateManager = new StateManager({}, apiService);
    stateManager.mutate = (mutator) => {
      if (mutator instanceof FolderCreatingMutator) {
        assert.step('FolderCreatingMutator');
      }
    };

    const action = new CreateFolderAction({id: folderId, name: 'test'});
    await action.apply(stateManager, apiService);
    assert.verifySteps(['FolderCreatingMutator', 'FolderCreatingMutator']);
  });

  test('Create folder action test when error was raised.', async (assert) => {
    assert.expect(5);
    const folderId = '0';

    const apiService = new ApiService();
    apiService.createFolder = async (model) => {
      assert.strictEqual(model.id, folderId, 'Api service method should be called with proper id.');
      throw new NotFoundError('');
    };

    const stateManager = new StateManager({}, apiService);
    stateManager.mutate = (mutator) => {
      if (mutator instanceof FolderCreatingMutator) {
        assert.step('FolderCreatingMutator');
      } else if (mutator instanceof FolderCreatingErrorMutator) {
        assert.step('FolderCreatingErrorMutator');
      }
    };

    const action = new CreateFolderAction({id: folderId, name: 'test'});
    await action.apply(stateManager, apiService);
    assert.verifySteps(['FolderCreatingMutator', 'FolderCreatingErrorMutator',
      'FolderCreatingMutator']);
  });
});
