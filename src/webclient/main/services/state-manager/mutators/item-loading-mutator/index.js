import Mutator from '../mutator.js';

export default class ItemLoadingMutator extends Mutator {
  constructor(isLoading) {
    super();
    this.isLoading = isLoading;
  }


  apply(state) {
    state.isLoading = this.isLoading;
  }
}
