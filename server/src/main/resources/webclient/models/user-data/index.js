/**
 * Contains users data, such as login and password.
 */
export default class UserData {
  /**
   * Class constructor.
   * @param {string} login - users login.
   * @param {string} password - users password.
   */
  constructor(login, password) {
    this.login = login;
    this.password = password;
  }
}
