import AppService from '../main/services/app-secrvice';
import StateManager from '../main/services/state-manager';
import Mutator from '../main/services/state-manager/mutators/mutator.js';
import Action from '../main/services/state-manager/actions/action.js';

const {module, test} = QUnit;

export default module('State manager test: ', function(hook) {
  const stateManager = new StateManager({}, new AppService(false));

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
});
