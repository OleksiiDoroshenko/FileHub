export default class StateManager extends EventTarget {

  constructor(initialState, apiService) {
    super();
    this.apiService = apiService;
    const setHandler = {
      set: (obj, prop, value) => {
        const setSucceeded = Reflect.set(...arguments);
        if (setSucceeded) {
          const stateChangedEvent = new Event(`stateChanged${prop}`);
          this.dispatchEvent(stateChangedEvent);
        }
        return setSucceeded;
      },
    };

    this.state = new Proxy(initialState, setHandler);
  }

  onStateChanged(field, handler) {
    this.addEventListener(`stateChanged.${field}`, handler);
  }

  dispatch(action) {
    return action.apply(this.state, this.apiService);
  }

  /**
   *
   * @param {Mutator} mutator
   */
  mutate(mutator) {
    mutator.apply(this.state);
  }

}
