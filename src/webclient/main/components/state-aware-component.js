import Component from './component.js';

/**
 * Extension of {@link Component} that adds opportunity for interaction with {@link StateManager}.
 */
export default class StateAwareComponent extends Component {
  /**
   * Returns instance of {@link StateAwareComponent}.
   * @param {HTMLElement} container - element for data rendering.
   * @param {Object} config - initial configuration.
   * @param {StateManager} stateManager - instance of {@link StateManager}.
   */
  constructor(container, config, stateManager) {
    super(container, config);
    this.stateManager = stateManager;
    this.initState();
  }

  /**
   * Initializes fields that interacts with {@link StateManager}.
   */
  initState() {
  }

  onStateChanged(filed, handler) {
    this.stateManager.onStateChanged(filed, handler);
  }
}
