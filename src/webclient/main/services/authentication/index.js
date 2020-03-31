import VerificationError from '../../../models/errors/verification-error';
import AuthorizationError from '../../../models/errors/authorization-error';

/**
 * Implements login and registration methods logic.
 */
export default class AuthenticationService {
  /**
   * @typedef User
   *  @param {string} login - users login.
   *  @param {string} password - users password.
   */

  /**
   * Registered users.
   * @type {{User}}
   */
  users = {};

  /**
   *  Implements logic for user login.
   * @param {string} login - users login.
   * @param {string} password - users password.
   * @return {Promise<AuthorizationError>} if user with this login and password is already registered then
   * method resolve contains welcoming alert.
   * If user is not registered than method reject returns {@link AuthorizationError}.
   */
  login(login, password) {
    return new Promise(((resolve, reject) => {
      if (this.isRegistered(login, password)) {
        resolve(200);
      } else {
        reject(new AuthorizationError(422, 'User with this login is not found.'));
      }
    }));
  }

  /**
   * Implements logic for user registration.
   * @param {string} login - users login.
   * @param {string} password - users password.
   * @return {Promise<Error>} if user with this login is already registered then method reject returns
   * {@link AuthorizationError}. If user's is not registered, but his password is not valid method reject returns
   * {@link VerificationError}. If everything is alright method resolve contains redirection to {@link LoginPage}
   */
  register(login, password) {
    return new Promise(((resolve, reject) => {
      if (this.isRegistered(login, password)) {
        reject(new AuthorizationError(422, 'User with this login already exists.'));
      } else if (password.length >= 10) {
        this.users[login] = password;
        resolve(200);
      } else {
        reject(new VerificationError(401, 'password', 'Password should be longer than 10 characters.'));
      }
    }));
  }

  /**
   * Checks if user with this login and password is registered.
   * @param {string} login - users login.
   * @param {string} password - users password.
   * @return {boolean} if user is registered returns true if it's not returns false.
   */
  isRegistered(login, password) {
    return this.users[login] && this.users[login] === password;
  }
}
