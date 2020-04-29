import VerificationError from '../../../models/errors/verification-error';
import AuthorizationError from '../../../models/errors/authorization-error';

import UserData from '../../../models/user-data';
import MockServer from '../mock-server';

/**
 * Implements login and registration methods logic.
 */
export default class AppService {
  /**
   * Creates object that intercepts requests for the server.
   */
  constructor(devMod) {
    if (devMod) {
      new MockServer();
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

  getItems() {
    return new Promise((resolve, reject) => {
      fetch('/get-items', {
        method: 'GET',
      }).then(response => {
        const result = response.json();
        result.then(result => {
          resolve(result.items);
        });
      });
    });
  }
}
