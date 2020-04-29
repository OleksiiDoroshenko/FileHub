export default class StateManager extends EventTarget {

  /**
   * Returns instance of {@link StateManager} class.
   * <p> Adds set event handler for state properties.
   * @param {object} initialState - initial state of {@link FileExplorerPage} content.
   * @param {AppService} appService - instance of {@link AppService}.
   */
  constructor(initialState, appService) {
    super();
    this.appService = appService;
    const setHandler = {
      set: (obj, prop, value) => {
        obj[prop] = value;
        const stateChangedEvent = new Event(`stateChanged.${prop}`);
        this.dispatchEvent(stateChangedEvent);
        return true;
      },
    };

    this.state = new Proxy(initialState, setHandler);
  }

  /**
   * Adds event handler to state filed change event.
   * @param {string} field - state filed that would be changed.
   * @param {function} handler - function that should be called when filed is changed.
   */
  onStateChanged(field, handler) {
    this.addEventListener(`stateChanged.${field}`, () => handler(this.state));
  }

  /**
   * Sends this {@link StateManager} and this.{@link AppService}.
   * @param {Action} action - class that extends {@link Action}.
   */
  dispatch(action) {
    action.apply(this, this.appService);
  }

  /**
   * Sends current state to the mutator.
   * @param {Mutator} mutator - instance of {@link Mutator}.
   */
  mutate(mutator) {
    mutator.apply(this.state);
  }

}
