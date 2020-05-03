import UserData from '../../../models/user-data';
import AuthorizationError from '../../../models/errors/authorization-error';
import VerificationError from '../../../models/errors/verification-error';
import fetchMock from '../../../../../node_modules/fetch-mock/esm/client.js';

/**
 * Pretending to be a server.
 * <p> Can answer to some of requests.
 */
export default class MockServer {

  /**
   * @typedef User
   *  @param {string} login - users login.
   *  @param {string} password - users password.
   */

  /**
   * Registered users.
   * @type {{User}}
   */
  users = {
    admin: 'Admin123456',
  };

  /**
   * List of user items.
   * @type {[ListItem]}
   */
  items = [
    {
      id: '1', parentId: '0', name: 'Documents', itemsAmount: '2', type: 'folder',
    },
    {
      id: '2', parentId: '0', name: 'Images', itemsAmount: '2', type: 'folder',
    },
    {
      id: '3', parentId: '0', name: 'Videos', itemsAmount: '1', type: 'folder',
    },
    {
      id: '4', parentId: '0', name: 'test.txt', mimeType: 'text', size: '20KB', type: 'file',
    },
  ];

  /**
   * Returns instance of {@link MockServer}.
   * <p> Instance has mocked HTTP requests.
   */
  constructor() {

    fetchMock.config.overwriteRoutes = true;

    fetchMock
      .post('/login', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (this.isUserRegistered(userData)) {
          return {
            status: 200,
            body: {token: `${userData.login}-token`},
          };
        } else {
          return 401;
        }
      }));

    fetchMock
      .post('/register', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (!this.isLoginRegistered(userData) && userData.password.length >= 10) {
          this.users[userData.login.toLowerCase()] = userData.password;
          return 200;
        } else {
          if (this.isLoginRegistered(userData)) {
            return {
              status: 401,
              body: {
                error: new AuthorizationError('User with this login already exists.'),
              },
            };
          } else {
            let errors = [];
            errors.push(new VerificationError('password', 'Password should be longer than 10 characters.'));
            return {
              status: 422,
              body: {
                errors,
              },
            };
          }
        }
      }));

    fetchMock
      .get('express:/folder/:id/content', ((url) => {
        const id = url.split('/')[2];
        let items = [];
        if (id === '0') {
          items = this.items;
        }
        return {items: items};
      }));
  }

  /**
   * Checks if user with this login and password is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @return {boolean} if user is registered returns true if it's not returns false.
   */
  isUserRegistered(userData) {
    const login = userData.login.toLowerCase();
    const password = userData.password;
    return this.users.hasOwnProperty(login) && this.users[login] === password;
  }

  /**
   * Checks if this login is registered.
   * @param {UserData} userData - instance of {@link UserData}.
   * @returns {boolean} if login is already registered returns True if not, false.
   */
  isLoginRegistered(userData) {
    const login = userData.login.toLowerCase();
    console.log(`login ${login} is ${this.users.hasOwnProperty(login)}`);
    return this.users.hasOwnProperty(login);
  }
}
