import ApiService from '../main/services/api-service';
import StateManager from '../main/services/state-manager';
import Mutator from '../main/services/state-manager/mutators/mutator.js';
import Action from '../main/services/state-manager/actions/action.js';
import ItemsMutator from '../main/services/state-manager/mutators/items-mutator';
import ItemsLoadingMutator from '../main/services/state-manager/mutators/items-loading-mutator';
import ItemsLoadingErrorMutator from '../main/services/state-manager/mutators/items-loading-error-mutator';
import GetRootIdAction from '../main/services/state-manager/actions/get-root-id';

const {module, test} = QUnit;

export default module('State manager test: ', function(hook) {
  const stateManager = new StateManager({}, new ApiService(false));

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

    function _testMutator(assert, mutator, field, value) {
      assert.notStrictEqual(stateManager.state[field], value, `should not be equal future ${field}`);
      stateManager.mutate(mutator);
      assert.strictEqual(stateManager.state[field], value, `'should change state's ${field} field'`);
    }
  });
});
