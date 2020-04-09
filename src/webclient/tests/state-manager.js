import AppService from '../main/services/app-secrvice';
import UserData from '../models/user-data';
import AuthorizationError from '../models/errors/authorization-error';
import StateManager from '../main/services/state-manager';
import ItemLoadingMutator from '../main/services/state-manager/mutators/item-loading-mutator';
import ItemLoadingErrorMutator from '../main/services/state-manager/mutators/item-loading-error-mutator';
import ItemsMutator from '../main/services/state-manager/mutators/get-item-mutator';
import GetItemsAction from '../main/services/state-manager/actions/get-items';

const {module, test} = QUnit;
const service = new AppService();

export default module('State manager nad actions test', function(hook) {
  const stateManager = new StateManager({}, new AppService());

  test('Should mutate its state', async (assert) => {
    let isLoadingMutator = new ItemLoadingMutator(true);
    stateManager.mutate(isLoadingMutator);
    assert.ok(stateManager.state.isLoading, 'Should assign true to the isLoading flag.');
    isLoadingMutator = new ItemLoadingMutator(false);
    stateManager.mutate(isLoadingMutator);
    assert.notOk(stateManager.state.isLoading, 'Should assign false to the isLoading flag.');

    const isLoadingErrorMutator = new ItemLoadingErrorMutator(new Error('Test'));
    stateManager.mutate(isLoadingErrorMutator);
    assert.strictEqual(stateManager.state.error.message, 'Test'
      , 'Should assign error to the state.');

    const itemsMutator = new ItemsMutator([{0: 'Test'}]);
    stateManager.mutate(itemsMutator);
    debugger;
    assert.strictEqual(stateManager.state.items.length, 1
      , 'Should assign items to the state.');
  });

  test('Should dispatch action', async (assert) => {
    const getItemsAction = new GetItemsAction();
    stateManager.onStateChanged('items', (state) => {
      assert.strictEqual(state.items.length, 4
        , 'Should dispatch get items action.');
    });
    stateManager.dispatch(getItemsAction);
    assert.ok(true, '');
  });
});
