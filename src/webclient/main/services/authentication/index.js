import VerificationError from '../../../models/errors/verification-error';
import AuthorizationError from '../../../models/errors/authorization-error';
import fetchMock from '../../../../../node_modules/fetch-mock/esm/client.js';
import UserData from '../../../models/user-data';

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
  users = {
    admin: 'Admin123456',
  };

  /**
   * Creates object that intercepts requests for the server.
   */
  constructor() {
    console.log('Current users');
    this.printUsers();

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
  }


  printUsers() {
    for (let p in this.users) {
      console.log(p);
    }
  }

  /**
   *  Implements logic for user login.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<Response>} if user with this login and password is already registered then
   * method resolve contains welcoming alert.
   * If user is not registered than method reject returns {@link AuthorizationError}.
   */
  login(userData) {
    return new Promise((resolve, reject) => {
      fetch('/login', {
        method: 'POST',
        body: userData,
      }).then(response => {
        if (response.ok) {
          const result = response.json();
          result.then(body => {
            resolve(body.token);
          });
        } else {
          reject(new AuthorizationError('Invalid login or password.'));
        }
      });
    });
  }

  /**
   * Implements logic for user registration.
   * @param {UserData} userData -instance of {@link UserData}.
   * @return {Promise<Error>} if user with this login is already registered then method reject returns
   * {@link AuthorizationError}. If user's is not registered, but his password is not valid method reject returns
   * {@link VerificationError}. If everything is alright method resolve contains redirection to {@link LoginPage}
   */
  register(userData) {
    return new Promise(((resolve, reject) => {
      fetch('/registration', {
        method: 'POST',
        body: userData,
      }).then(response => {
        if (response.ok) {
          resolve();
        }
        let result = response.json();
        switch (response.status) {
          case 401: {
            result.then(errors => {
              console.log(errors.error.message);
              reject(errors.error);
            });
            break;
          }
          case 422: {
            let serverErrors = [];
            result.then(errors => {
              errors.errors.forEach(error => {
                serverErrors.push(new VerificationError(error.field, error.message));
              });
              reject(serverErrors);
            });
            break;
          }
          case 500: {
            reject(new Error('Internal server error.'));
            break;
          }
        }
      });
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

  isLoginRegistered(userData) {
    const login = userData.login.toLowerCase();
    console.log(`login ${login} is ${this.users.hasOwnProperty(login)}`);
    return this.users.hasOwnProperty(login);
  }
}
