import AuthorizationError from '../../../models/errors/authorization-error';
import ServerValidationError from '../../../models/errors/server-validation-error';

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
   *  Implements logic for user log in.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<number>} if user with this login and password is already registered then
   * method resolve contains welcoming alert.
   * If user is not registered than method reject returns {@link AuthorizationError}.
   */
  logIn(userData) {
    return new Promise(((resolve, reject) => {
      if (this.isRegistered(userData)) {
        resolve(200);
      } else {
        reject(new AuthorizationError('User with this login is not found.'));
      }
    }));
  }

  /**
   * Implements logic for user registration.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<number>} if user with this login is already registered then method reject returns
   * {@link AuthorizationError}. If user's is not registered, but his password is not valid method reject returns
   * {@link ServerValidationError}. If everything is alright method resolve contains redirection to {@link LoginPage}
   */
  register(userData) {
    const login = userData.login;
    const password = userData.password;
    return new Promise(((resolve, reject) => {
      if (password.length >= 10 && !this.isRegistered(userData)) {
        this.users[login] = password;
        resolve(200);
      } else {
        const errors = [];
        if (this.isRegistered(userData)) {
          reject(new AuthorizationError('User with this login already exists.'));
        } else {
          errors.push(new ServerValidationError({
            filed: 'password',
            message: 'Pass word should be longer than 10 characters.',
          }));
          reject(errors);
        }
      }
    }));
  }

  /**
   * Checks if user with this login and password is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @return {boolean} if user is registered returns true if it's not returns false.
   */
  isRegistered(userData) {
    const login = userData.login;
    const password = userData.password;
    return this.users[login] && this.users[login] === password;
  }
}
