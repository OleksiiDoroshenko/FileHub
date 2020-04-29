import UserData from '../../../models/user-data';
import AuthorizationError from '../../../models/errors/authorization-error';
import VerificationError from '../../../models/errors/verification-error';
import fetchMock from '../../../../../node_modules/fetch-mock/esm/client.js';

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

  constructor() {
    console.log('Current users');
    this.printUsers();

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
      .post('/registration', ((url, request) => {
        const userData = new UserData(request.body.login, request.body.password);
        if (!this.isLoginRegistered(userData) && userData.password.length >= 10) {
          console.log(`New user ${userData.login} has been registered`);
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
      .get('/get-items', ((url, request) => {
        const items = [
          {
            type: 'folder',
            config: {name: 'Documents', itemsAmount: '2'},
          },
          {
            type: 'folder',
            config: {name: 'Images', itemsAmount: '2'},
          },
          {
            type: 'folder',
            config: {name: 'Videos', itemsAmount: '1'},
          },
          {
            type: 'file',
            config: {name: 'test.txt', mimeType: 'text', size: '20KB'},
          },
        ];
        return {items: items};
      }));
  }


  printUsers() {
    for (let p in this.users) {
      console.log(p);
    }
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

  isLoginRegistered(userData) {
    const login = userData.login.toLowerCase();
    console.log(`login ${login} is ${this.users.hasOwnProperty(login)}`);
    return this.users.hasOwnProperty(login);
  }
}
