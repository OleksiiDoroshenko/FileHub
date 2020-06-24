import ApiService from '../../main/services/api-service';
import StateManager from '../../main/services/state-manager';
import AddItemToDownloadingListMutator
  from '../../main/services/state-manager/mutators/add-item-to-download-list-mutator';
import RemoveItemFromDownloadingListMutator
  from '../../main/services/state-manager/mutators/remove-item-from-downloading-list-mutator';
import DownloadFileService from '../../main/services/download-file-service';
import DownloadFileAction from '../../main/services/state-manager/actions/download-file';
import NotFoundError from '../../models/errors/not-found-error';
import ItemsDownloadingErrorMutator from '../../main/services/state-manager/mutators/items-downloading-error-mutator';

const {module, test} = QUnit;

export default module('Download action test: ', function(hook) {
  test('Download file action should call specific steps', async (assert) => {
    assert.expect(8);
    const fileId = '0';
    const testBlob = new Blob(['smth'], {type: `text/txt`});
    const testFileName = 'test';

    const apiService = new ApiService();
    apiService.getFile = async ({id}) => {
      assert.strictEqual(id, fileId, 'Api service method should be called with proper id.');
      return testBlob;
    };

    const stateManager = new StateManager({}, apiService);
    stateManager.mutate = (mutator) => {
      if (mutator instanceof AddItemToDownloadingListMutator) {
        assert.strictEqual(mutator.itemId, fileId, 'Mutator should be created with proper params.');
        assert.step('AddItemToDownloadingListMutator');
      } else if (mutator instanceof RemoveItemFromDownloadingListMutator) {
        assert.strictEqual(mutator.itemId, fileId, 'Mutator should be created with proper params.');
        assert.step('RemoveItemFromDownloadingListMutator');
      }
    };

    const downloadService = new DownloadFileService();
    downloadService.download = (blob, fileName) => {
      assert.strictEqual(blob.parts, testBlob.parts, 'Download file service\'s method should be called with proper blob param.');
      assert.strictEqual(fileName, testFileName,
        'Download file service\'s method should be called with proper file name param.');
    };

    const action = new DownloadFileAction({id: fileId, name: testFileName}, downloadService);
    await action.apply(stateManager, apiService);
    assert.verifySteps(['AddItemToDownloadingListMutator', 'RemoveItemFromDownloadingListMutator']);
  });

  test('Download file action test when error was raised.', async (assert) => {
    assert.expect(7);
    const fileId = '0';
    const testFileName = 'test';

    const apiService = new ApiService();
    apiService.getFile = async ({id}) => {
      assert.strictEqual(id, fileId, 'Api service method should be called with proper id.');
      return new NotFoundError('', '');
    };

    const stateManager = new StateManager({}, apiService);
    stateManager.mutate = (mutator) => {
      if (mutator instanceof AddItemToDownloadingListMutator) {
        assert.strictEqual(mutator.itemId, fileId, 'Mutator should be created with proper params.');
        assert.step('AddItemToDownloadingListMutator');
      } else if (mutator instanceof RemoveItemFromDownloadingListMutator) {
        assert.strictEqual(mutator.itemId, fileId, 'Mutator should be created with proper params.');
        assert.step('RemoveItemFromDownloadingListMutator');
      } else if (mutator instanceof ItemsDownloadingErrorMutator) {
        assert.step('ItemsDownloadingErrorMutator');
      }
    };

    const downloadService = new DownloadFileService();
    const action = new DownloadFileAction({id: fileId, name: testFileName}, downloadService);
    await action.apply(stateManager, apiService);
    assert.verifySteps(['AddItemToDownloadingListMutator', 'ItemsDownloadingErrorMutator',
      'RemoveItemFromDownloadingListMutator']);
  });
});
