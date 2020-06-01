import Mutator from '../mutator.js';

/**
 * Allows {@link StateManager} state user field changing.
 */
export default class UserDataMutator extends Mutator {

  /**
   * Returns instance of {@link UserDataMutator} class.
   * @param {Object} user - user info.
   * @param {string} user.id - user's id.
   * @param {string} user.name - username.
   */
  constructor(user) {
    super();
    this.user = user;
  }

  /**
   * @inheritoc
   */
  apply(state) {
    state.user = this.user;
  }
}
