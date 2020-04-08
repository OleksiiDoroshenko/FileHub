import Mutator from '../mutator.js';

export default class FilesMutator extends Mutator {
  constructor(files) {
    super();
    this.files = files;
  }

  apply(state) {
    state.files = this.files;
  }
}
