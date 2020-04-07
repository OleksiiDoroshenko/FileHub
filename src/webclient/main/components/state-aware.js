import Component from './component.js';

export default class StateAwareComponent extends Component {
  constructor(container, config, stateManager) {
    super(container, config);
    this.stateManager = stateManager;
  }

  initState() {
  }
}
