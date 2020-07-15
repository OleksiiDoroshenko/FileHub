import ApiService from '../../main/services/api-service/index.js';
import StateManager from '../../main/services/state-manager/index.js';
import NotFoundError from '../../models/errors/not-found-error/index.js';
import AddItemToRenamingListMutator
  from '../../main/services/state-manager/mutators/add-item-to-renaming-list-mutator/index.js';
import RemoveItemFromRenamingListMutator
  from '../../main/services/state-manager/mutators/remove-item-from-renaming-list-mutator/index.js';
import RenameItemAction from '../../main/services/state-manager/actions/rename-item/index.js';
import ItemsRenamingErrorMutator from '../../main/services/state-manager/mutators/item-renaming-error-mutator/index.js';

const {module, test} = QUnit;

export default module('Rename item action test: ', function(hook) {

  test('Rename item action should call specific steps', async (assert) => {
    assert.expect(7);
    const itemId = '0';
    const name = 'test';
    const model = {name, id: '0', parentId: '1', type: 'folder'};

    const apiService = new ApiService();
    apiService.renameItem = async (model) => {
      assert.strictEqual(model.id, itemId, 'Api service method should be called with proper id.');
      assert.strictEqual(model.name, name, 'Api service method should be called with proper id.');
      return 200;
    };

    const stateManager = new StateManager({}, apiService);
    stateManager.mutate = (mutator) => {
      if (mutator instanceof AddItemToRenamingListMutator) {
        assert.strictEqual(mutator.itemId, itemId, 'Mutator should be created with proper params.');
        assert.step('AddItemToRenamingListMutator');
      } else if (mutator instanceof RemoveItemFromRenamingListMutator) {
        assert.strictEqual(mutator.itemId, itemId, 'Mutator should be created with proper params.');
        assert.step('RemoveItemFromRenamingListMutator');
      }
    };

    const action = new RenameItemAction(model);
    await action.apply(stateManager, apiService);
    assert.verifySteps(['AddItemToRenamingListMutator', 'RemoveItemFromRenamingListMutator']);
  });

  test('Rename item action test when error was raised.', async (assert) => {
    assert.expect(7);
    const itemId = '0';
    const name = 'test';

    const apiService = new ApiService();
    apiService.renameItem = async (model) => {
      assert.strictEqual(model.id, itemId, 'Api service method should be called with proper id.');
      throw new NotFoundError('');
    };

    const stateManager = new StateManager({}, apiService);
    stateManager.mutate = (mutator) => {
      if (mutator instanceof AddItemToRenamingListMutator) {
        assert.strictEqual(mutator.itemId, itemId, 'Mutator should be created with proper params.');
        assert.step('AddItemToRenamingListMutator');
      } else if (mutator instanceof RemoveItemFromRenamingListMutator) {
        assert.strictEqual(mutator.itemId, itemId, 'Mutator should be created with proper params.');
        assert.step('RemoveItemFromRenamingListMutator');
      } else if (mutator instanceof ItemsRenamingErrorMutator) {
        assert.step('ItemsRenamingErrorMutator');
      }
    };


    const action = new RenameItemAction({id: itemId, name});
    await action.apply(stateManager, apiService);
    assert.verifySteps(['AddItemToRenamingListMutator', 'ItemsRenamingErrorMutator',
      'RemoveItemFromRenamingListMutator']);
  });
});
