import ApiService from '../../main/services/api-service';
import StateManager from '../../main/services/state-manager';
import FolderLoadingMutator from '../../main/services/state-manager/mutators/folder-loading-mutator';
import NotFoundError from '../../models/errors/not-found-error';
import FolderCreatingMutator from '../../main/services/state-manager/mutators/folder-creating-mutator';
import FolderCreatingErrorMutator from '../../main/services/state-manager/mutators/folder-creating-error-mutator';
import CreateFolderAction from '../../main/services/state-manager/actions/create-folder';

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
        assert.step('FolderLoadingMutator');
      }
    };

    const action = new CreateFolderAction({id: folderId, name: 'test'});
    await action.apply(stateManager, apiService);
    assert.verifySteps(['FolderLoadingMutator', 'FolderLoadingMutator']);
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
