import Mutator from '../mutator.js';

export default class ItemLoadingErrorMutator extends Mutator {
  constructor(error) {
    super();
    this.error = error;
  }


  apply(state) {
    state.error = this.error;
  }
}
